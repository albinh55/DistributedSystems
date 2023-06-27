import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Clothing extends Remote {
    public String getCategory() throws RemoteException;

    public String getColour() throws RemoteException;

    public String getSize() throws RemoteException;

    public void setSize(String size) throws RemoteException;
}