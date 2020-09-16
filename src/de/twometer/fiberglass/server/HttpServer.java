package de.twometer.fiberglass.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final HttpConfig config;

    private ServerSocket serverSocket;

    private ExecutorService executorService;

    private volatile boolean isRunning;

    public HttpServer(HttpConfig config) {
        this.config = config;
    }

    public void start() throws IOException {
        executorService = Executors.newCachedThreadPool();
        serverSocket = new ServerSocket(config.getPort());

        isRunning = true;
        new Thread(this::beginListening).start();
    }

    public void stop() throws IOException {
        isRunning = false;
        executorService.shutdown();
        serverSocket.close();
    }

    private void beginListening() {
        try {
            while (isRunning) {
                var socket = serverSocket.accept();
                executorService.submit(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket socket) {
        try {
            var inputStream = socket.getInputStream();
            var outputStream = socket.getOutputStream();

            var writer = new PrintWriter(new OutputStreamWriter(outputStream));
            var reader = new BufferedReader(new InputStreamReader(inputStream));

            var firstLine = true;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty())
                    break;

                if (firstLine) {
                    firstLine = false;
                    var parts = line.split(" ");
                    if (parts[0].equals("GET") && parts[2].equals("HTTP/1.1")) {
                        System.out.println("Requesting: " + parts[1]);

                        var reply = "Currently requesting '" + parts[1] + "'";

                        writer.println("HTTP/1.1 200 OK");
                        writer.println("Content-Length: " + reply.length());
                        writer.println();
                        writer.println(reply);
                        writer.flush();
                    }
                } else {
                    System.out.println("HEADER: " + line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
