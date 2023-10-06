import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {
    DataInputStream in = new DataInputStream( s.getInputStream());
    DataOutputStream out = new DataOutputStream( s.getOutputStream());
    private String name;
    private int year;

    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(object);
    Person ret = (Person) ois.readObject();
}
