package de.twometer.fiberglass.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private HttpConfig config;

    private ExecutorService executorService;

    private volatile boolean isRunning;

    public HttpServer(HttpConfig config) {
        this.config = config;
    }

    public void start() {
        executorService = Executors.newCachedThreadPool();
        isRunning = true;
        new Thread(this::beginListening).start();
    }

    public void stop() {
        executorService.shutdown();
        isRunning = false;
    }

    private void beginListening() {
        while (isRunning) {

        }
    }

}
