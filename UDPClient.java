import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);


            System.out.println("UDP: Connected to " + host + " on port " + port + "\n");
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("quotes:" + received);
            socket.close();
        } catch (IOException e) {
            System.out.println("cannot connect");
            e.printStackTrace();
        }

    }
}

