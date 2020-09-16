package de.twometer.fiberglass.host;

public interface IHost {

    boolean match(String requestUri);

    void serve();

}
