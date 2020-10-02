package de.twometer.fiberglass.di;

public interface ServiceConfig<T> {

    void configure(T service) throws Exception;

}
