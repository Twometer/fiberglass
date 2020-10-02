package de.twometer.fiberglass.example;

import de.twometer.fiberglass.di.Inject;

public class DatabaseService {

    @Inject
    private OtherService otherService;

    public void test() {
        otherService.sayHello();
    }

}
