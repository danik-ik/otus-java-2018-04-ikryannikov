package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.*;
import java.util.stream.Collectors;

class SimpleBundle implements Bundle {

    private List<Banknote> content = new LinkedList<>();

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
