import java.io.*;

public class Person implements Serializable {
    private String name;
    private int year;
    private String phoneNumber;


    public Person(String _name, int _year){
        this.name = _name;
        this.year = _year;
    }

    public String getName(){
        return name;
    }

    public int getYear() { return year; }

    public String getPhoneNumber() { return phoneNumber; }


    public void setName(String _name){
        this.name = _name;
    }

    public void setYear(int _year){
        this.year = _year;
    }

    public void setPhoneNumber(String _phoneNumber) { this.phoneNumber = _phoneNumber; }


}