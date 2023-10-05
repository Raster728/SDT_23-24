import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer {

    public static void main(String args[]) {
        HashMap<Integer, String> mensagensRecebidas = new HashMap<Integer, String>();
        HashMap<Integer, String> mensagensTemporárias = new HashMap<Integer, String>();
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

                    mensagensRecebidas.put(receivedSequenceNumber, receivedMessage);

                    aSocket.send(reply);
                    expectedSequenceNumber++;

                    Set<Integer> keys = mensagensRecebidas.keySet();
                    // Convert the set to a list

                    List<Integer> keyList = new ArrayList<>(keys);


                    Integer lastKey = keyList.get(keyList.size() - 1);

                    while (mensagensTemporárias.containsKey(lastKey + 1)) {
                        String tempMessage = mensagensTemporárias.remove(lastKey + 1);
                        mensagensRecebidas.put(lastKey + 1, tempMessage);
                        expectedSequenceNumber++;
                        lastKey++;
                        String numero = String.valueOf(lastKey);
                        String mensagem = numero + "," + receivedMessage;
                        System.out.println("Mensagem recebida: " + mensagem);
                        byte[] replyData2 = mensagem.getBytes();
                        DatagramPacket resposta = new DatagramPacket(replyData2, replyData2.length, request.getAddress(), request.getPort());
                        aSocket.send(resposta);
                    }

                } else {
                    String replyMessage = "waitingfor," + expectedSequenceNumber;
                    DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.length(),
                            request.getAddress(), request.getPort());
                    mensagensTemporárias.put(receivedSequenceNumber, receivedMessage);
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
