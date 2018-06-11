package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.DefaultBundleFactory;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.util.function.BiConsumer;

public class Main
{
    private final Factories factories;

    {
        final BiConsumer <RemoteAtm, String> callbackHandler = (atm, message) -> {
            System.out.printf("===[ %s ]=====================================\n", atm.getName());
            System.out.println(message);
        };

        final BundleFactory bundleFactory = new DefaultBundleFactory();

        factories = new Factories(
                (name) ->
                        new RemoteRecyclableATM(bundleFactory, name, callbackHandler),
                () -> new SimpleDepartment()
        );
    }

    private void run() {
        RemoteAtm atm1 = factories.newRemoteAtm("First");
        RemoteAtm atm2 = factories.newRemoteAtm("Second");
        RemoteAtm atm3 = factories.newRemoteAtm("Third");
    }

    public static void main( String[] args ) {
        new Main().run();
    }
}
