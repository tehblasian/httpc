package TCP;

import TCP.TCPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.List;

import http.*;

public class TCPClientImpl implements TCPClient {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private String host;
    private int port;

    public TCPClientImpl() {

    }

    public TCPClientImpl(String host, int port) throws IOException {
        this.connect(host, port);
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect(String host, int port) throws IOException {
        this.socket = new Socket(InetAddress.getByName(host), port);
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public String read() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = this.input.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void send(HttpRequest httpRequest) throws IOException {
        List<String> lines = httpRequest.getOutputLines();

        for (String line : lines) {
            System.out.println(line);
            this.output.println(line);
        }
    }

    @Override
    public void stopConnection() throws IOException {
        this.output.close();
        this.input.close();
        this.socket.close();
    }

    public String getUri() {
        return String.format("%s:%s", this.host, this.port);
    }

    public String sendAndRead(HttpRequest httpRequest) throws IOException {
        this.send(httpRequest);
        String response = this.read();
        this.stopConnection();

        return response;
    }
}
