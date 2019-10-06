package http;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpGetRequest extends HttpRequest {
    private static String HTTP_METHOD = "GET";

    public HttpGetRequest(URL url) {
        super(HTTP_METHOD, url);
    }

    protected String getHostLine() {
        return String.format("Host: %s", this.url.getHost());
    }

    public List<String> getOutputLines() {
        List<String> lines = new ArrayList<String>();
        lines.add(this.getStartLine());
        lines.add(this.getHostLine());
        lines.addAll(this.getHeaders());
        lines.add("");
        return lines
                .stream()
                .map(line -> line + "\r\n")
                .collect(Collectors.toList());
    }
}
