import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ObjectRegistry extends UnicastRemoteObject implements ObjectRegistryInterface {

    private String objectID;

    public ObjectRegistry() throws RemoteException {
        objectID = new String();
    }
    @Override
    public void addObject(String objectID, String serverAddress) throws RemoteException {

    }

    @Override
    public String resolve(String objectID) throws RemoteException {
        return null;
    }
}
