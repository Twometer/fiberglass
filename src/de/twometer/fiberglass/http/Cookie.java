package de.twometer.fiberglass.http;

import java.time.Instant;

public class Cookie {

    private String key;

    private String value;

    private Instant expires;

    private int maxAge;

    private String path;

    private boolean secure;

    private boolean httpOnly;

    public String getKey() {
        return key;
    }

    public Cookie setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Cookie setValue(String value) {
        this.value = value;
        return this;
    }

    public Instant getExpires() {
        return expires;
    }

    public Cookie setExpires(Instant expires) {
        this.expires = expires;
        return this;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Cookie setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Cookie setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isSecure() {
        return secure;
    }

    public Cookie setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public Cookie setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }
}
