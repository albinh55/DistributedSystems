import java.rmi.*;

public class Server {
    public Server () throws RemoteException {
    }

    public static void main (String[] args) {
        try {
            WardrobesImpl wardrobes1 = new WardrobesImpl("Albins Gardarobe");
            wardrobes1.add("Hose", "rot", "M");
            wardrobes1.add("Socken", "blau", "XS");

            WardrobesImpl wardrobes2 = new WardrobesImpl("Erions Gardarobe");
            wardrobes2.add("Jeans", "rot", "L");
            wardrobes2.add("Hemd", "schwarz", "M");


            Naming.rebind ("Albin", wardrobes1);
            Naming.rebind ("Erion", wardrobes2);

            System.out.println("The server is up");
        } catch (Exception e) {
            System.out.println("example.WardrobeServer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
