package http;

import http.HttpMessage;

import java.util.List;
import java.util.Map;

public abstract class HttpRequest extends HttpMessage {
    private String method;
    private String uri;

    public HttpRequest(String method, String uri) {
        super();
        this.method = method;
        this.uri = uri;
    }

    public HttpRequest withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    protected String getStartLine() {
        return String.format("%s %s %s\n", this.method.toUpperCase(), '/', this.HTTP_VERSION);
    }

    public abstract List<String> getOutputLines();
}
