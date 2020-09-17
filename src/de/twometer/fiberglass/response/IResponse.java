package de.twometer.fiberglass.response;

import java.io.IOException;
import java.io.OutputStream;

public interface IResponse {

    void write(OutputStream outputStream) throws IOException;

}
