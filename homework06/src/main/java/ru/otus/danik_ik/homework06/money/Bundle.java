package ru.otus.danik_ik.homework06.money;

import java.util.*;

public interface Bundle extends Iterable<Banknote> {

    static Bundle empty() {
        return new SimpleBundle();
    }

    static Bundle byValues(int... values) {
        Bundle result = Bundle.empty();
        for (int value: values)
            result.add(new Banknote(Denomination.of(value)));
        return result;
    }

    static Bundle byCount(Denomination denomination, int value) {
        Bundle result = Bundle.empty();
        for (int i = 0; i < value; i++)
            result.add(new Banknote(denomination));
        return result;
    }

    boolean add(Banknote banknote);

    boolean addAll(Bundle bundle);

    List<Banknote> asList();

    int size();

}

class SimpleBundle implements Bundle {

    List<Banknote> content = new ArrayList<>();

    @Override
    public boolean add(Banknote banknote) {
        return content.add(banknote);
    }

    @Override
    public boolean addAll(Bundle bundle) {
        return content.addAll(bundle.asList());
    }

    @Override
    public List<Banknote> asList() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public Iterator<Banknote> iterator() {
        return content.iterator();
    }
}
