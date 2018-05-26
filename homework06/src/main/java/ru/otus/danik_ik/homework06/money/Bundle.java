package ru.otus.danik_ik.homework06.money;

import java.util.ArrayList;
import java.util.List;

public interface Bundle extends List<Banknote> {
    static Bundle empty() {
        return new SimpleBundle();
    }

    static Bundle byValues(int... values) {
        Bundle result = Bundle.empty();
        for (int value: values)
            result.add(new Banknote(Denomination.of(value)));
        result.sort(Banknote.getReversedDenominationComparator());
        return result;
    };

    static Bundle byCount(Denomination denomination, int value) {
        Bundle result = Bundle.empty();
        for (int i = 0; i < value; i++)
            result.add(new Banknote(denomination));
        return result;
    };


}

class SimpleBundle extends ArrayList<Banknote> implements Bundle {
}
