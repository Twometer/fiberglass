package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.di.InstanceProvider;
import de.twometer.fiberglass.hosting.base.IHost;
import de.twometer.fiberglass.http.StatusCode;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.ErrorResponse;
import de.twometer.fiberglass.response.HttpResponse;
import de.twometer.fiberglass.response.IResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticFileHost implements IHost {

    private final StaticFileProvider fileProvider;

    public StaticFileHost(StaticFileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    @Override
    public void initialize(InstanceProvider instanceProvider) throws IOException {
        fileProvider.scan();
    }

    @Override
    public boolean match(String requestUri) throws IOException {
        return findFile(requestUri) != null;
    }

    @Override
    public IResponse serve(HttpRequest request) throws IOException {
        var file = findFile(request.getRequestUri());

        if (file == null)
            return new ErrorResponse(StatusCode.NotFound);

        return new HttpResponse()
                .addHeader("Content-Type", file.getMimeType())
                .setBody(file.getContents());
    }

    private StaticFile findFile(String path) throws FileNotFoundException {
        String fileWithIndex = path + (path.endsWith("/") ? "" : '/') + "index.html";
        var file = fileProvider.getFileMap().get(path);
        if (file == null)
            file = fileProvider.getFileMap().get(fileWithIndex);
        return file;
    }

}
