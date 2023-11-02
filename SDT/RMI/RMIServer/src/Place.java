import java.io.Serializable;

public class Place implements Serializable {
    private String postalCode;
    private String locality;

    public Place(String _postalCode, String _locality){
        this.postalCode = _postalCode;
        this.locality = _locality;
    }

    public String getLocality(){ return locality; }

    public String getPostalCode(){
        return postalCode;
    }

    public void setLocality(String _locality){
        this.locality = _locality;
    }

    public void setPostalCode(String _postalCode){
        this.postalCode = _postalCode;
    }
}
