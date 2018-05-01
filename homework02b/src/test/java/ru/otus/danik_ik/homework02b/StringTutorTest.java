package ru.otus.danik_ik.homework02b;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class StringTutorTest {
    @Test
    public void testcheckGreeting() {
        assertTrue(new StringTutor().checkGreeting("Привет, Иван Иванов!"));
        assertTrue(new StringTutor().checkGreeting("Привет,Петр Первый!"));
        assertTrue(new StringTutor().checkGreeting("Привет, Петр Первый!"));
        assertTrue(new StringTutor().checkGreeting("Привет, Петр Первый !"));

        assertFalse("В начале должно быть слово Привет и запятая",
                new StringTutor().checkGreeting("Здравствуйте, Петр Первый!"));
        assertFalse("В конце должен быть восклицательный знак",
                new StringTutor().checkGreeting("Привет, Петр Первый"));
        assertFalse("Имя слишком короткое",
                new StringTutor().checkGreeting("Привет, Ли Сунь!"));
        assertFalse("Фамилия слишком короткая",
                new StringTutor().checkGreeting("Привет, Куй Ли!"));
        assertFalse("Должны быть указаны и имя, и фамилия",
                new StringTutor().checkGreeting("Привет, Петр!"));
        assertFalse("Первая буква имени должна быть заглавной",
                new StringTutor().checkGreeting("Привет, петр Первый!"));
        assertFalse("Первая буква фамилии должна быть заглавной",
                new StringTutor().checkGreeting("Привет, Петр первый!"));
    }

}
