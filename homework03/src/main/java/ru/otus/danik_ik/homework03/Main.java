package ru.otus.danik_ik.homework03;

import ru.otus.danik_ik.homework03.collections.MyQueue;

import java.io.InputStreamReader;
import java.util.Queue;
import java.util.Scanner;

public class Main
{
    public static void main( String[] args ) {

        new Main().run();

    }

    private final int CAPACITY = 10;
    private Queue<String> queue = new MyQueue<>(CAPACITY);
    Scanner scanner = new Scanner(new InputStreamReader(System.in));

    private void run() {
        System.out.println("Введи строку, и я положу её в очередь.\n"
                + "Нажми Enter, и я отдам первую строку из очереди.\n"
                + "Но постарайся не брать больше, чем положил. Обижусь!");
        while (true) {
            System.out.printf("У меня заныкано %d строк из %d возможных\n", queue.size(), CAPACITY);
            String input = scanner.nextLine();

            if ("".equals(input)) {
                String out = queue.poll();
                if (out == null) {
                    System.out.println("Жадина! хочешь взять больше, чем положил!!!");
                    break;
                }
                System.out.printf("Заберите и распишитесь! <%s>\n", out);
            } else {
                if (!queue.offer(input)) {
                    System.out.println("Погано мне... Обожрался...");
                } else
                    System.out.printf("Проглочено: <%s>\n", input);
            }
        }
    }
}
