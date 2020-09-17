package de.twometer.fiberglass.http;

public enum StatusCode {

    Continue(100, "Continue"),
    SwitchingProtocols(101, "Switching Protocols"),
    Processing(102, "Processing"),
    EarlyHints(103, "Early Hints"),

    OK(200, "OK"),
    Created(201, "Created"),
    Accepted(202, "Accepted"),
    NonAuthorativeInfo(203, "Non-Authorative Information"),
    NoContent(204, "No Content"),
    ResetContent(205, "Reset Content"),
    PartialContent(206, "Partial Content"),

    MultipleChoices(300, "Multiple Choices"),
    MovedPermanently(301, "Moved Permanently"),
    Found(302, "Found"),
    SeeOther(303, "See Other"),
    NotModified(304, "Not Modified"),
    UseProxy(305, "Use Proxy"),
    TemporaryRedirect(307, "Temporary Redirect"),
    PermanentRedirect(308, "Permanent Redirect"),

    BadRequest(400, "Bad Request"),
    Unauthorized(401, "Unauthorized"),
    PaymentRequired(402, "Payment Required"),
    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    MethodNotAllowed(405, "Method Not Allowed"),

    InternalServerError(500, "Internal Server Error"),
    NotImplemented(501, "Not Implemented"),
    BadGateway(502, "Bad Gateway"),
    ServiceUnavailable(503, "Service Unavailable");

    private final int code;
    private final String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
