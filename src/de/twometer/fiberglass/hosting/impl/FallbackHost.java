package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.request.IRequest;
import de.twometer.fiberglass.response.IResponse;

public class FallbackHost implements IHost {
    @Override
    public void initialize(InstanceProvider instanceProvider) {

    }

    @Override
    public boolean match(String requestUri) {
        return false;
    }

    @Override
    public IResponse serve(IRequest request) {
        return null;
    }
}
