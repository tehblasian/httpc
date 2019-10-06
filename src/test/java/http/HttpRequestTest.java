package http;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpRequestTest {

    @Test
    public void withData_contentLengthHeaderIsAdded() throws MalformedURLException {
        HttpRequest request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        request.withData("data");
        assertTrue(request.headers.containsKey("Content-Length"));
    }

    @Test
    public void withData_correctContentLengthIsAdded() throws MalformedURLException {
        HttpRequest request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        String data = "data";
        request.withData(data);
        assertEquals(request.headers.get("Content-Length"), Integer.toString(data.getBytes().length));
    }

    @Test
    public void withFile_contentLengthHeaderIsAdded() throws MalformedURLException {
        HttpRequest request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        File file = mock(File.class);
        when(file.length()).thenReturn(7L);
        request.withFile(file);
        assertTrue(request.headers.containsKey("Content-Length"));
    }

    @Test
    public void withFile_correctContentLengthIsAdded() throws MalformedURLException {
        HttpRequest request = new HttpRequest("", new URL("http://test.com")) {
            @Override
            public List<String> getOutputLines() {
                return null;
            }
        };

        File file = mock(File.class);
        when(file.length()).thenReturn(7L);
        request.withFile(file);
        assertEquals(request.headers.get("Content-Length"), Long.toString(file.length()));
    }
}
