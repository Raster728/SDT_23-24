import java.net.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UDPClient {

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        int sequenceNumber = 1; // Used to number the messages sent to the server

        try {
            aSocket = new DatagramSocket();

            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            String message = "Mensagem enviada pelo cliente"; // Create a message without sequence number

            while (sequenceNumber < 10) {
                if (sequenceNumber == 4 || sequenceNumber == 7)
                    sequenceNumber++;

                String fullMessage = sequenceNumber + "," + message; // Add sequence number
                byte[] m = fullMessage.getBytes(); // Convert the message to bytes

                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort); // Create a packet with destination information
                aSocket.send(request); // Send the message

                byte[] buffer = new byte[1000]; // Prepare to receive the response

                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply); // Receive the response

                // Compare the received data as byte arrays
                assertEquals(fullMessage, new String(reply.getData(), 0, reply.getLength()).trim());

                sequenceNumber++;
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}
