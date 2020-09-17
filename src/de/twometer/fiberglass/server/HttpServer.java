package de.twometer.fiberglass.server;

import de.twometer.fiberglass.http.StatusCode;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.ErrorResponse;
import de.twometer.fiberglass.response.IResponse;
import de.twometer.fiberglass.response.ResponseFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class HttpServer {

    private final HttpConfig config;

    private ServerSocket serverSocket;

    private HttpExecutor executor;

    private HttpCallback callback;

    private volatile boolean isRunning;

    public HttpServer(HttpConfig config) {
        this.config = config;
    }

    public void start() throws IOException {
        executor = new HttpExecutor(2, 8, 30, TimeUnit.SECONDS);
        serverSocket = new ServerSocket(config.getPort());

        isRunning = true;
        new Thread(this::beginListening).start();
    }

    public void stop() throws IOException {
        isRunning = false;
        executor.shutdown();
        serverSocket.close();
    }

    private void beginListening() {
        try {
            while (isRunning) {
                var socket = serverSocket.accept();
                executor.submit(() -> handleConnection(socket));
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

            var response = getSafeResponse(request);
            response.write(outputStream);

            outputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IResponse getSafeResponse(HttpRequest request) {
        try {
            return callback.handleRequest(request);
        } catch (NumberFormatException e) {
            return new ErrorResponse(StatusCode.BadRequest, e.getMessage());
        } catch (Throwable t) {
            return ResponseFactory.newInternalServerError(t);
        }
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }
}
