package cli;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpcTest {
    private Httpc httpc;

    @Before
    public void setup() {
        this.httpc = new Httpc();
    }

    @Test
    public void parseHeaders() {
        List<String> headers = Arrays.asList("Content-Type: application/json", "Content-Length: 21");
        Map<String, String> parsedHeaders = this.httpc.parseHeaders(headers);
        assertEquals(headers.size(), parsedHeaders.size());
    }
}
