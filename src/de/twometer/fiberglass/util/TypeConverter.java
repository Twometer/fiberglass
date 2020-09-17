package de.twometer.fiberglass.util;

public class TypeConverter {

    public static Object convertType(Class<?> dstClass, String src) {
        if (dstClass == String.class)
            return src;
        else if (dstClass == int.class)
            return Integer.valueOf(src);
        else if (dstClass == long.class)
            return Long.valueOf(src);
        else if (dstClass == float.class)
            return Float.valueOf(src);
        else if (dstClass == double.class)
            return Double.valueOf(src);
        else if (dstClass == byte.class)
            return Byte.valueOf(src);
        else
            throw new ClassCastException("Cannot convert string to " + dstClass);
    }

}
