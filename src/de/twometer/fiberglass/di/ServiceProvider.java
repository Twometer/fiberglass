package de.twometer.fiberglass.di;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {

    private final Map<Class<?>, ServiceBuilder<?>> serviceBuilders = new HashMap<>();

    public <T> ServiceBuilder<T> register(Class<T> serviceClass) {
        if (serviceBuilders.containsKey(serviceClass))
            throw new IllegalArgumentException("Cannot add service " + serviceClass.getName() + " twice.");

        ServiceBuilder<T> builder = new ServiceBuilder<>(serviceClass);
        serviceBuilders.put(serviceClass, builder);
        return builder;
    }

    void createInstances() {
        for (var entry : serviceBuilders.entrySet()) {
            try {
                entry.getValue().createInstance();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    Map<Class<?>, ServiceBuilder<?>> getServiceBuilders() {
        return serviceBuilders;
    }

    boolean hasService(Class<?> serviceClass) {
        return serviceBuilders.containsKey(serviceClass);
    }

    Object getService(Class<?> serviceClass) {
        return serviceBuilders.get(serviceClass).getInstance();
    }


}
