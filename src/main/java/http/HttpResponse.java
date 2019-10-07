package http;

import http.HttpMessage;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpResponse extends HttpMessage {
    private int status;
    private String reasonPhrase;
    private String body;

    public HttpResponse() {

    }

    public HttpResponse(int status, String reasonPhrase, String body) {
        super();
        this.status = status;
        this.reasonPhrase = reasonPhrase;
        this.body = body;
    }

    public static HttpResponse fromRaw(String raw) {
        HttpResponse response = new HttpResponse();
        addStatusAndReasonPhraseToResponseFromRaw(raw, response);
        addHeadersToResponseFromRaw(raw, response);
        addBodyToResponseFromRaw(raw, response);
        return response;
    }

    private static void addStatusAndReasonPhraseToResponseFromRaw(String raw, HttpResponse response) {
        Pattern startLineRegex = Pattern.compile("^(HTTP\\/\\d.\\d)\\s(\\d+)\\s(\\w+)$");
        String startLine = raw.split("\n")[0];
        Matcher matcher = startLineRegex.matcher(startLine);
        if (matcher.find()) {
            response.status = Integer.parseInt(matcher.group(2));
            response.reasonPhrase = matcher.group(3);
        }
    }

    private static void addHeadersToResponseFromRaw(String raw, HttpResponse response) {
        Map<String, String> headers = Arrays.stream(raw.split("\n"))
                .filter(line -> line.matches("([\\w-]+):(.*)"))
                .collect(Collectors.toMap(
                        header -> header.split(":")[0],
                        header -> header.split(":")[1])
                );
        response.headers = headers;
    }

    private static void addBodyToResponseFromRaw(String raw, HttpResponse response) {
        String[] split = raw.split("\n\n");
        String body = "";
        if (split.length > 1) {
            body = split[1];
        }
        response.body = body;
    }

    @Override
    protected String getStartLine() {
        return String.format("%s %d %s", this.HTTP_VERSION, this.status, this.reasonPhrase);
    }

    @Override
    public String toString() {
        String headers = getHeaders().stream().collect(Collectors.joining("\n"));
        return String.format("%s\n%s\n\n%s", getStartLine(), headers, this.body);
    }
}
