import java.io.*;
import java.net.*;


public class TCPClient {
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.valueOf(args[1]);
        try (Socket sock = new Socket(host, port)) {
//            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            System.out.println("TCP: Connected to " + host + " on port " + port + "\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            sock.close();
            in.close();
//            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}