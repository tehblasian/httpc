package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpMessage {
    public final String HTTP_VERSION = "HTTP/1.0";
    protected Map<String, String> headers;
    private String data;

    public HttpMessage() {
        this.headers = new HashMap<>();
    }

    protected abstract String getStartLine();

    public List<String> getHeaders() {
        List<String> formattedHeaders = new ArrayList<String>();
        this.headers.forEach((name, value) -> formattedHeaders.add(String.format("%s: %s\r\n", name, value)));
        return formattedHeaders;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public final String toString() {
        return getStartLine() + getHeaders() + "\r\n" + this.data;
    }
}
