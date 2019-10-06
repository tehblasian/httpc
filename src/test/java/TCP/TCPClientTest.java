package TCP;

import http.HttpRequest;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class TCPClientTest {

    @Test
    public void send() throws IOException {
        String host = "www.httpbin.org";
        int port = 80;
        TCPClient client = new TCPClientImpl(host, port);

        HttpRequest mockRequest = mock(HttpRequest.class);
        when(mockRequest.getOutputLines()).thenReturn(Collections.emptyList());
        client.send(mockRequest);
        verify(mockRequest).getOutputLines();
    }
}
