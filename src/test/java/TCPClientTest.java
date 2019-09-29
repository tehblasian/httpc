import TCP.TCPClient;
import TCP.TCPClientImpl;
import org.junit.Test;

import java.io.IOException;

public class TCPClientTest {

    @Test
    public void request() throws IOException {
        TCPClient client = new TCPClientImpl();
        String host = "www.httpbin.org";
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
}
