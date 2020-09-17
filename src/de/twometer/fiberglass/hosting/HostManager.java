package de.twometer.fiberglass.hosting;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.IResponse;
import de.twometer.fiberglass.server.HttpCallback;
import de.twometer.fiberglass.server.HttpConfig;
import de.twometer.fiberglass.server.HttpServer;

import java.io.IOException;
import java.util.List;

public class HostManager implements HttpCallback {

    private final List<IHost> hosts;

    private final HttpConfig httpConfig;

    private final InstanceProvider instanceProvider;

    private HttpServer httpServer;

    public HostManager(List<IHost> hosts, HttpConfig httpConfig, InstanceProvider instanceProvider) {
        this.hosts = hosts;
        this.httpConfig = httpConfig;
        this.instanceProvider = instanceProvider;
    }

    public void start() {
        for (var host : hosts)
            host.initialize(instanceProvider);

        try {
            httpServer = new HttpServer(httpConfig);
            httpServer.setCallback(this);
            httpServer.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start host manager", e);
        }
    }

    public void stop() {
        try {
            httpServer.stop();
        } catch (IOException e) {
            throw new RuntimeException("Failed to stop host manager", e);
        }
    }

    @Override
    public IResponse handleRequest(HttpRequest request) {
        for (var host : hosts) {
            if (host.match(request.getRequestUri()))
                return host.serve(request);
        }

        throw new IllegalStateException("No host matched the request. Fallback host not registered?");
    }
}
