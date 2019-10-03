package http;

import java.util.ArrayList;
import java.util.List;

public class HttpPostRequest extends HttpRequest {
    private static String HTTP_METHOD = "POST";

    private String uri;

    public HttpPostRequest(String uri) {
        super(HTTP_METHOD, uri);
        this.uri = uri;
    }

    protected String getHostLine() {
        return String.format("Host: %s", this.uri);
    }

    public List<String> getOutputLines() {
        List<String> lines = new ArrayList<String>();
        lines.add(this.getStartLine());
        lines.add(this.getHostLine());
        lines.addAll(this.getHeaders());
        return lines;
    }
}