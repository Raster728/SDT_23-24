import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
    ObjectInputStream in;
    ObjectOutputStream  out;
    Socket clientSocket;

    public Connection (Socket aClientSocket) {
        try {
            // inicializa variáveis
            clientSocket = aClientSocket;
            in = new ObjectInputStream ( clientSocket.getInputStream());
            out =new ObjectOutputStream ( clientSocket.getOutputStream());

            this.start(); //executa o método run numa thread separada

        } catch(IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }


    public void run(){
        try {

            Person person = (Person) in.readObject();
            System.out.println("Recebido: " + person);

            // Envie o nome da pessoa de volta como resposta
            String response = "Nome da pessoa: " + person.getName();
            out.writeObject(person.getName());

        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
                out.close();
                clientSocket.close();
            }catch (IOException e){
                /*close failed*/
            }
        }
    }
}