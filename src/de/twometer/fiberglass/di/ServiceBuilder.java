package de.twometer.fiberglass.di;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class ServiceBuilder<T> {

    private final Class<T> serviceClass;

    private ServiceConfig<T> configCallback;

    private Consumer<T> bindingCallback;

    private T instance;

    public ServiceBuilder(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public ServiceBuilder<T> configure(ServiceConfig<T> callback) {
        this.configCallback = callback;
        return this;
    }

    public ServiceBuilder<T> bind(Consumer<T> bindingCallback) {
        this.bindingCallback = bindingCallback;
        return this;
    }

    void createInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        instance = ReflectionUtil.createDefaultInstance(serviceClass);
    }

    void runCallbacks() throws Exception {
        if (configCallback != null)
            configCallback.configure(instance);
        if (bindingCallback != null)
            bindingCallback.accept(instance);
    }

    T getInstance() {
        return instance;
    }
}
