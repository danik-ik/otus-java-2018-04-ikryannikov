package ru.otus.danik_ik.homework13;

import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.util.concurrent.ThreadLocalRandom;

public class LoadingEmulator {
    private static final int MAX_INDEX = 4;
    private static final int PAUSE = 300;

    private final DBService dbService;

    public LoadingEmulator(DBService dbService) {
        this.dbService = dbService;
    }

    public void runAsThread() {
        new Thread(this::run).start();
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
        for (int i = 0; i <= MAX_INDEX; i++) {
            UserDataSet user = new UserDataSet();
            user.setName("User " + i);
            dbService.save(user);
        }
    }
}
