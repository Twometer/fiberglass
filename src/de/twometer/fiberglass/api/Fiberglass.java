package de.twometer.fiberglass.api;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.di.ServiceBuilder;
import de.twometer.fiberglass.di.ServiceProvider;
import de.twometer.fiberglass.hosting.HostManager;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.hosting.impl.ControllerHost;
import de.twometer.fiberglass.hosting.impl.FallbackHost;
import de.twometer.fiberglass.hosting.impl.StaticFileHost;
import de.twometer.fiberglass.hosting.impl.StaticFileProvider;
import de.twometer.fiberglass.photon.PhotonPageService;
import de.twometer.fiberglass.server.HttpConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fiberglass {

    private final List<IHost> hosts = new ArrayList<>();

    private final ServiceProvider services = new ServiceProvider();

    private final HttpConfig httpConfig = new HttpConfig();

    private HostManager hostManager;

    public <T> ServiceBuilder<T> addService(Class<T> service) {
        return services.register(service);
    }

    public void addController(Class<? extends Controller> controllerClass) {
        hosts.add(new ControllerHost<>(controllerClass));
    }

    public void addStaticFiles(String folder, Class<?> resourceOwner) {
        var host = new StaticFileHost();

        addService(StaticFileProvider.class)
                .configure(p -> p.initialize(folder, resourceOwner.getClassLoader()))
                .bind(host::setFileProvider);

        hosts.add(host);
    }

    public void addPhotonPages() {
        addService(PhotonPageService.class);
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public void start() throws IOException {
        if (hostManager == null) {
            addFallbackHost();

            InstanceProvider instanceProvider = InstanceProvider.create(services);

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
