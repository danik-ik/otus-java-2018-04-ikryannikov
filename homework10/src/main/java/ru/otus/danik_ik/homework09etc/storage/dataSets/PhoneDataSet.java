package ru.otus.danik_ik.homework09etc.storage.dataSets;

import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PhoneDataSet extends DataSet {
    private String number;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getID() {
        return super.getID();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
