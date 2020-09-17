package de.twometer.fiberglass.routing;

import de.twometer.fiberglass.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RouteMatcher {

    private final String route;

    private final List<RoutePart> routeParts = new ArrayList<>();

    public RouteMatcher(String route) {
        this.route = route;
    }

    public void addPart(RoutePart part) {
        routeParts.add(part);
    }

    public ParsedRequestRoute parseRoute(String route) {
        var result = new ParsedRequestRoute();
        var parts = StringUtil.split(route, '/', true);

        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];

            if (i < routeParts.size()) {
                var refPart = routeParts.get(i);

                if (refPart.getType() == RoutePart.Type.Variable)
                    result.getPathParameters().put(refPart.getValue(), StringUtil.urlDecode(part));
            } else {
                result.setAction(part);
            }
        }

        return result;
    }

    public boolean matches(String candidate) {
        var parts = StringUtil.split(candidate, '/', true);

        if (parts.length == 0) {
            return candidate.equalsIgnoreCase(route);
        }

        for (int i = 0; i < routeParts.size(); i++) {
            var part = parts[i];
            var refPart = routeParts.get(i);

            if (refPart.getType() == RoutePart.Type.Constant && !refPart.getValue().equalsIgnoreCase(part))
                return false;
        }

        return true;
    }

}
