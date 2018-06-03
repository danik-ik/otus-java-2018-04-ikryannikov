package ru.otus.danik_ik.homework06.money;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;

import java.util.*;
import java.util.stream.Collectors;

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

    int getCount();

    Set<Denomination> getDenominations();

    Bundle extract(int count) throws NotEnoughException;

    Map<Denomination, Bundle> splitByDenominations();

}

class SimpleBundle implements Bundle {

    List<Banknote> content = new LinkedList<>();

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
    public int getCount() {
        return content.size();
    }

    @Override
    public Set<Denomination> getDenominations() {
        return content.stream()
                .map(banknote -> banknote.getDenomination())
                .collect(Collectors.toSet())
                ;
    }

    @Override
    public Bundle extract(int count) throws NotEnoughException {
        if (count > this.getCount()) throw new NotEnoughException();
        Bundle result = new SimpleBundle();
        for (int i = 0; i < count; i++) result.add(content.remove(0));
        return result;
    }

    @Override
    public Map<Denomination, Bundle> splitByDenominations() {
        Map<Denomination, Bundle> result = new HashMap<>();
        for(Banknote banknote: content) {
            Bundle destination = result.computeIfAbsent(banknote.getDenomination(), (a) -> new SimpleBundle());
            destination.add(banknote);
        }
        return result;
    }

    @Override
    public Iterator<Banknote> iterator() {
        return content.iterator();
    }
}
