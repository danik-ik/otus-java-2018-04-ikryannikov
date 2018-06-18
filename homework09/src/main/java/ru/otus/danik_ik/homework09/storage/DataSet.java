package ru.otus.danik_ik.homework09.storage;

import java.sql.SQLException;

public abstract class DataSet {
    public static final long UNDEFINED_ID = -1L;
    private long ID = UNDEFINED_ID; // определяется при загрузке или по факту сохранения

    public long getID() {
        return ID;
    }

    /* package */ void setID(long ID) {
        this.ID = ID;
    }
}
