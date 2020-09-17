package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.IResponse;

public class ControllerHost<T extends Controller> implements IHost {

    private final Class<T> controllerClass;

    private Controller controller;

    private ControllerInvoker invoker;

    public ControllerHost(Class<T> controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public void initialize(InstanceProvider instanceProvider) {
        controller = instanceProvider.createInstance(controllerClass);
        invoker = new ControllerInvoker(controllerClass, controller);
    }

    @Override
    public boolean match(String requestUri) {
        return invoker.getController().getRouteMatcher().matches(requestUri);
    }

    @Override
    public IResponse serve(HttpRequest request) {
        var parsedRoute = controller.getRouteMatcher().parseRoute(request.getRequestUri());
        return invoker.invoke(new ControllerRequestContext(request, parsedRoute));
    }


}
