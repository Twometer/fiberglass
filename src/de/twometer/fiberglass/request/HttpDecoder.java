package de.twometer.fiberglass.request;

import de.twometer.fiberglass.http.Cookie;
import de.twometer.fiberglass.http.Header;
import de.twometer.fiberglass.http.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HttpDecoder {

    private final HttpRequest request;

    public HttpDecoder(HttpRequest request) {
        this.request = request;
    }

    public void decodeRequest(InputStream inputStream) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(inputStream));

        var firstLine = true;

        String line;
        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                parseHead(line);
                firstLine = false;
                continue;
            }

            if (line.isBlank()) {
                readBody(inputStream);
                return;
            }

            parseRequestHeader(line);
        }
    }

    private void parseHead(String line) throws IOException {
        var parts = line.split(" ");

        var optionalMethod = Arrays.stream(Method.values())
                .filter(c -> c.name().equalsIgnoreCase(parts[0]))
                .findAny();
        if (optionalMethod.isEmpty()) {
            throw new IOException("Invalid request method");
        }
        request.setMethod(optionalMethod.get());
        request.setRequestUri(parts[1]);
    }

    private void parseRequestHeader(String line) {
        var key = line.substring(0, line.indexOf(":") + 1);
        var val = line.substring(key.length()).trim();

        if (key.equalsIgnoreCase("cookie")) {
            if (val.contains(":")) {
                var cookies = val.split(";");
                for (var cookie : cookies)
                    parseCookie(cookie);
            } else {
                parseCookie(val);
            }
        } else {
            request.getHeaders().add(new Header(key, val));
        }
    }

    private void parseCookie(String cookie) {
        var key = cookie.substring(0, cookie.indexOf("="));
        var val = cookie.substring(key.length() + 1);
        request.getCookies().add(new Cookie().setKey(key).setValue(val));
    }

    private void readBody(InputStream inputStream) {

    }

}
