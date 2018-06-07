package ru.otus.danik_ik.homework06.money;


 class DefaultBundleFactory implements BundleFactory {
    @Override
    public Bundle empty() {
        return new SimpleBundle();
    }

     @Override
     public Bundle byValues(int... values) {
        Bundle result = empty();
        for (int value: values)
            result.add(new Banknote(Denomination.of(value)));
        return result;
    }

     @Override
     public Bundle byCount(Denomination denomination, int value) {
        Bundle result = empty();
        for (int i = 0; i < value; i++)
            result.add(new Banknote(denomination));
        return result;
    }
}
