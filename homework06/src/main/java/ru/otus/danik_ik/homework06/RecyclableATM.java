package ru.otus.danik_ik.homework06;

import ru.otus.danik_ik.homework06.atm.ATM;
import ru.otus.danik_ik.homework06.atm.DepositCurrencyBox;
import ru.otus.danik_ik.homework06.atm.WithdrawCurrencyBox;
import ru.otus.danik_ik.homework06.atm.exceptions.AmountCantBeCollectedException;
import ru.otus.danik_ik.homework06.atm.exceptions.CantDepositException;
import ru.otus.danik_ik.homework06.atm.exceptions.NotEnoughException;
import ru.otus.danik_ik.homework06.money.Bundle;
import ru.otus.danik_ik.homework06.money.BundleFactory;
import ru.otus.danik_ik.homework06.money.Denomination;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecyclableATM implements ATM {
    private final int RECYCLABLE_BOX_COUNT = 4;
    private BundleFactory bundleFactory = BundleFactory.getDefault();

    protected RecyclableCurrencyBox[] currencyBoxes = new RecyclableCurrencyBox[RECYCLABLE_BOX_COUNT];
    protected DepositCurrencyBox depositBox;

    public DepositCurrencyBox replaceDepositBox(DepositCurrencyBox currencyBox){
        DepositCurrencyBox result = depositBox;
        depositBox = currencyBox;
        return result;
    }

    public RecyclableCurrencyBox replaceRecyclableBox(int slot, RecyclableCurrencyBox currencyBox){
        RecyclableCurrencyBox result = currencyBoxes[slot];
        currencyBoxes[slot] = currencyBox;
        return result;
    }

    @Override
    public void deposit(Bundle bundle) throws CantDepositException {
        Plan plan = makePlan(bundle);
        plan.apply();
    }

    private Plan makePlan(Bundle bundle) throws CantDepositException {
        return new Plan(bundle);
    }

    @Override
    public int getWithdrawBoxCount() {
        return RECYCLABLE_BOX_COUNT;
    }

    @Override
    public Bundle withdraw(BigDecimal amount) throws AmountCantBeCollectedException {
        List<WithdrawCurrencyBox> boxes = getOrderedWithdrawBoxes();

        Map<WithdrawCurrencyBox, Integer> specification = getSpecification(boxes, amount.intValueExact());

        return collectNotes(specification);
    }

    private Bundle collectNotes(Map<WithdrawCurrencyBox,Integer> specification) throws AmountCantBeCollectedException {
        Bundle bundle = bundleFactory.empty();
        for (WithdrawCurrencyBox box: currencyBoxes)
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
            if (count > 0) {
                remainder -= count * denomination;
                specification.put(box, count);
            }
        }
        if (remainder > 0) throw new AmountCantBeCollectedException();
        return specification;
    }

    private List<WithdrawCurrencyBox> getOrderedWithdrawBoxes() {
        return Stream.of(currencyBoxes)
                .filter(it -> it != null)
                .sorted(Comparator.comparing(a -> ((WithdrawCurrencyBox)a).getDenomination().asInt()).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getAmountToIssue() {
        BigDecimal result = new BigDecimal(0);
        for (RecyclableCurrencyBox box: currencyBoxes)
            if (box != null)
                result = result.add(box.getAmount());
        return result;
    }

    @Override
    public BigDecimal getAmountTotal() {
        return depositBox == null?
                getAmountToIssue() :
                getAmountToIssue().add(depositBox.getAmount());
    }

    private class Plan {
        Map<Denomination, Bundle> denominationBundleMap;
        List<Operation> operations = new LinkedList<>();
        
        public Plan(Bundle bundle) throws CantDepositException {
            denominationBundleMap = bundle.splitByDenominations();
            makeOperations();
        }

        private void makeOperations() throws CantDepositException {
            Bundle remaining = bundleFactory.empty();
            for (Map.Entry<Denomination, Bundle> entry: denominationBundleMap.entrySet()) {
                Denomination denomination = entry.getKey();
                Bundle bundle = entry.getValue();
                
                // Распихиваем по основным ячейкам
                for (DepositCurrencyBox box: currencyBoxes) {
                    if (box.acceptsDenomination(denomination)) {
                        int count = box.canToDeposit(bundle.getCount());
                        if (count == 0) continue;
                        try {
                            operations.add(new Operation(box, bundle.extract(count)));
                        } catch (NotEnoughException e) {
                            // Алгоритм должен исключать данную ситуацию -- не запрашивать больше, чем есть
                            throw new RuntimeException(e);
                        }
                        if (bundle.getCount() == 0) break;
                    }
                }

                if (bundle.getCount() > 0) {
                    if (!depositBox.acceptsDenomination(denomination))
                        throw new CantDepositException("Универсальная ячейка не может принять данный номинал");
                    remaining.addAll(bundle);
                }
            }
            int count = depositBox.canToDeposit(remaining.getCount());
            if (count != remaining.getCount())
                throw new CantDepositException("Банкомат переполнен");
            operations.add(new Operation(depositBox, remaining));
        }

        public void apply() throws CantDepositException {
            for (Operation op: operations)
                op.box.deposit(op.bundle);
        }
        
        private class Operation {
            DepositCurrencyBox box;
            Bundle bundle;

            public Operation(DepositCurrencyBox box, Bundle bundle) {
                this.box = box;
                this.bundle = bundle;
            }
        }
    }
}
