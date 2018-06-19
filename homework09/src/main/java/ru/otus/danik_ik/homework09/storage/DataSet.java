package ru.otus.danik_ik.homework09.storage;

import ru.otus.danik_ik.homework09.database.DbFieldType;
import ru.otus.danik_ik.homework09.database.annotations.DbField;

import java.sql.SQLException;

public abstract class DataSet {
    public static final long UNDEFINED_ID = -1L;
    private long ID = UNDEFINED_ID; // определяется при загрузке или по факту сохранения

    @DbField(name="ID", type = DbFieldType.LONG, isKey = true)
    public long getID() {
        return ID;
    }

    @DbField(name="ID", type = DbFieldType.LONG, isKey = true)
    /* package */ void setID(long ID) {
        this.ID = ID;
    }
}
