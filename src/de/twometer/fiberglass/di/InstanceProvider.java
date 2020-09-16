package de.twometer.fiberglass.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InstanceProvider {

    private final ServiceProvider serviceProvider;

    public InstanceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> objectClass) {
        for (var constructor : objectClass.getConstructors()) {
            if (!isConstructorValid(constructor))
                continue;

            try {
                return (T) createInstance(constructor);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException("Failed to invoke constructor", e);
            }

        }

        throw new RuntimeException("Cannot create instance for " + objectClass.toString() + ", as no constructor matches.");
    }

    private Object createInstance(Constructor<?> constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var parameters = new Object[constructor.getParameterCount()];
        var types = constructor.getParameterTypes();
        for (int i = 0; i < parameters.length; i++)
            parameters[i] = serviceProvider.getService(types[i]);
        return constructor.newInstance(parameters);
    }

    private boolean isConstructorValid(Constructor<?> constructor) {
        for (var parameter : constructor.getParameters()) {
            if (!serviceProvider.hasService(parameter.getType()))
                return false;
        }
        return true;
    }

}
