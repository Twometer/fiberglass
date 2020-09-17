package de.twometer.fiberglass.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String[] split(String str, char chr) {
        return split(str, chr, false);
    }

    public static String[] split(String str, char chr, boolean skipEmpty) {
        List<String> result = new ArrayList<>();

        StringBuilder current = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c == chr) {
                if (current.length() == 0 && skipEmpty)
                    continue;
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0)
            result.add(current.toString());

        return result.toArray(String[]::new);
    }

    public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

}
