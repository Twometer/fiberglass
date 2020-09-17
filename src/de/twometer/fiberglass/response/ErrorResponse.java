package de.twometer.fiberglass.response;

import de.twometer.fiberglass.http.StatusCode;

public class ErrorResponse extends HttpTextResponse {

    public ErrorResponse(StatusCode code) {
        this(code, "");
    }

    public ErrorResponse(StatusCode code, String message) {
        setStatusCode(code);
        addHeader("Content-Type", "text/html");
        setTextBody(String.format("<html><body><h1>Error %d - %s</h1><p>The server could not process the request.</p><p><code><pre>%s</pre></code></p></body></html>", code.getCode(), code.getDescription(), message));
    }
}
