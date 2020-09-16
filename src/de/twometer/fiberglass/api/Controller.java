package de.twometer.fiberglass.api;

import de.twometer.fiberglass.routing.RouteMatcher;
import de.twometer.fiberglass.routing.RouteParser;

public class Controller {

    private final RouteMatcher routeMatcher;

    public Controller() {
        routeMatcher = RouteParser.parse(this);
    }

    public final RouteMatcher getRouteMatcher() {
        return routeMatcher;
    }
}
