import java.net.*;
import java.io.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Teste {
    @Test
    public void test() throws IOException {

        DatagramSocket aSocket = null;

        //try {

        aSocket = new DatagramSocket();
        aSocket.setSoTimeout(5000);
        int nMessage=1;

        String m= "vou enviar esta mensagem ao servidor";

        InetAddress aHost = InetAddress.getByName("localhost");

        int serverPort = 6789;
        while(nMessage < 10) {
            if(nMessage == 4 || nMessage == 7) nMessage++;
            byte[] mb= (String.valueOf(nMessage).concat(",").concat(m)).getBytes();

            DatagramPacket reply = sendDatagramAndWaitForResponse(aSocket, aHost, serverPort, mb);
            if(nMessage > 4)
                assertEquals("waitingfor,"+4, new String(reply.getData()).trim());
            else
                assertEquals(m, new String(reply.getData()).trim());
            nMessage++;
        }

        byte[] mb= (String.valueOf(4).concat(",").concat(m)).getBytes();
        DatagramPacket reply = sendDatagramAndWaitForResponse(aSocket, aHost, serverPort, mb);
        assertEquals("waitingfor,"+7, new String(reply.getData()).trim());

        mb= (String.valueOf(7).concat(",").concat(m)).getBytes();
        reply = sendDatagramAndWaitForResponse(aSocket, aHost, serverPort, mb);
        assertEquals(m, new String(reply.getData()).trim());

        mb= (String.valueOf(10).concat(",").concat(m)).getBytes();
        reply = sendDatagramAndWaitForResponse(aSocket, aHost, serverPort, mb);
        assertEquals(m, new String(reply.getData()).trim());

        //}catch (SocketException e){System.out.println("Socket: " + e.getMessage()); fail("");

        //}catch (IOException e){System.out.println("IO: " + e.getMessage()); fail("");

        //  }finally {if(aSocket != null) aSocket.close();}

    }

    private DatagramPacket sendDatagramAndWaitForResponse(DatagramSocket aSocket, InetAddress aHost, int serverPort,
                                                          byte[] mb) throws IOException {
        DatagramPacket request = new DatagramPacket(mb,  mb.length, aHost, serverPort);

        aSocket.send(request);

        byte[] buffer = new byte[1000];

        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

        aSocket.receive(reply);
        System.out.println("Enviei/recebi a mensagem: " + new String(reply.getData()));
        return reply;
    }
}
