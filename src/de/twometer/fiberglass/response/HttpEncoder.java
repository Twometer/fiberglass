package de.twometer.fiberglass.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpEncoder {

    private final HttpResponse response;

    public HttpEncoder(HttpResponse response) {
        this.response = response;
    }

    public void encodeResponse(OutputStream outputStream) throws IOException {
        var writer = new PrintWriter(outputStream);
        writeHead(writer);

        for (var header : response.getHeaders())
            writeHeader(writer, header.getKey(), header.getValue());

        writeHeader(writer, "Content-Length", String.valueOf(response.getBody().length));

        writer.println();
        writer.flush();
        outputStream.write(response.getBody());
    }

    private void writeHead(PrintWriter writer) {
        writer.print("HTTP/1.1 ");
        writer.print(response.getStatusCode().getCode());
        writer.print(' ');
        writer.println(response.getStatusCode().getDescription());
    }

    private void writeHeader(PrintWriter writer, String key, String value) {
        writer.print(key);
        writer.print(": ");
        writer.println(value);
    }

}
