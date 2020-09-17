package de.twometer.fiberglass.routing;

public class RoutePart {

    private final Type type;
    private final String value;

    public RoutePart(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        Constant,
        Variable
    }

}
