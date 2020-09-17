package de.twometer.fiberglass.response;

import de.twometer.fiberglass.http.StatusCode;

public class ErrorResponse extends HttpTextResponse {

    public ErrorResponse(StatusCode code) {
        setStatusCode(code);
        addHeader("Content-Type", "text/html");
        setTextBody(String.format("<html><body><h1>Error %d - %s</h1><p>The server could not process the request.</p></body></html>", code.getCode(), code.getDescription()));
    }

}
