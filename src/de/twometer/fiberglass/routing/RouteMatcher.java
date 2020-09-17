package de.twometer.fiberglass.routing;

import de.twometer.fiberglass.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteMatcher {

    private final List<RoutePart> routeParts = new ArrayList<>();

    public void addPart(RoutePart part) {
        routeParts.add(part);
    }

    public Map<String, String> getPathParameters(String route) {
        var result = new HashMap<String, String>();
        var parts = StringUtil.split(route, '/', true);

        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var refPart = routeParts.get(i);

            if (refPart.getType() == RoutePart.Type.Variable)
                result.put(refPart.getValue(), StringUtil.urlDecode(part));
        }

        return result;
    }

    public boolean matches(String candidate) {
        var parts = StringUtil.split(candidate, '/', true);
        if (parts.length != routeParts.size())
            return false;

        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var refPart = routeParts.get(i);

            if (refPart.getType() == RoutePart.Type.Constant && !refPart.getValue().equalsIgnoreCase(part))
                return false;
        }

        return true;
    }

}
