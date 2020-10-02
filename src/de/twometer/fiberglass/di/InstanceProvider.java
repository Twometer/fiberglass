package de.twometer.fiberglass.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InstanceProvider {

    private final ServiceProvider serviceProvider;

    private InstanceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public static InstanceProvider create(ServiceProvider services) {
        var instanceProvider = new InstanceProvider(services);
        services.createInstances();
        instanceProvider.injectServices();
        instanceProvider.configureServices();
        return instanceProvider;
    }

    public <T> T createInstance(Class<T> clazz) {
        try {
            var instance = ReflectionUtil.createDefaultInstance(clazz);
            inject(instance);
            return instance;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureServices() {
        for (var builder : serviceProvider.getServiceBuilders().entrySet()) {
            try {
                builder.getValue().runCallbacks();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void injectServices() {
        for (var builder : serviceProvider.getServiceBuilders().entrySet())
            inject(builder.getValue().getInstance());
    }

    private void inject(Object o) {
        for (var field : o.getClass().getDeclaredFields()) {
            var injectAnnotation = field.getAnnotation(Inject.class);
            if (injectAnnotation == null) continue;

            var type = field.getType();

            if (!serviceProvider.hasService(type))
                throw new IllegalStateException("Cannot inject type " + type.getName() + " into " + o.getClass().getName() + "." + field.getName() + ": Service does not exist.");
            else {
                var service = serviceProvider.getService(type);
                setField(o, field, service);
            }
        }
    }

    private void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
