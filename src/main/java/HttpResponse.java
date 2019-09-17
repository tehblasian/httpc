import java.util.Map;

public class HttpResponse extends HttpMessage {
    private int status;
    private String reasonPhrase;

    public HttpResponse(int status, String reasonPhrase) {
        super();
        this.status = status;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    protected String getStartLine() {
        return String.format("%s %d %s\n", this.HTTP_VERSION, this.status, this.reasonPhrase);
    }
}
