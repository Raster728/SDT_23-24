import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PlacesListInterface l = null;
        try{
            l  = (PlacesListInterface) Naming.lookup("rmi://localhost:2022/placelist");
            Place p = new Place("3510", "Viseu");
            l.addPlace(p);

            ArrayList<Place> sList = l.allPlaces();
            System.out.println(sList.get(0).getLocality());
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        }catch(Exception e) {e.printStackTrace();}
    }
}