package de.twometer.fiberglass.response;

import de.twometer.fiberglass.http.StatusCode;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ResponseFactory {

    private ResponseFactory() {
    }

    public static IResponse newInternalServerError(Throwable t) {
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));
        return new ErrorResponse(StatusCode.InternalServerError, writer.toString());
    }

}
