package de.twometer.fiberglass.example;

import de.twometer.fiberglass.api.Fiberglass;

public class ExampleApp {

    public static void main(String[] args) {
        Fiberglass app = new Fiberglass();
        app.addController(TestController.class);
        app.addService(DatabaseService.class);
        app.addStaticFiles();
        app.start();
    }

}
