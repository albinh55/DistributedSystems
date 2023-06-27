import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Wardrobes extends Remote {
    public Clothing search(String category, String colour) throws RemoteException;

    public Set<Clothing> getClothing() throws RemoteException;

    public void add(String category, String colour, String size) throws RemoteException;

    public String getName() throws RemoteException;

    public String getAllCategories() throws RemoteException;
}
