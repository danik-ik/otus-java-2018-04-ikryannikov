package ru.otus.danik_ik.homework06.money;

import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;

import java.util.*;

public interface Bundle extends Iterable<Banknote> {

    boolean add(Banknote banknote);

    boolean addAll(Bundle bundle);

    List<Banknote> asList();

    int getCount();

    Set<Denomination> getDenominations();

    Bundle extract(int count) throws NotEnoughException;

    Map<Denomination, Bundle> splitByDenominations();

}

