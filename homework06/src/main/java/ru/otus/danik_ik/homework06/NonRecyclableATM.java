package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Banknote;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NonRecyclableATM implements ATM {
    WithdrawCurrencyBox[] withdrawBoxes = new WithdrawCurrencyBox[4];
    DepositCurrencyBox depositBox;

    public WithdrawCurrencyBox replaceWithdrawBox(int slot, WithdrawCurrencyBox currencyBox){
        WithdrawCurrencyBox result = withdrawBoxes[slot];
        withdrawBoxes[slot] = currencyBox;
        return result;
    }

    public DepositCurrencyBox replaceDepositBox(int slot, DepositCurrencyBox currencyBox){
        DepositCurrencyBox result = depositBox;
        depositBox = currencyBox;
        return result;
    }

    @Override
    public void deposit(List<Banknote> bundle) throws CantDepositException {
        depositBox.deposit(bundle);
    }

    @Override
    public List<Banknote> withdraw(BigDecimal amount) throws AmountCantBeCollectedException {
        List<WithdrawCurrencyBox> boxes = getOrderedWithdrawBoxes();

        Map<WithdrawCurrencyBox, Integer> specification = getSpecification(boxes, amount.intValueExact());

        return collectNotes(specification);
    }

    private List<Banknote> collectNotes(Map<WithdrawCurrencyBox,Integer> specification) throws AmountCantBeCollectedException {
        List<Banknote> bundle = new ArrayList<>();
        for (WithdrawCurrencyBox box: withdrawBoxes)
            if (specification.containsKey(box)) try {
                bundle.addAll(box.withdraw(specification.get(box)));
            } catch (NotEnoughException e) {
                try {
                    depositBox.deposit(bundle);
                } catch (CantDepositException e1) {
                    // Если при наборе пачки произошла ошибка, и мы не можем
                    // сбросить эту пачку -- всё, сливай вода, глуши машина
                    throw new RuntimeException(e1);
                }
                throw new AmountCantBeCollectedException(e);
            }
        return bundle;
    }

    private Map<WithdrawCurrencyBox,Integer> getSpecification(List<WithdrawCurrencyBox> boxes, int amount) throws AmountCantBeCollectedException {
        int remainder = amount;
        Map<WithdrawCurrencyBox,Integer> specification = new HashMap<>();
        for (WithdrawCurrencyBox box: boxes) {
            int denomination = box.getDenomination().asInt();
            int requiredCount = remainder / denomination;
            int count = box.canToWithdraw(requiredCount);
            remainder = count * denomination;
            if (count > 0) specification.put(box, count);
        }
        if (remainder > 0) throw new AmountCantBeCollectedException();
        return specification;
    }

    private List<WithdrawCurrencyBox> getOrderedWithdrawBoxes() {
        return Stream.of(withdrawBoxes)
                .sorted(Comparator.comparing(a -> ((WithdrawCurrencyBox)a).getDenomination().asInt()).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getAmountToIssue() {
        BigDecimal result = new BigDecimal(0);
        for (WithdrawCurrencyBox box: withdrawBoxes) result = result.add(box.getAmount());
        return result;
    }

    @Override
    public BigDecimal getAmountTotal() {
        return getAmountToIssue().add(depositBox.getAmount());
    }
}
