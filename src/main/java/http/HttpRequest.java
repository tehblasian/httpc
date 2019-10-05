package http;

import http.HttpMessage;

import java.util.List;
import java.util.Map;

public abstract class HttpRequest extends HttpMessage {
    private String method;
    private String uri;

    protected String data;

    public HttpRequest(String method, String uri) {
        super();
        this.method = method;
        this.uri = uri;
    }

    public HttpRequest withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest withData(String data) {
        this.data = data;
        this.addContentLengthToHeaders();
        return this;
    }

    private void addContentLengthToHeaders() {
        int contentLength = this.data.getBytes().length;
        this.headers.put("Content-Length", Integer.toString(contentLength));
    }

    @Override
    protected String getStartLine() {
        // WILL NEED TO PARSE PATH FROM URI, THEN PASS INTO HERE WHERE '/' IS WRITTER
        return String.format("%s %s %s", this.method.toUpperCase(), "/", this.HTTP_VERSION);
    }

    public abstract List<String> getOutputLines();

    @Override
    public String toString() {
        return super.toString() + (this.data != null ? this.data : "");
    }
}
