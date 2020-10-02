package de.twometer.fiberglass.request;

import de.twometer.fiberglass.http.Cookie;
import de.twometer.fiberglass.http.Header;
import de.twometer.fiberglass.http.Method;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequest {

    private final List<Header> headers = new ArrayList<>();
    private final List<Cookie> cookies = new ArrayList<>();
    private final Map<String, String> query = new HashMap<>();
    private Method method;
    private String requestUri;
    private byte[] body;

    public void read(InputStream inputStream) throws IOException {
        var decoder = new HttpDecoder(this);
        decoder.decodeRequest(inputStream);
    }

    private Stream<String> getHeaderStreamMatching(String name) {
        return headers.stream().filter(h -> h.getKey().equalsIgnoreCase(name)).map(Header::getValue);
    }

    public String getHeader(String name) {
        return getHeaderStreamMatching(name).findAny().orElse(null);
    }

    public Collection<String> getHeaders(String name) {
        return getHeaderStreamMatching(name).collect(Collectors.toList());
    }

    public Method getMethod() {
        return method;
    }

    void setMethod(Method method) {
        this.method = method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    void addQueryParameter(String key, String value) {
        query.put(key, value);
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public byte[] getBody() {
        return body;
    }

    void setBody(byte[] body) {
        this.body = body;
    }

}
