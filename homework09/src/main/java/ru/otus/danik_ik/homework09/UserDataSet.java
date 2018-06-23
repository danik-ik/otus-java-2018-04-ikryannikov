package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.storm.DbFieldType;
import ru.otus.danik_ik.homework09.storm.annotations.DbField;
import ru.otus.danik_ik.homework09.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09.storage.DataSet;

import java.time.LocalDate;

@DbTable(name = "Users")
public class UserDataSet extends DataSet {
    @DbField(name="name", type = DbFieldType.STRING)
    private String name;
    private LocalDate bornDate;

    @DbField(name="name", type = DbFieldType.STRING)
    public String getName() {
        return name;
    }

    @DbField(name="name", type = DbFieldType.STRING)
    public void setName(String name) {
        this.name = name;
    }

    @DbField(name="bornDate", type = DbFieldType.DATE)
    public LocalDate getBornDate() {
        return bornDate;
    }

    @DbField(name="bornDate", type = DbFieldType.DATE)
    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }
}
