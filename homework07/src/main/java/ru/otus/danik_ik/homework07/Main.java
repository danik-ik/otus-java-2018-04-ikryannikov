package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.DefaultBundleFactory;

public class Main
{
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
