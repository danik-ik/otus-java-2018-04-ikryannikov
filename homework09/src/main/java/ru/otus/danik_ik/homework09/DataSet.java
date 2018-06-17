package ru.otus.danik_ik.homework09;

import java.sql.SQLException;

public abstract class DataSet {
    private long ID;

    public abstract <T extends DataSet> T load(long id) throws SQLException;
    public abstract void save() throws SQLException;

    public long getID() {
        return ID;
    }
}
