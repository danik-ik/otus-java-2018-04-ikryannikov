package ru.otus.danik_ik.homework09.storage;

import java.sql.SQLException;

public abstract class DataSet {
    private long ID;

    public long getID() {
        return ID;
    }

    /* package */ void setID(long ID) {
        this.ID = ID;
    }
}
