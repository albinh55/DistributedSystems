import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClothingImpl extends UnicastRemoteObject implements Clothing {
    private String category;
    private String colour;
    private String size;

    public ClothingImpl(String category, String colour, String size) throws RemoteException {
        this.category = category;
        this.colour = colour;
        this.size = size;
    }

    public String getCategory() throws RemoteException {
        return category;
    }

    public String getColour() throws RemoteException {
        return colour;
    }

    public String getSize() throws RemoteException {
        return size;
    }

    public void setSize(String size) throws RemoteException {
        this.size = size;
    }
}
