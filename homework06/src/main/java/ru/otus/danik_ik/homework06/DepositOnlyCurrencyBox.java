package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.min;

public class DepositOnlyCurrencyBox implements DepositCurrencyBox {
    private final int capacity;
    private Map<Denomination, Integer> content = new HashMap<>();

    public DepositOnlyCurrencyBox(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void deposit(Bundle bundle) throws CantDepositException {
        if (bundle.getCount() > capacity - getCount()) throw new CantDepositException("Кассета заполнена");
        for (Banknote note: bundle){
            Denomination denomination = note.getDenomination();
            content.put(denomination, content.getOrDefault(denomination, 0) + 1);
        }
    }

    @Override
    public int canToDeposit(int count) {
        return min(count, capacity - getCount());
    }

    @Override
    public Denomination getDenomination() {
        throw new UnsupportedOperationException("Кассета принимает банкноты всех номиналов");
    }

    @Override
    public int getCount() {
        return content.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public BigDecimal getAmount() {
         return content.entrySet().stream()
                .map(e -> BigDecimal.valueOf(e.getValue())
                        .multiply(e.getKey().asBigDecimal()))
                .reduce(BigDecimal::add)
                .orElseGet(() -> BigDecimal.valueOf(0));
    }

    @Override
    public boolean acceptsDenomination(Denomination denomination) {
        return true;
    }
}
