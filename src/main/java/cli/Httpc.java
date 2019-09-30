package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import TCP.*;
import http.*;

@Command(name = "httpc",
        version = "1.0.0",
        description = "httpc is a cURL-like application, but supports HTTP protocol only.",
        subcommands = HelpCommand.class)
public class Httpc {
    @Spec
    private CommandSpec spec;

    @Option(names = "-h", description = "Associates headers to the HTTP request with the format 'key:value'.", paramLabel = "key:value")
    private List<String> headers;

    public Httpc() {
        this.headers = new ArrayList<>();
    }

    @Command(description = "Executes an HTTP GET request for a given url")
    public void get(@Parameters(paramLabel = "URL") String url) {
        Map<String, String> parsedHeaders = parseHeaders(this.headers);

        try {
            TCPClient tcpClient = new TCPClientImpl(url, 80);

            HttpRequest getRequest = new HttpGetRequest(tcpClient.getUri()).withHeaders(parsedHeaders);
            String response = tcpClient.sendAndRead(getRequest);
            System.out.println(response);
        }
        catch (IOException e) {
            System.out.println("Error sending or reading request with TCP");
        }
    }

    @Command(description = "Executes an HTTP POST request for a given url")
    public void post(
            @Parameters(paramLabel = "URL") String url,
            @Option(
                    names = { "-f", "--file" },
                    paramLabel = "FILE",
                    description = "Associates the contents of a file with the body of the HTTP POST request"
            ) File file,
            @Option(
                    names = { "-d", "--data" },
                    paramLabel = "DATA",
                    description = "Associates inline data with the body of the HTTP POST request"
            ) String data
    ) {
        if (file != null && data != null) {
            throw new ParameterException(spec.commandLine(), "Either [-d] or [-f] can be used, but not both");
        }
        Map<String, String> parsedHeaders = parseHeaders(this.headers);
        // TODO: send post request using TCP.TCPClient
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Httpc()).execute(args);
        System.exit(exitCode);
    }

    protected Map<String, String> parseHeaders(List<String> headers) {
        validateHeaders(headers);
        return headers
                .stream().
                collect(Collectors.toMap(
                        header -> header.split(":")[0],
                        header -> header.split(":")[1])
                );
    }

    private void validateHeaders(List<String> headers) {
        headers.forEach(header -> {
            if (!header.matches("([\\w-]+):(.*)")) {
                throw new ParameterException(spec.commandLine(), String.format("Invalid header format for header %s", header));
            }
        });
    }
}
