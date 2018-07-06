package ru.otus.danik_ik.homework09etc.storage.dataSets;

import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AddressDataSet extends DataSet {
    private String street;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getID() {
        return super.getID();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
