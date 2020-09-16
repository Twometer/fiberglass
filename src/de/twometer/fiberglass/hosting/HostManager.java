package de.twometer.fiberglass.hosting;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.server.HttpConfig;
import de.twometer.fiberglass.server.HttpServer;

import java.util.List;

public class HostManager {

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

        httpServer = new HttpServer(httpConfig);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop();
    }

}
