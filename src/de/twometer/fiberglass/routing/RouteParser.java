package de.twometer.fiberglass.routing;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.api.annotation.Route;
import de.twometer.fiberglass.util.StringUtil;

public class RouteParser {

    public static RouteMatcher parse(Controller controller) {
        var route = getRoute(controller);
        var parts = StringUtil.split(route, '/', true);
        var matcher = new RouteMatcher(route);

        for (var part : parts) {
            if (part.startsWith("{") && part.endsWith("}")) {
                var content = part.substring(1, part.length() - 1);
                matcher.addPart(new RoutePart(RoutePart.Type.Variable, content));
            } else {
                matcher.addPart(new RoutePart(RoutePart.Type.Constant, part));
            }

        }

        return matcher;
    }

    private static String getRoute(Controller controller) {
        Route route = controller.getClass().getAnnotation(Route.class);
        if (route == null)
            throw new IllegalArgumentException("Cannot get the route for a controller with no route annotation");

        return route.value();
    }

}
