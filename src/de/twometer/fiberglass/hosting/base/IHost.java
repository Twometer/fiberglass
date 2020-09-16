package de.twometer.fiberglass.hosting.base;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.request.IRequest;
import de.twometer.fiberglass.response.IResponse;

public interface IHost {

    void initialize(InstanceProvider instanceProvider);

    boolean match(String requestUri);

    IResponse serve(IRequest request);

}
