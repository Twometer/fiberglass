package de.twometer.fiberglass.di;

import java.lang.reflect.InvocationTargetException;

class ReflectionUtil {

    @SuppressWarnings("unchecked")
    static <T> T createDefaultInstance(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var constructors = clazz.getConstructors();
        for (var constructor : constructors)
            if (constructor.getParameterCount() == 0) {
                return (T) constructor.newInstance();
            }
        throw new IllegalStateException("Cannot build instance for " + clazz.getName() + " as it has no zero-parameter constructor.");
    }

}
