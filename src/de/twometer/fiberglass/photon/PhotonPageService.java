package de.twometer.fiberglass.photon;

import de.twometer.fiberglass.hosting.impl.StaticFile;
import de.twometer.fiberglass.http.MimeType;
import de.twometer.fiberglass.response.HttpTextResponse;
import de.twometer.fiberglass.response.IResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhotonPageService {

    private final Map<String, CompiledPhotonPage> compiledPageCache = new HashMap<>();

    private final PhotonCompiler compiler = new PhotonCompiler();

    public IResponse render(StaticFile file, Object viewModel) throws IOException {
        var page = compiledPageCache.get(file.getPath());
        if (page == null) {
            page = compiler.compile(file);
            compiledPageCache.put(file.getPath(), page);
        }

        var builder = new StringBuilder();
        page.render(viewModel, builder);
        return new HttpTextResponse()
                .setTextBody(builder.toString())
                .addHeader("Content-Type", MimeType.HTML);
    }

}
