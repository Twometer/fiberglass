package de.twometer.fiberglass.response;

import de.twometer.fiberglass.http.Cookie;
import de.twometer.fiberglass.http.Header;
import de.twometer.fiberglass.http.StatusCode;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse implements IResponse {

    private final List<Header> headers = new ArrayList<>();
    private final List<Cookie> cookies = new ArrayList<>();
    private StatusCode statusCode;
    private byte[] body;

    @Override
    public void write(OutputStream outputStream) throws IOException {
        var encoder = new HttpEncoder(this);
        encoder.encodeResponse(outputStream);
    }

    public HttpResponse addHeader(String key, String value) {
        headers.add(new Header(key, value));
        return this;
    }

    public HttpResponse addCookie(Cookie cookie) {
        cookies.add(cookie);
        return this;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public HttpResponse setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
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

    public HttpResponse setBody(byte[] body) {
        this.body = body;
        return this;
    }
}
