package de.twometer.fiberglass.example;

import de.twometer.fiberglass.api.Fiberglass;

import java.io.IOException;

public class ExampleApp {

    public static void main(String[] args) throws IOException {
        Fiberglass app = new Fiberglass();
        app.addController(TestController.class);
        app.addService(DatabaseService.class);
        app.addService(OtherService.class);
        app.addStaticFiles("web", ExampleApp.class);
        app.addPhotonPages();
        app.start();
    }

}
