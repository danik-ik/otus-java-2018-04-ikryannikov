package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.DefaultBundleFactory;
import ru.otus.danik_ik.homework06.RecyclableCurrencyBoxImpl;
import ru.otus.danik_ik.homework06.money.BundleFactory;

import java.util.function.BiConsumer;

public class Main
{
    private final Factories factories = new Factories(){{
        final int DEFAULT_CAPACITY = 3000;
        final BiConsumer <RemoteAtm, String> callbackHandler = (atm, message) -> {
            System.out.printf("===[ %s ]=====================================\n", atm.getName() );
            System.out.println(message);
        };

        final BundleFactory bundleFactory = new DefaultBundleFactory();

        setNewRemoteAtmFn(
                (name) -> new RemoteRecyclableATM(bundleFactory, name, callbackHandler) );
        setNewDepartmentFn(
                () -> new SimpleDepartment() );
        setNewWithdrawBoxFn(
                (denomination, count) -> new RecyclableCurrencyBoxImpl(
                        denomination, DEFAULT_CAPACITY, count, bundleFactory));
    }};

    public static void main( String[] args ) {
        new Main().run();
    }

    private void run() {
        Department dept = factories.newDepartment();

        RemoteAtm atm1 = factories.newRemoteAtm("First");
        RemoteAtm atm2 = factories.newRemoteAtm("Second");
        RemoteAtm atm3 = factories.newRemoteAtm("Third");

        BoxSet boxSet1 = ...
        dept.subcsribe(atm1, boxSet1);
        ....
    }
}
