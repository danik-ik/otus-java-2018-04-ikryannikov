package ru.otus.danik_ik.homework09etc.storage.dataSets;

import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.*;

@Entity
public class PhoneDataSet extends DataSet {
    @ManyToOne
    private UserDataSet user;
    private String number;

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
