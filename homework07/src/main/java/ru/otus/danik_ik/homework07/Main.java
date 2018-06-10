package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.DefaultBundleFactory;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.util.function.BiConsumer;

public class Main
{
    private final BiConsumer <RemoteAtm, String> callbackHandler = (atm, message) -> {
        System.out.printf("===[ %s ]=====================================\n", atm.getName());
        System.out.println(message);
    };

    private final BundleFactory bundleFactory = new DefaultBundleFactory();

    private static final Factories factories = new Factories(
            () -> new RemoteRecyclableATM(new DefaultBundleFactory()),
            () -> new SimpleDepartment()
    );
    
    public static void main( String[] args ) {
        RemoteAtm atm1 = factories.newRemoteAtm();
        RemoteAtm atm2 = factories.newRemoteAtm();
        RemoteAtm atm3 = factories.newRemoteAtm();
    }
}
