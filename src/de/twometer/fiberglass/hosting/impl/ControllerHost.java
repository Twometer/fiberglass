package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.request.IRequest;
import de.twometer.fiberglass.response.IResponse;

public class ControllerHost<T extends Controller> implements IHost {

    private final Class<T> controllerClass;

    private Controller controller;

    public ControllerHost(Class<T> controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public void initialize(InstanceProvider instanceProvider) {
        controller = instanceProvider.createInstance(controllerClass);
    }

    @Override
    public boolean match(String requestUri) {
        return controller.getRouteMatcher().matches(requestUri);
    }

    @Override
    public IResponse serve(IRequest request) {
        return null;
    }
}
