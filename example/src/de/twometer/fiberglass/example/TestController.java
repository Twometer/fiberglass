package de.twometer.fiberglass.example;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.api.annotation.Http;
import de.twometer.fiberglass.api.annotation.Index;
import de.twometer.fiberglass.api.annotation.Param;
import de.twometer.fiberglass.api.annotation.Route;
import de.twometer.fiberglass.di.Inject;
import de.twometer.fiberglass.hosting.impl.StaticFileProvider;
import de.twometer.fiberglass.http.Method;
import de.twometer.fiberglass.http.StatusCode;
import de.twometer.fiberglass.photon.PhotonPageService;
import de.twometer.fiberglass.response.IResponse;

import java.io.IOException;

@Route("/book/{id}")
public class TestController extends Controller {

    @Inject
    private PhotonPageService photon;

    @Inject
    private StaticFileProvider fileProvider;

    @Inject
    private DatabaseService databaseService;

    @Index
    @Http(Method.GET)
    public IResponse get(@Param("id") String id) {
        databaseService.test();
        return text("hello :3 id=" + id);
    }

    @Http(Method.GET)
    public IResponse get(@Param("id") int id, @Param("action") String action) {
        return error(StatusCode.OK, "id=" + id);
    }

    @Http(Method.GET)
    public IResponse testEndpoint(@Param("id") String id) throws IOException {
        var model = new ExampleViewModel();
        model.current_id = id;
        return photon.render(fileProvider.getFile("/index.html"), model);
    }

    @Http(Method.GET)
    public IResponse jsonTest(@Param("id") String id) {
        return json(new Test());
    }

    private static class Test {
        String hello = "world";
        String foo = "bar";
    }

}
