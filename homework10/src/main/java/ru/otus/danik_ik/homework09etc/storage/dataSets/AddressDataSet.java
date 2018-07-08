package ru.otus.danik_ik.homework09etc.storage.dataSets;

import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.*;

@Entity
public class AddressDataSet extends DataSet {
    @OneToOne
    private UserDataSet user;

    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(UserDataSet user, String street) {
        this.user = user;
        this.street = street;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
