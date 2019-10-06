package http;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class HttpRequest extends HttpMessage {
    private String method;
    private String uri;

    protected String data;
    protected File file;

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

    public File getFile() {
        return this.file;
    }

    public HttpRequest withFile(File file) {
        this.file = file;
        this.addContentLengthToHeaders();
        return this;
    }

    private void addContentLengthToHeaders() {
        long contentLength = 0;
        if (this.data != null) {
            contentLength = this.data.getBytes().length;
        } else if (this.file != null) {
            contentLength = this.file.length();
        }
        this.headers.put("Content-Length", Long.toString(contentLength));
    }

    @Override
    protected String getStartLine() {
        // WILL NEED TO PARSE PATH FROM URI, THEN PASS INTO HERE WHERE '/' IS WRITTER
        return String.format("%s %s %s", this.method.toUpperCase(), "/", this.HTTP_VERSION);
    }

    public abstract List<String> getOutputLines();
}
