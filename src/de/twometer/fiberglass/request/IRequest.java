package de.twometer.fiberglass.request;

import java.io.IOException;
import java.io.InputStream;

public interface IRequest {

    void read(InputStream inputStream) throws IOException;

}
