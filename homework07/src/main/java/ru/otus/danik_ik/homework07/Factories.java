package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Factories {
    private Function<String, RemoteAtm> newRemoteAtmFn;
    private Supplier <Department> newDepartmentFn;
    private BiFunction<Denomination, Integer, WithdrawCurrencyBox> newWithdrawBox;

    protected void setNewRemoteAtmFn(Function<String, RemoteAtm> newRemoteAtmFn) {
        this.newRemoteAtmFn = newRemoteAtmFn;
    }

    protected void setNewDepartmentFn(Supplier<Department> newDepartmentFn) {
        this.newDepartmentFn = newDepartmentFn;
    }

    protected void setNewWithdrawBoxFn(BiFunction<Denomination, Integer, WithdrawCurrencyBox> newWithdrawBox) {
        this.newWithdrawBox = newWithdrawBox;
    }

    public RemoteAtm newRemoteAtm(String name) {
        return newRemoteAtmFn.apply(name);
    }

    public Department newDepartment() {
        return newDepartmentFn.get();
    }

    public WithdrawCurrencyBox newWithdrawCurrencyBox(Denomination denomination, int count) {
        return newWithdrawBox.apply(denomination, count);
    }
}
