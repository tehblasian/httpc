package http;

import java.io.IOException;

public interface HttpClient {
    HttpResponse send(HttpRequest request) throws IOException;
}
