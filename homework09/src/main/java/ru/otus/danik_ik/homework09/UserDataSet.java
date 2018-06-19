package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.storage.DataSet;

import java.util.Date;

public class UserDataSet extends DataSet {
    private String name;
    private Date bornDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }
}
