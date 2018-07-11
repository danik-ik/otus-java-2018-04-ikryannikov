package ru.otus.danik_ik.homework11.storage.dataSets;

import ru.otus.danik_ik.homework11.hibernateStorage.convertors.LocalDateAttributeConverter;
import ru.otus.danik_ik.homework11.storage.DataSet;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class UserDataSet extends DataSet {
    private String name;
    private LocalDate bornDate;
    private float rating;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private AddressDataSet address;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<PhoneDataSet> phones = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Convert(converter = LocalDateAttributeConverter.class)
    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
        address.setUser(this);
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setStreet(String street) {
        this.address = new AddressDataSet(this, street);
    }

    public String getStreet() {
        return address.getStreet();
    }

    public List<PhoneDataSet> getPhones() {
        return Collections.unmodifiableList(phones);
    }

    public void addPhone (PhoneDataSet phone) {
        phones.add(phone);
        phone.setUser(this);
    }

    public void removePhone(PhoneDataSet phone) {
        phones.remove(phone);
    }
}
