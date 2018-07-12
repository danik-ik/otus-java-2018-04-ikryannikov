package ru.otus.danik_ik.homework11.storage.dataSets;

import ru.otus.danik_ik.homework11.storage.DataSet;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getStreet(), that.getStreet());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUser(), getStreet());
    }
}
