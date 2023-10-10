import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public static void main (String args[]) {
        Socket s = null;
        try{
            int serverPort = 7896; //porto do servidor
            s = new Socket("localhost", serverPort);

            ObjectInputStream in = new ObjectInputStream( s.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream( s.getOutputStream());
            Person pessoa = new Person("Maria", 23);
            System.out.println(pessoa);
            out.writeObject(pessoa); //Envia os dados para o servidor

            String data = in.readUTF(); // Bloqueia à espera da resposta do servidor
            System.out.println("Received: "+ data);
        }catch (UnknownHostException e){
            System.out.println("Sock:"+e.getMessage());
        }catch (EOFException e){
            System.out.println("IO:" + e.getMessage());
        }catch (IOException e){
            System.out.println("IO:"+e.getMessage());
        }finally {
            if(s!=null)
                try {
                    s.close();
                }catch (IOException e){
                    System.out.println("close:"+e.getMessage());}
        }
    }
}