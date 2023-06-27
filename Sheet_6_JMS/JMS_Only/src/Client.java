import javax.jms.*;
import javax.naming.*;
import java.util.*;

public class Client {
    public static void main(String argv[]) throws Exception {

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
        Topic topic2 = (Topic) context.lookup("dynamicTopics/topic2");



        TopicPublisher publisher = session.createPublisher(topic);
        TopicSubscriber subscriber = session.createSubscriber(topic2);

        TextMessage message = session.createTextMessage("object_one");
        publisher.publish(message);
        System.out.println("Sending Message: "+ message.getText());

        TextMessage reply = (TextMessage) subscriber.receive();
        System.out.println("Message Received: " + reply.getText());

        session.close();
        conn.close();

        /*
        add,            object_one, Jacke, blau, XL
        categories,     object_one
        search,         object_one, Jacke, blau

*/
    }
}