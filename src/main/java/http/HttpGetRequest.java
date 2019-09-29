package http;

import java.util.ArrayList;
import java.util.List;

public class HttpGetRequest extends HttpRequest {
    private String method;
    private String uri;

    public HttpGetRequest(String method, String uri) {
        super(method, uri);
        this.method = method;
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
