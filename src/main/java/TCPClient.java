import java.io.IOException;
import java.net.UnknownHostException;

public interface TCPClient {
    void connect(String ip, int port) throws UnknownHostException, IOException;
    String read() throws IOException;
    void send(String content) throws IOException;
    void stopConnection() throws IOException;
}
