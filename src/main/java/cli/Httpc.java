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
import java.net.URL;
import java.util.*;
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

    @Command(description = "Executes an HTTP GET request for a given url")
    public void get(
            @Parameters(paramLabel = "URL") String url,
            @Option(names = { "-v", "--verbose" }, description = "Prints the response details") boolean verbose,
            @Option(names = "-h", description = "Associates headers to the HTTP request with the format 'key:value'.", paramLabel = "key:value") List<String> headers
    ) {
        Map<String, String> parsedHeaders = parseHeaders(headers);
        try {
            URL u = new URL(url);
            TCPClient tcpClient = new TCPClientImpl(u.getHost(), 80);

            HttpRequest getRequest = new HttpGetRequest(u).withHeaders(parsedHeaders);
            String response = tcpClient.sendAndRead(getRequest);
            if (verbose) {
                System.out.println(response);
            }
        }
        catch (IOException e) {
            System.out.println("Error sending or reading get request with TCP");
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
            ) String data,
            @Option(names = { "-v", "--verbose" }, description = "Prints the response details")
            boolean verbose,
            @Option(names = "-h", description = "Associates headers to the HTTP request with the format 'key:value'.", paramLabel = "key:value") List<String> headers
    ) {
        if (file != null && data != null) {
            throw new ParameterException(spec.commandLine(), "Either [-d] or [-f] can be used, but not both");
        }
        Map<String, String> parsedHeaders = parseHeaders(headers);

        try {
            URL u = new URL(url);
            TCPClient tcpClient = new TCPClientImpl(u.getHost(), 80);
            HttpRequest postRequest = new HttpPostRequest(u).withHeaders(parsedHeaders);
            addDataOrFileToPostRequest(postRequest, data, file);

            String response = tcpClient.sendAndRead(postRequest);
            if (verbose) {
                System.out.println(response);
            }
        }
        catch (IOException e) {
            System.err.println(e);
            System.out.println("Error sending or reading post request with TCP");
        }
    }

    private void addDataOrFileToPostRequest(HttpRequest postRequest, String data, File file) {
        if (data != null) {
            postRequest.withData(data);
        }
        if (file != null) {
            postRequest.withFile(file);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Httpc()).execute(args);
        System.exit(exitCode);
    }

    protected Map<String, String> parseHeaders(List<String> headers) {
        if (headers == null) {
            return new HashMap<>();
        }
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
