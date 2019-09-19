package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Command(name = "httpc",
        version = "1.0.0",
        description = "httpc is a cURL-like application, but supports HTTP protocol only.")
public class Httpc implements Runnable {
    @Spec
    private CommandSpec spec;

    @Option(names = "-h", description = "Associates headers to the HTTP request with the format 'key:value'.", paramLabel = "key:value")
    private List<String> headers;

    public Httpc() {
        this.headers = new ArrayList<>();
    }

    @Override
    public void run() {
        Map<String, String> parsedHeaders = null;
        if (headers.size() > 0) {
            validateHeaders(headers);
            parsedHeaders = parseHeaders(headers);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Httpc()).execute(args);
        System.exit(exitCode);
    }

    private void validateHeaders(List<String> headers) {
        headers.forEach(header -> {
            if (!header.matches("([\\w-]+):(.*)")) {
                throw new ParameterException(spec.commandLine(), String.format("Invalid header format for header %s", header));
            }
        });
    }

    private Map<String, String> parseHeaders(List<String> headers) {
        return headers
                .stream().
                collect(Collectors.toMap(
                        header -> header.split(":")[0],
                        header -> header.split(":")[1])
                );
    }
}
