package ru.otus.danik_ik.homework09.storage;

import ru.otus.danik_ik.homework09.storm.DbFieldType;
import ru.otus.danik_ik.homework09.storm.annotations.DbField;
import ru.otus.danik_ik.homework09.storm.annotations.ID;

public abstract class DataSet {
    public static final long UNDEFINED_ID = -1L;
    private long ID = UNDEFINED_ID; // определяется при загрузке или по факту сохранения

    @ID
    @DbField(name="ID", type = DbFieldType.LONG)
    public long getID() {
        return ID;
    }

    @ID
    @DbField(name="ID", type = DbFieldType.LONG)
    public void setID(long ID) {
        this.ID = ID;
    }
}
