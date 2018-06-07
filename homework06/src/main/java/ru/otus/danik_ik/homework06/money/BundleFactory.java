package ru.otus.danik_ik.homework06.money;

public interface BundleFactory {
    Bundle empty();

    Bundle byValues(int... values);

    Bundle byCount(Denomination denomination, int value);
    
    static BundleFactory getDefault() { return new DefaultBundleFactory(); }
}
