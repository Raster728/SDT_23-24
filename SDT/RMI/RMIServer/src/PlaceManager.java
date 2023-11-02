import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;

class PlaceManager extends UnicastRemoteObject implements PlacesListInterface{
    private ArrayList<Place> placeList;

    public PlaceManager() throws RemoteException {
        placeList = new ArrayList<>();
    }

    @Override
    public void addPlace(Place p) throws RemoteException {
        placeList.add(p);
    }

    @Override
    public ArrayList<Place> allPlaces() throws RemoteException {
        return placeList;
    }
}
