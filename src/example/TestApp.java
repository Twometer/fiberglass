package example;

import de.twometer.fiberglass.api.Fiberglass;

public class TestApp {

    public static void main(String[] args) {
        Fiberglass fiberglass = new Fiberglass();

        fiberglass.addService(DatabaseService.class);

        fiberglass.addStaticFiles();

        fiberglass.start();
        System.out.println("Running");
    }

}
