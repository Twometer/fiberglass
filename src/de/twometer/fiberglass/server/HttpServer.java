package de.twometer.fiberglass.server;

import de.twometer.fiberglass.request.HttpRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final HttpConfig config;

    private ServerSocket serverSocket;

    private ExecutorService executorService;

    private HttpCallback callback;

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

            var request = new HttpRequest();
            request.read(inputStream);

            var response = callback.handleRequest(request);
            response.write(outputStream);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }
}
