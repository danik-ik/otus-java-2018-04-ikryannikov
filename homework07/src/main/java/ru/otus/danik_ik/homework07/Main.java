package ru.otus.danik_ik.homework07;

public class Main
{
    private static final Factories factories = new Factories(
            () -> new RemoteRecyclableATM(),
            () -> new SimpleDepartment()
    );
    
    public static void main( String[] args ) {
        RemoteAtm atm1 = factories.newRemoteAtm();
        RemoteAtm atm2 = factories.newRemoteAtm();
        RemoteAtm atm3 = factories.newRemoteAtm();
    }
}
