package ru.otus.danik_ik.homework02.util;

public class StrUtil {

    public static String randomString(int length) {
        final char[] allowedChars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя").toCharArray();
        char[] sequence = new char[length];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = allowedChars[(int)(Math.random()*allowedChars.length)];
        }
        String result = new String(sequence);
        return result;
    }

}
