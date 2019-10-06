package http;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class HttpMessageTest {

    @Test
    public void testGetHeaders_formatsAllHeaders() throws MalformedURLException {
        HttpMessage request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        request.headers.put("Test1", "Header1");
        request.headers.put("Test2", "Header2");
        request.headers.put("Test3", "Header3");
        List<String> formattedHeaders = request.getHeaders();
        assertEquals(formattedHeaders.size(), 3);
    }

    @Test
    public void testGetHeaders_correctlyFormatsHeaders() throws MalformedURLException {
        HttpMessage request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        request.headers.put("Test1", "Header1");
        List<String> formattedHeaders = request.getHeaders();
        assertEquals(formattedHeaders.get(0), "Test1: Header1");
    }
}
