package de.twometer.fiberglass.hosting.base;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.IResponse;

import java.io.IOException;

public interface IHost {

    void initialize(InstanceProvider instanceProvider) throws IOException;

    boolean match(String requestUri);

    IResponse serve(HttpRequest request);

}
