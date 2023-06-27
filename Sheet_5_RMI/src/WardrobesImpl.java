import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class WardrobesImpl extends UnicastRemoteObject implements Wardrobes {
    private String name;
    private Set<Clothing> cloth;

    public WardrobesImpl(String name) throws RemoteException {
        this.name = name;
        this.cloth = new HashSet<Clothing>();
    }

    public Clothing search(String category, String colour) throws RemoteException {
        for (Clothing c : cloth) {
            if (c.getCategory().equals(category)) {
                if (c.getColour().equals(colour)) {
                    return c;
                }
            }
        }
        return null;
    }

    public Set<Clothing> getClothing() throws RemoteException {
        return cloth;

    }

    public void add(String category, String colour, String size) throws RemoteException {
        cloth.add(new ClothingImpl(category, colour, size));

    }


    public String getName() throws RemoteException {
        return name;
    }


    public String getAllCategories() throws RemoteException {
        Set<String> categories = new LinkedHashSet<String>();

        for (Clothing c : cloth) {
            categories.add(c.getCategory());
        }

        return String.join(", ", categories);
    }

}
