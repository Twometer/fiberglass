package de.twometer.fiberglass.server;

import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.IResponse;

import java.io.IOException;

public interface HttpCallback {

    IResponse handleRequest(HttpRequest request) throws IOException;

}
