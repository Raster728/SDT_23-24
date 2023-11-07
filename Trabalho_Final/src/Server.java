import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) throws IOException {
        MulticastSocket socket = null;

        try {
            socket = new MulticastSocket(2022);
            socket.joinGroup(InetAddress.getByName("224.0.0.1"));
            System.out.println("///////////////////////////////////////////");
            System.out.println("Server is running...");
            byte[] buffer = new byte[1000];

            while (true) {
                System.out.println("///////////////////////////////////////////");
                System.out.println("Waiting for a message from a client...");
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData());System.out.println("///////////////////////////////////////////");
                System.out.println("Message received from client: " + message.trim());
                byte[] replyData = message.getBytes();
                DatagramPacket reply = new DatagramPacket(replyData, replyData.length, packet.getAddress(), packet.getPort());
                socket.send(reply);


            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (socket != null)
                socket.leaveGroup(InetAddress.getByName("224.0.0.1"));
                socket.close();
        }


    }
}
