package http;

import TCP.TCPClient;
import TCP.TCPClientImpl;

import java.io.IOException;

public class HttpClientImpl implements HttpClient {
    private final int DEFAULT_PORT = 80;

    @Override
    public HttpResponse send(HttpRequest request) throws IOException {
        TCPClient client = new TCPClientImpl(request.url.getHost(), DEFAULT_PORT);
        String raw = client.sendAndRead(request);
        HttpResponse response = HttpResponse.fromRaw(raw);
        return response;
    }
}
