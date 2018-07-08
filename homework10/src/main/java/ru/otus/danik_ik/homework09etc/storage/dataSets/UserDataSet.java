package ru.otus.danik_ik.homework09etc.storage.dataSets;

import org.hibernate.annotations.Cascade;
import ru.otus.danik_ik.homework09etc.hibernateStorage.convertors.LocalDateAttributeConverter;
import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.*;
import java.time.LocalDate;
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
    private List<PhoneDataSet> phones;

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

}
