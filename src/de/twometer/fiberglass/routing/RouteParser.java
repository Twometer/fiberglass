package de.twometer.fiberglass.routing;

import de.twometer.fiberglass.api.Controller;

public class RouteParser {

    public static RouteMatcher parse(Controller controller) {
        return new RouteMatcher();
    }

}
