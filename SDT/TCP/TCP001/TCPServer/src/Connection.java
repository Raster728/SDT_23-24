import java.net.*;
import java.io.*;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;

    Socket clientSocket;

    public Connection (Socket aClientSocket) {
        try {
            // inicializa variáveis
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out = new DataOutputStream( clientSocket.getOutputStream());
            this.start(); //executa o método run numa thread separada

        } catch(IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }

    public void run(){
        try {
            Person P = null;
            ObjectInputStream ois = new ObjectInputStream(in);
            try{
                P = (Person) ois.readObject();
                //String Pname = P.getName();
                String PLocality = P.getName();
                out.writeUTF(PLocality); //envia a mensagem (resposta) ao cliente
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally{
            try {
                clientSocket.close();
            }catch (IOException e){
                /*close failed*/
            }
        }
    }
}
