package de.twometer.fiberglass.routing;

import java.util.HashMap;
import java.util.Map;

public class ParsedRequestRoute {

    private final Map<String, String> pathParameters = new HashMap<>();

    private String action;

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
