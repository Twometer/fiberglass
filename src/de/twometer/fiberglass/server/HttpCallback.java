package de.twometer.fiberglass.server;

import de.twometer.fiberglass.request.IRequest;
import de.twometer.fiberglass.response.IResponse;

public interface HttpCallback {

    IResponse handleRequest(IRequest request);

}
