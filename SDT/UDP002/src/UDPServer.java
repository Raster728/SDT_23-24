import java.net.*;
import java.io.*;
import java.util.HashMap;

public class UDPServer {

    public static HashMap<Integer, String> temporaryHash = new HashMap<Integer, String>();
    public static HashMap<Integer, String> deliveredHash = new HashMap<Integer, String>();
    public static int port;
    public static InetAddress address;

    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        try {
            aSocket = new DatagramSocket(6789);
            int expectedNumber = 1;
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                port = request.getPort();
                address = request.getAddress();

                String msgReceived = new String(request.getData());
                String[] parts = msgReceived.split(",");

                expectedNumber = processDeliveredMessages(expectedNumber, Integer.parseInt(parts[0]), msgReceived);

                if (temporaryHash.isEmpty()) {
                    DatagramPacket reply = new DatagramPacket(parts[1].getBytes(), request.getLength(), request.getAddress(), request.getPort());
                    aSocket.send(reply);
                } else {
                    byte [] m = ("waitingfor," + expectedNumber).getBytes();
                    DatagramPacket reply = new DatagramPacket(m, m.length, request.getAddress(), request.getPort());
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

    public static int processDeliveredMessages(int nLastMessageInOrder, int nCurrentMessage, String currentMessage) {
        if (nCurrentMessage != nLastMessageInOrder) {
            temporaryHash.put(nCurrentMessage, currentMessage);
            return nLastMessageInOrder;
        } else {
            deliveredHash.put(nCurrentMessage, currentMessage);
            int nextMessageNumber = nLastMessageInOrder + 1;

            while (temporaryHash.containsKey(nextMessageNumber)) {
                deliveredHash.put(nextMessageNumber, temporaryHash.get(nextMessageNumber));
                DatagramPacket reply = new DatagramPacket(temporaryHash.get(nextMessageNumber).getBytes(), temporaryHash.get(nextMessageNumber).length(), address, port);
                temporaryHash.remove(nextMessageNumber);

                nextMessageNumber++;
            }

            return nextMessageNumber;
        }
    }
}