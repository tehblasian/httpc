package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpMessage {
    public final String HTTP_VERSION = "HTTP/1.0";
    protected Map<String, String> headers;

    public HttpMessage() {
        this.headers = new HashMap<>();
    }

    protected abstract String getStartLine();

    public List<String> getHeaders() {
        List<String> formattedHeaders = new ArrayList<String>();
        this.headers.forEach((name, value) -> formattedHeaders.add(String.format("%s: %s", name, value)));
        return formattedHeaders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStartLine() + "\r\n");
        this.getHeaders().forEach(header -> sb.append(header + "\r\n"));
        sb.append("\r\n");

        return sb.toString();
    }
}
