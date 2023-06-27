import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Set;

public class Client {
    public static void main(String[] args) throws Exception {

        try {
            Wardrobes wardrobes1 = (Wardrobes) Naming.lookup("rmi://" + "localhost" + "/Albin");
            Wardrobes wardrobes2 = (Wardrobes) Naming.lookup("rmi://" + "localhost" + "/Erion");

            //war 1 name
            System.out.println ("Name of wardrobe1: " + wardrobes1.getName());
            System.out.println ("Name of wardrobe2: " + wardrobes2.getName());

            //war 2 add
            wardrobes1.add("Sacko","rot" , "XL");
            wardrobes2.add("Shorts","lila" , "L");

            //war 3 search
            Clothing s_clothes1 = wardrobes1.search("Sacko", "rot");
            Clothing s_clothes2 = wardrobes2.search("Hemd", "schwarz");
            System.out.println("Search: category = " + s_clothes1.getCategory() +", colour = "+ s_clothes1.getColour() + ", size = " + s_clothes1.getSize());
            System.out.println("Search: category = " + s_clothes2.getCategory() +", colour = "+ s_clothes2.getColour() + ", size = " + s_clothes2.getSize());

            s_clothes1.setSize("S");

            //war 4 set
            Set<Clothing> clothes1 = wardrobes1.getClothing();
            for (Clothing clothing : clothes1) {
                System.out.println("Clothing in wardrobe1: category = " + clothing.getCategory() +
                        ", colour = " + clothing.getColour() + ", size = " + clothing.getSize());
            }

            Set<Clothing> clothes2 = wardrobes2.getClothing();
            for (Clothing clothing : clothes2) {
                System.out.println("Clothing in wardrobe2: category = "+ clothing.getCategory() +
                        ", colour = " + clothing.getColour() + ", size = " + clothing.getSize());
            }



            int redcounter = countRedClothes(clothes1) +countRedClothes(clothes2);
            System.out.println("Total of red clothes: " + redcounter);

        }catch (Exception e) {
            System.out.println("example.WardrobeServer: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private static int countRedClothes(Set<Clothing> clothes) throws RemoteException {
        int count = 0;
        for (Clothing clothing : clothes) {
            try {
                if ("rot".equals(clothing.getColour())) {
                    count++;
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }
}


