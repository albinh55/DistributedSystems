import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main (String args[]) {
        Wardrobes wardrobes1 = new Wardrobes("Albins Gardarobe");
        wardrobes1.add("Hose", "rot", "M");
        wardrobes1.add("Socken", "blau", "XS");

        Wardrobes wardrobes2 = new Wardrobes("Erions Gardarobe");
        wardrobes2.add("Jeans", "grün", "L");
        wardrobes2.add("Hemd", "schwarz", "M");
        try{

            System.out.println("The Server is running");
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket (serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                System. out.println("New Connection");
                Connection c = new Connection(clientSocket, wardrobes1, wardrobes2);
            }
        } catch( IOException e) {System.out.println(" Listen :"+ e.getMessage());}
    }// main

}//class


class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Wardrobes wardrobes2;
    Wardrobes wardrobes1;

    public Connection (Socket aClientSocket, Wardrobes wardrobes1, Wardrobes wardrobes2) {
        try {
            clientSocket = aClientSocket;
            out = new DataOutputStream ( clientSocket.getOutputStream() );
            in = new DataInputStream ( clientSocket.getInputStream() );
            this.start();
            this.wardrobes1 = wardrobes1;
            this.wardrobes2 = wardrobes2;
        } catch( IOException e) {System. out. println(" Connection:"+ e.getMessage());}
    }

    public void run(){

        try {
            int data = in.readInt ();
            int method = data /10000;
            int wardrob = (data%10000)/1000;
            int category = (data%1000)/100;
            int colour = (data%100)/10;
            int size = data%10;
            String result = null;

            switch (method) {
                case 1:
                    result = add(wardrob, category, colour, size, wardrobes1, wardrobes2);
                    break;
                case 2:
                    result = getCategories(wardrob, wardrobes1, wardrobes2);
                    break;
                case 3:
                    result = getName(wardrob, wardrobes1, wardrobes2);
                    break;
            }

            out.writeUTF(result);

            System.out.println("Sent data: " + result);
        } catch( EOFException e) {System.out.println(" EOF:"+ e.getMessage());
        } catch( IOException e) {System.out.println(" IO:"+ e.getMessage());}
    }
    public String add(int wardrob, int category, int colour, int size, Wardrobes wardrobes1, Wardrobes wardrobes2) {
        if (wardrob ==1) {
            wardrobes1.add(decoder("category", category),decoder("colour", colour),decoder("size", size));
            return "hat geklappt supa 1"+ wardrobes1.getClothing() ;
        } else {
            wardrobes2.add(decoder("category", category),decoder("colour", colour),decoder("size", size));
            return "hat geklappt supa 2"+ wardrobes2.getClothing() ;
        }
    }

    public String getCategories(int wardrob, Wardrobes wardrobes1, Wardrobes wardrobes2) {
        if (wardrob ==1) {
            return wardrobes1.getAllCategories();
        } else {
            return wardrobes2.getAllCategories();
        }
    }


    public String getName(int wardrob, Wardrobes wardrobes1, Wardrobes wardrobes2) {
        if (wardrob ==1) {
            return wardrobes1.getName();
        } else {
            return wardrobes2.getName();
        }
    }


    public String decoder(String object, int num) {
        String result = null;
        switch (object) {
            case "category":
                switch(num) {
                    case 0:
                        result = "Hose";
                        break;
                    case 1:
                        result = "Hemd";
                        break;
                    case 2:
                        result = "Socken";
                        break;
                    case 3:
                        result = "Pullover";
                        break;
                    case 4:
                        result = "Shirt";
                        break;
                    case 5:
                        result = "Shorts";
                        break;
                    case 6:
                        result = "Jeans";
                        break;
                    case 7:
                        result = "Mütze";
                        break;
                    case 8:
                        result = "Jogginghose";
                        break;
                    case 9:
                        result = "Sacko";
                        break;
                }
                break;
            case "colour":
                switch(num) {
                    case 0:
                        result = "rot";
                        break;
                    case 1:
                        result = "blau";
                        break;
                    case 2:
                        result = "grün";
                        break;
                    case 3:
                        result = "lila";
                        break;
                    case 4:
                        result = "gelb";
                        break;
                    case 5:
                        result = "pink";
                        break;
                    case 6:
                        result = "schwarz";
                        break;
                    case 7:
                        result = "weis";
                        break;
                    case 8:
                        result = "beige";
                        break;
                    case 9:
                        result = "grau";
                        break;
                }
                break;
            case "size":
                switch(num) {
                    case 0:
                        result = "M";
                        break;
                    case 1:
                        result = "L";
                        break;
                    case 2:
                        result = "S";
                        break;
                    case 3:
                        result = "XS";
                        break;
                    case 4:
                        result = "XSS";
                        break;
                    case 5:
                        result = "XL";
                        break;
                    case 6:
                        result = "XXL";
                        break;
                    case 7:
                        result = "XXXL";
                        break;
                    case 8:
                        result = "XSSS";
                        break;
                    case 9:
                        result = "SLIM FIT";
                        break;
                }
                break;
        }
        return result;
    }

}