package http;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HttpMessageTest {

    @Test
    public void testGetHeaders_formatsAllHeaders() {
        HttpMessage request = new HttpRequest("", "") {
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
    public void testGetHeaders_correctlyFormatsHeaders() {
        HttpMessage request = new HttpRequest("", "") {
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
