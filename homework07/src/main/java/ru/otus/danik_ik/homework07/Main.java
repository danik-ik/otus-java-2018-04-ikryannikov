package ru.otus.danik_ik.homework07;

import ru.otus.danik_ik.homework06.DefaultBundleFactory;
import ru.otus.danik_ik.homework06.DepositOnlyCurrencyBox;
import ru.otus.danik_ik.homework06.RecyclableCurrencyBoxImpl;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiConsumer;

public class Main
{
    private final Factories factories = new Factories(){{
        final int DEFAULT_CAPACITY = 3000;
        final BiConsumer <RemoteAtm, String> callbackHandler = (atm, message) -> {
            System.out.printf("===[ %s ]=====================================\n", atm.getName() );
            System.out.println(message);
        };

        final BundleFactory bundleFactory = new DefaultBundleFactory();

        setNewRemoteAtmFn(
                (name) -> new RemoteRecyclableATM(bundleFactory, name, callbackHandler) );
        setNewDepartmentFn(
                () -> new SimpleDepartment() );
        setNewDepositCurrencyBoxFn(
                () -> new DepositOnlyCurrencyBox(DEFAULT_CAPACITY));
        setNewWithdrawBoxFn(
                (denomination, count) -> new RecyclableCurrencyBoxImpl(
                        denomination, DEFAULT_CAPACITY, count, bundleFactory));
    }};

    public static void main( String[] args ) {
        new Main().run();
    }

    private void run() {
        Department dept = factories.newDepartment();

        RemoteAtm atm1 = factories.newRemoteAtm("First");
        RemoteAtm atm2 = factories.newRemoteAtm("Second");
        RemoteAtm atm3 = factories.newRemoteAtm("Third");

        dept.subcsribe(atm1, createBoxSet(1000, 2000, 1000, 1000));
        dept.subcsribe(atm2, createBoxSet(2800, 1000, 1000, 2000));
        dept.subcsribe(atm3, createBoxSet(1000, 1000, 1000, 3000));

        System.out.println("* Department запрашивает полную сумму");
        printAmounts(dept.getAmountsTotal());
        System.out.println("* Department запрашивает сумму к выдаче");
        printAmounts(dept.getAmountsToIssue());

        System.out.println("* Department инициирует замену кассет");
        dept.replaceBoxes();

        System.out.println("* Department запрашивает полную сумму (после замены кассет)");
        printAmounts(dept.getAmountsTotal());
        System.out.println("* Department запрашивает сумму к выдаче (после замены кассет)");
        printAmounts(dept.getAmountsToIssue());

    }

    private void printAmounts(Map<RemoteAtm,BigDecimal> amounts) {
        System.out.println("Результаты запроса");
        amounts.forEach(
                (atm, amount) -> {
                    System.out.println(atm.getName() + ": " + amount);
                }
        );
    }

    private final Denomination[] denominations = new Denomination[]{
            Denomination.ONE_HUNDRED,
            Denomination.FIVE_HUNDRED,
            Denomination.ONE_THOUSAND,
            Denomination.FIVE_THOUSAND,
    };

    private BoxSet createBoxSet(int... counts) {
        if (counts.length > denominations.length)
            throw new IllegalArgumentException( String.format("Максимальное количество аргументов для " +
                    "createBoxSet %d", denominations.length));

        WithdrawCurrencyBox[] wBoxes = new WithdrawCurrencyBox[counts.length];
        for (int i = 0; i < counts.length; i++) {
            wBoxes[i] = factories.newWithdrawCurrencyBox(denominations[i], counts[i]);
        }

        return new BoxSet(factories.newDepositCurrencyBox(), wBoxes);
    }
}
