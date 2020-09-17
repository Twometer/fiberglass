package example;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.api.annotation.Http;
import de.twometer.fiberglass.api.annotation.Index;
import de.twometer.fiberglass.api.annotation.Param;
import de.twometer.fiberglass.api.annotation.Route;
import de.twometer.fiberglass.http.Method;
import de.twometer.fiberglass.response.HttpTextResponse;
import de.twometer.fiberglass.response.IResponse;

@Route("/book/{id}")
public class TestController extends Controller {

    private final DatabaseService databaseService;

    public TestController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Index
    @Http(Method.GET)
    public IResponse get(@Param("id") String id) {
        databaseService.test();
        return new HttpTextResponse().setTextBody("hello :3 id=" + id);
    }

    @Http(Method.GET)
    public IResponse get(@Param("id") int id, @Param("action") String action) {
        return new HttpTextResponse().setTextBody("penis. " + id + " " + action);
    }

}
