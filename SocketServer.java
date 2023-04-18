import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketServer {
    private static ExecutorService exec = Executors.newFixedThreadPool(5);
    private static String[] Quotes = {
            "Be yourself; everyone else is already taken",
            "Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.",
            "Life is pain, Highness. Anyone who says differently is selling something.",
            "So many books, so little time.",
            "A room without books is like a body without a soul.",
            "You only live once, but if you do it right, once is enough.",
            "Life is pain, Highness. Anyone who says differently is selling something.(Princess Bride)",
            "You seem a decent fellow. I hate to kill you.(Princess Bride)"
    };

    public static void main(String[] arg) throws IOException {
        exec.submit(() -> {
            try {
                handleRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        DatagramSocket udpServer = new DatagramSocket(17);
        exec.submit(() -> handleRequestUDP(udpServer));
    }

    public static void handleRequest() throws IOException {
        ServerSocket server = new ServerSocket(17);
        Socket socket = null;
        while ((socket = server.accept()) != null) {
            System.out.println("Accepted TCP Client request");
            final Socket threadSocket = socket;
            handleRequestTCP(threadSocket);
        }
        server.close();
    }

    public static void handleRequestUDP(DatagramSocket socket) {
        try{
            while (true) {
                String quote = getRandomQuote();
                byte[] buf = quote.getBytes();
                byte[] receiveData = new byte[256];

                DatagramPacket pack = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(pack);
                System.out.print("Accepted UDP Client request");
                System.out.println();

                InetAddress address = pack.getAddress();
                int port = pack.getPort();
                pack = new DatagramPacket(buf, buf.length, address, port);
//            DatagramSocket socket = new DatagramSocket();
                socket.send(pack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void handleRequestTCP(Socket Socket) {
        try{
            OutputStream out = Socket.getOutputStream();
            String quote = getRandomQuote();
            out.write(quote.getBytes());
            System.out.println();

            out.close();
            Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomQuote(){
        Random rand = new Random();
        int index = rand.nextInt(Quotes.length);
        return Quotes[index];
    }

}

