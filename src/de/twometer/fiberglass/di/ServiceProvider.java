package de.twometer.fiberglass.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {

    private Map<Class<?>, Object> services = new HashMap<>();

    public void registerService(Class<?> serviceClass) {
        if (hasService(serviceClass))
            throw new IllegalArgumentException("Cannot add service " + serviceClass.getName() + " twice.");

        Constructor<?>[] constructors = serviceClass.getDeclaredConstructors();
        if (constructors.length == 0)
            throw new IllegalArgumentException("The service " + serviceClass.getName() + " does not have an available constructor.");

        instantiateAndAdd(serviceClass, constructors[0]);
    }

    private void instantiateAndAdd(Class<?> serviceClass, Constructor<?> constructor) {
        try {
            Object obj = constructor.newInstance();
            services.put(serviceClass, obj);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate service " + serviceClass.getName(), e);
        }
    }

    public Object getService(Class<?> service) {
        return services.get(service);
    }

    public boolean hasService(Class<?> service) {
        return services.containsKey(service);
    }

}
