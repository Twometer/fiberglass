package de.twometer.fiberglass.api;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.di.ServiceProvider;
import de.twometer.fiberglass.hosting.HostManager;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.hosting.impl.ControllerHost;
import de.twometer.fiberglass.hosting.impl.FallbackHost;
import de.twometer.fiberglass.hosting.impl.StaticFileHost;
import de.twometer.fiberglass.server.HttpConfig;

import java.util.ArrayList;
import java.util.List;

public class Fiberglass {

    private final List<IHost> hosts = new ArrayList<>();

    private final ServiceProvider serviceProvider = new ServiceProvider();

    private final InstanceProvider instanceProvider = new InstanceProvider(serviceProvider);

    private final HttpConfig httpConfig = new HttpConfig();

    private HostManager hostManager;

    public void addService(Class<?> service) {
        serviceProvider.registerService(service);
    }

    public void addController(Class<? extends Controller> controllerClass) {
        hosts.add(new ControllerHost<>(controllerClass));
    }

    public void addStaticFiles() {
        hosts.add(new StaticFileHost());
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public void start() {
        if (hostManager == null) {
            addFallbackHost();

            hostManager = new HostManager(hosts, httpConfig, instanceProvider);
            hostManager.start();
        } else {
            throw new IllegalStateException("Cannot start app twice.");
        }
    }

    public void stop() {
        if (hostManager != null) {
            hostManager.stop();
        } else {
            throw new IllegalStateException("Cannot stop app that's not running.");
        }
    }

    private void addFallbackHost() {
        hosts.add(new FallbackHost());
    }

}
