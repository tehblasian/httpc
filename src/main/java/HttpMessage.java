import java.util.HashMap;
import java.util.Map;

public abstract class HttpMessage {
    public final String HTTP_VERSION = "HTTP/1.0";
    private Map<String, String> headers;
    private String data;

    public HttpMessage() {
        this.headers = new HashMap<>();
    }

    protected abstract String getStartLine();

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public String getHeaders() {
        StringBuilder stringBuilder = new StringBuilder();
        this.headers.forEach((name, value) -> stringBuilder.append(String.format("%s: %s\r\n", name, value)));
        return stringBuilder.toString();
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public final String toString() {
        return getStartLine() + getHeaders() + "\r\n" + this.data;
    }
}
