import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            String mensagem = "Hello World!!";

            // Create a multicast packet
            DatagramPacket packet = new DatagramPacket(mensagem.getBytes(), mensagem.getBytes().length);
            packet.setAddress(InetAddress.getByName("224.0.0.1"));
            packet.setPort(2022);

            // Send the packet
            socket.send(packet);

            // Create a new DatagramPacket object to receive the response
            DatagramPacket reply = new DatagramPacket(new byte[1000], 1000);

            // Receive the response
            socket.receive(reply);

            // Print the received message
            System.out.println("Mensagem recebida: " + new String(reply.getData(), 0, reply.getLength()).trim());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null)
                socket.close();
        }
    }
}
