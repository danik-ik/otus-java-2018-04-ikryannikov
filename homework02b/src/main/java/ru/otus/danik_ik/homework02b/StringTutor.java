package ru.otus.danik_ik.homework02b;

import java.util.regex.Pattern;

public class StringTutor {
    /**
     *  Убедитесь, что приветствие greeting имеет вид
     *  Привет, Иван Иванов!
     *  или
     *  Привет,Петр Первый!
     *  т.е. начинается на Привет, заканчивается на !
     *  и содержит 2 слова для имени и фамилии,
     *  причем имя и фамилия не короче 3 букв
     *  и начинаются с большой буквы
     */

    String regEx = "^Привет,\\s*[А-Я][А-Яа-я]{2,}\\s+[А-Я][А-Яа-я]{2,}\\s*!$";

    public boolean checkGreeting(String greeting) {
        return greeting.matches(regEx);
    }
}
