package ru.otus.danik_ik.homework02b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StringTutorTest {
    private final boolean expected;
    private final String greeting;
    private final String errorMessage;

    public StringTutorTest(boolean expected, String greeting, String errorMessage) {
        this.expected = expected;
        this.greeting = greeting;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters(name = " {index}) «{1}»: ожидается {0} // {2}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {true, "Привет, Иван Иванов!", ""},
                {true, "Привет,Петр Первый!", ""},
                {true, "Привет, Петр Первый!", ""},
                {true, "Привет, Петр Первый !", ""},
                {false, "Здравствуйте, Петр Первый!", "В начале должно быть слово Привет и запятая"},
                {false, "Привет, Петр Первый", "В конце должен быть восклицательный знак"},
                {false, "Привет, Ли Сунь!", "Имя слишком короткое"},
                {false, "Привет, Куй Ли!", "Фамилия слишком короткая"},
                {false, "Привет, Петр!", "Должны быть указаны и имя, и фамилия"},
                {false, "Привет, петр Первый!", "Первая буква имени должна быть заглавной"},
                {false, "Привет, Петр первый!", "Первая буква фамилии должна быть заглавной"},
        });
    }

    @Test
    public void testCheckGreeting() {
        assertEquals(errorMessage, expected, new StringTutor().checkGreeting(greeting));
    }

}
