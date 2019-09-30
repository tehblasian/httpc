package http;

import java.util.ArrayList;
import java.util.List;

public class HttpGetRequest extends HttpRequest {
    private static String HTTP_GET_METHOD = "GET";

    private String uri;

    public HttpGetRequest(String uri) {
        super(HTTP_GET_METHOD, uri);
        this.uri = uri;
    }

    protected String getHostLine() {
        return String.format("Host: %s", this.uri);
    }

    public List<String> getOutputLines() {
        List<String> lines = new ArrayList<String>();
        lines.add(this.getStartLine());
        lines.add(this.getHostLine());

        return lines;
    }
}
