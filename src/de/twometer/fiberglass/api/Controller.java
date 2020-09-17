package de.twometer.fiberglass.api;

import de.twometer.fiberglass.hosting.impl.StaticFile;
import de.twometer.fiberglass.http.StatusCode;
import de.twometer.fiberglass.response.*;
import de.twometer.fiberglass.routing.RouteMatcher;
import de.twometer.fiberglass.routing.RouteParser;

public class Controller {

    private final RouteMatcher routeMatcher;

    public Controller() {
        routeMatcher = RouteParser.parse(this);
    }

    public final RouteMatcher getRouteMatcher() {
        return routeMatcher;
    }

    protected final IResponse error(StatusCode statusCode) {
        return new ErrorResponse(statusCode);
    }

    protected final IResponse error(StatusCode statusCode, String message) {
        return new ErrorResponse(statusCode, message);
    }

    protected final IResponse redirect(String target) {
        return new HttpResponse()
                .setStatusCode(StatusCode.Found)
                .addHeader("Location", target);
    }

    protected final IResponse text(String content) {
        return new HttpTextResponse()
                .setTextBody(content);
    }

    protected final IResponse text(String content, String type) {
        return new HttpTextResponse()
                .setTextBody(content)
                .addHeader("Content-Type", type);
    }

    protected final IResponse binary(byte[] data, String type) {
        return new HttpResponse()
                .setBody(data)
                .addHeader("Content-Type", type);
    }

    protected final IResponse file(StaticFile file) {
        return new HttpResponse()
                .setBody(file.getContents())
                .addHeader("Content-Type", file.getMimeType());
    }

    protected final IResponse json(Object obj) {
        return new JsonResponse(obj);
    }

}
