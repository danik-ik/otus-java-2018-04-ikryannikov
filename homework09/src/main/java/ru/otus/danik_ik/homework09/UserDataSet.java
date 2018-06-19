package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.database.DbFieldType;
import ru.otus.danik_ik.homework09.database.annotations.DbField;
import ru.otus.danik_ik.homework09.database.annotations.DbTable;
import ru.otus.danik_ik.homework09.storage.DataSet;

import java.util.Date;

@DbTable(name = "Users")
public class UserDataSet extends DataSet {
    @DbField(name="name", type = DbFieldType.STRING)
    private String name;
    private Date bornDate;

    @DbField(name="name", type = DbFieldType.STRING)
    public String getName() {
        return name;
    }

    @DbField(name="name", type = DbFieldType.STRING)
    public void setName(String name) {
        this.name = name;
    }

    @DbField(name="bornDate", type = DbFieldType.DATE)
    public Date getBornDate() {
        return bornDate;
    }

    @DbField(name="bornDate", type = DbFieldType.DATE)
    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }
}
