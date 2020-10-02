package de.twometer.fiberglass.request;

import de.twometer.fiberglass.http.Cookie;
import de.twometer.fiberglass.http.Header;
import de.twometer.fiberglass.http.Method;
import de.twometer.fiberglass.util.StringUtil;

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
        var parts = StringUtil.split(line, ' ');

        var optionalMethod = Arrays.stream(Method.values())
                .filter(c -> c.name().equalsIgnoreCase(parts[0]))
                .findAny();
        if (optionalMethod.isEmpty()) {
            throw new IOException("Invalid request method");
        }
        request.setMethod(optionalMethod.get());
        parseRequestUri(parts[1]);
    }

    private void parseRequestUri(String uri) {
        var idx = uri.indexOf('?');
        if (idx >= 0) {
            request.setRequestUri(uri.substring(0, idx));

            var query = uri.substring(idx + 1);
            var items = StringUtil.split(query, '&', true);
            for (var item : items) {
                var separatorIdx = item.indexOf('=');
                if (separatorIdx < 0)
                    request.addQueryParameter(item, "");
                else {
                    var key = item.substring(0, separatorIdx);
                    var val = item.substring(separatorIdx + 1);
                    request.addQueryParameter(StringUtil.urlDecode(key), StringUtil.urlDecode(val));
                }
            }
        } else {
            request.setRequestUri(uri);
        }
    }

    private void parseRequestHeader(String line) {
        var key = line.substring(0, line.indexOf(":"));
        var val = line.substring(key.length() + 1).trim();

        if (key.equalsIgnoreCase("cookie")) {
            if (val.contains(":")) {
                var cookies = StringUtil.split(val, ';');
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
