package de.twometer.fiberglass.response;

import java.nio.charset.StandardCharsets;

public class HttpTextResponse extends HttpResponse {

    public HttpTextResponse setTextBody(String textBody) {
        setBody(textBody.getBytes(StandardCharsets.UTF_8));
        return this;
    }

}
