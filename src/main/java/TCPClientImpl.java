import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class TCPClientImpl implements TCPClient {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClientImpl();
        String host = "www.google.com";
        int port = 80;

        client.connect(host, port);
        client.send("GET / HTTP/1.1");
        client.send("Host: " + host + ":" + port);
        client.send("Connection: Close");
        client.send("\n");

        String response = client.read();
        System.out.println(response);
        client.stopConnection();
    }

    @Override
    public void connect(String host, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(InetAddress.getByName(host), port);
        System.out.println("Connected: " + this.socket.isConnected());
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public String read() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = this.input.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void send(String content) throws IOException {
        this.output.println(content);
    }

    @Override
    public void stopConnection() throws IOException {
        this.output.close();
        this.input.close();
        this.socket.close();
    }

}
