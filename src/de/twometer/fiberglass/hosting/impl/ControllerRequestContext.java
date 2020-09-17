package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.routing.ParsedRequestRoute;

public class ControllerRequestContext {

    private final HttpRequest request;

    private final ParsedRequestRoute requestRoute;

    public ControllerRequestContext(HttpRequest request, ParsedRequestRoute requestRoute) {
        this.request = request;
        this.requestRoute = requestRoute;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public ParsedRequestRoute getRequestRoute() {
        return requestRoute;
    }

}
