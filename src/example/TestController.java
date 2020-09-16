package example;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.api.annotation.Route;
import de.twometer.fiberglass.api.annotation.http.Get;
import de.twometer.fiberglass.response.IResponse;

@Route("~/edit/{id}")
public class TestController extends Controller {

    private final DatabaseService databaseService;

    public TestController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Get
    public IResponse get(String id) {
        databaseService.test();
        return null;
    }

    @Get
    public IResponse get(String id, String action) {
        return null;
    }

}
