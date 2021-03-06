package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;

import static java.lang.Integer.min;

public class BaseCurrencyBox implements WithdrawCurrencyBox {
    protected final Denomination denomination;
    protected final int capacity;
    protected int count;
    
    private final BundleFactory bundleFactory;

    public BaseCurrencyBox(Denomination denomination, int capacity, int count, BundleFactory bundleFactory) {
        this.denomination = denomination;
        this.capacity = capacity;
        this.count = count;
        this.bundleFactory = bundleFactory;
    }

    public BaseCurrencyBox(Denomination denomination, int capacity, int count) {
        this(denomination, capacity, count, new DefaultBundleFactory());
    }

    @Override
    public Bundle withdraw(int count) throws NotEnoughException {
        if (count > this.count)
            throw new NotEnoughException( String.format("Попытка взять из кассеты больше денег, чем в ней есть. " +
                    "Затребовано: %d; в наличии: %d", count, this.count));
        if (count <= 0)
            throw new IllegalArgumentException("Количество купюр к выдаче должно быть положительным");
        this.count -= count;
        return bundleFactory.byCount(this.denomination, count);
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
