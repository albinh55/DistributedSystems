import javax.jms.*;
import javax.naming.*;
import java.lang.reflect.Array;
import java.util.*;

public class Server {
    public static void main(String argv[]) throws Exception {
        Wardrobes wardrobes1 = new Wardrobes("Albins Gardarobe");
        wardrobes1.add("Hose", "rot", "M");
        wardrobes1.add("Socken", "blau", "XS");

        Wardrobes wardrobes2 = new Wardrobes("Erions Gardarobe");
        wardrobes2.add("Jeans", "grün", "L");
        wardrobes2.add("Hemd", "schwarz", "M");

        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

        Context context = new InitialContext(properties);

        TopicConnectionFactory connFactory =
                (TopicConnectionFactory) context.lookup("ConnectionFactory");
        TopicConnection conn = connFactory.createTopicConnection();
        conn.start();
        TopicSession session =
                conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = (Topic) context.lookup("dynamicTopics/topic1");

        TopicSubscriber subscriber = session.createSubscriber(topic);
        while (true) {
            Topic topic2 = (Topic) context.lookup("dynamicTopics/topic2");
            TextMessage message = (TextMessage) subscriber.receive();
            String[] list;
            list = message.getText().split(", ");
            String reply = decoder(list, wardrobes1, wardrobes2);
            //ArrayList<String> list = new ArrayList<>();
            //list.add(message.getText());
            TopicPublisher publisher = session.createPublisher(topic2);
            TextMessage responseMessage = session.createTextMessage(reply);
            publisher.publish(responseMessage);
        }


    }

    public static String decoder(String list[], Wardrobes wardrobes1, Wardrobes wardrobes2){
        String reply = "nix";
        int list_size = list.length;
        switch (list_size){
            case 1:
                if (list[0].equals("object_one")){
                    reply = category(wardrobes1);
                } else if (list[0].equals("object_two")) {
                    reply = category(wardrobes2);
                }else{
                    reply = "fehler";
                    System.out.println(list[0]);
                }
                break;

            case 3:
                if (list[0].equals("object_one")){
                    reply = search(wardrobes1, list[1], list[2]);
                } else if (list[0].equals("object_two")) {
                    reply = search(wardrobes2, list[1], list[2]);
                }else{
                    reply = "fehler2";
                }
                break;

            case 4:
                if (list[0].equals("object_one")){
                    reply = add(wardrobes1, list[1], list[2], list[3]);
                } else if (list[0].equals("object_two")) {
                    reply = add(wardrobes2, list[1], list[2], list[3]);
                }else{
                    reply = "fehler3";
                }
                break;
        }
        return reply;
    }
    public static String add(Wardrobes object, String category, String colour, String size){
        object.add(category, colour, size);
        return "object added succsesfully";
    }
    public static String category(Wardrobes object){
        return object.getAllCategories();
    }
    public static String search(Wardrobes object, String category, String colour){
        Clothing cloth = object.search(category, colour);
        return cloth.getSize();
    }
}