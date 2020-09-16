package de.twometer.fiberglass.server;

public class HttpConfig {

    private String basePath = "/";

    private int port = 8080;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
