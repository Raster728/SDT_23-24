import java.net.*;
import java.io.*;

public class UDPServer {

    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        int expectedSequenceNumber = 1;

        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String message = new String(request.getData()).trim();
                String[] parts = message.split(",");
                int receivedSequenceNumber = Integer.parseInt(parts[0]);
                String receivedMessage = parts[1];

                if (receivedSequenceNumber == expectedSequenceNumber) {
                    byte[] replyData = parts[1].getBytes();
                    DatagramPacket reply = new DatagramPacket(replyData, replyData.length, request.getAddress(), request.getPort());

                    aSocket.send(reply);
                    expectedSequenceNumber++;
                } else {
                    String replyMessage = "waitingfor," + expectedSequenceNumber;
                    DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.length(),
                            request.getAddress(), request.getPort());
                    aSocket.send(reply);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}
