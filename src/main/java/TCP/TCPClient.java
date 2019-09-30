package TCP;

import java.io.IOException;
import java.net.UnknownHostException;

import http.HttpRequest;

public interface TCPClient {
    void connect(String host, int port) throws UnknownHostException, IOException;
    String read() throws IOException;
    void send(HttpRequest httpRequest) throws IOException;
    void stopConnection() throws IOException;
    String getUri();
    String sendAndRead(HttpRequest httpRequest) throws IOException;
}
