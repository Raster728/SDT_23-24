import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // bloqueia à espera de uma conexão

                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Object data = in.readObject();
                System.out.println("Received: " + data);
                Connection c = new Connection(clientSocket); // processa o pedido
            }
        } catch(IOException e) {
            System.out.println("Listen :"+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}