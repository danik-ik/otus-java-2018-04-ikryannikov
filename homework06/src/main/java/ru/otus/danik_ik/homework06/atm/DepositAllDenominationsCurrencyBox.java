package ru.otus.danik_ik.homework06.atm;

/**
 * Выделен во избежание нарушения LSP, так как "кассета, принимающая только один номинал"
 * не является в этом смысле правомерным наследником "кассеты, принимающей любой номинал"
 */
public interface DepositAllDenominationsCurrencyBox extends DepositCurrencyBox {
}
