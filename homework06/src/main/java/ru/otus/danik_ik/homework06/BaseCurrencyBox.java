package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Banknote;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.min;

public class BaseCurrencyBox implements WithdrawCurrencyBox {
    protected final Denomination denomination;
    protected final int capacity;
    protected int count;

    public BaseCurrencyBox(Denomination denomination, int capacity, int count) {
        this.denomination = denomination;
        this.capacity = capacity;
        this.count = count;
    }

    @Override
    public List<Banknote> withdraw(int count) throws NotEnoughException {
        if (count > this.count) throw new NotEnoughException("Нет достаточного количества денег");
        if (count <= 0) throw new IllegalArgumentException();
        this.count -= count;
        return Stream
                .generate(() -> new Banknote(denomination))
                .limit(count)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public int canToWithdraw(int count) {
        return min(count, this.count);
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public BigDecimal getAmount() {
        return denomination.asBigDecimal().multiply(new BigDecimal(count));
    }
}
