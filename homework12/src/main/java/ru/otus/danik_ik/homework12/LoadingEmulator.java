package ru.otus.danik_ik.homework12;

import ru.otus.danik_ik.homework11.storage.DBService;

import java.util.concurrent.ThreadLocalRandom;

public class LoadingEmulator {
    private static final int MAX_INDEX = 4;
    private static final int PAUSE = 300;

    private final DBService dbService;

    public LoadingEmulator(DBService dbService) {
        this.dbService = dbService;
    }

    public void runAsThread() {
        new Thread(() -> this.run()).run();
    }

    private void run() {
        createData();
        while (!Thread.currentThread().isInterrupted()) {
            dbService.read(getRandomIndex());
            sleep(PAUSE);
        }
    }

    private long getRandomIndex() {
        return ThreadLocalRandom.current().nextLong(0, MAX_INDEX + 1);
    }

    private void sleep(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void createData() {
        // TODO: 19.07.2018
    }
}
