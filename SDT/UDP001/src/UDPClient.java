import java.net.*;
import java.io.*;

public class UDPClient{

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        int sequenceNumber = 4; //utilizamos para numerar as mensagens enviadas para o servidor



        try {
            aSocket = new DatagramSocket();

            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            String message = sequenceNumber + "," + "Mensagem enviada pelo cliente"; //criação de mensagem
            byte[] m = message.getBytes(); // conversão da mensagem em bytes

            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort); //criação do objeto com informações de destino
            aSocket.send(request); //envio da mensagem

            byte[] buffer = new byte[1000]; //preparação da receção da mensagem
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length); //preparação da receção da mensagem
            aSocket.receive(reply); //recebimento da resposta

            String replyMessage = new String(reply.getData()).trim(); //processamento da resposta, resposta convertida em bytes e os espaços em branco removidos pelo trim()

                if (replyMessage.equals("Estou à espera da mensagem "+ (sequenceNumber + 1))) {
                    System.out.println("O servidor está à espera da mensagem " + (sequenceNumber + 1));
                }else{
                    System.out.println("Resposta: " + replyMessage);
                    sequenceNumber++;
                }
            //if -> verifica resposta, caso o server esteja À espera de uma mensagem, uma mensagem informativa é exibida e o número da sequência incrementadp

        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}

    }
}
