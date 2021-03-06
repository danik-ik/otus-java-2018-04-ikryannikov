package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.storm.DbFieldType;
import ru.otus.danik_ik.homework09.storm.annotations.DbField;
import ru.otus.danik_ik.homework09.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09.storage.DataSet;

import java.time.LocalDate;

@DbTable(name = "Users")
public class UserDataSet extends DataSet {
    private String name;
    private LocalDate bornDate;
    private float rating;

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

    @DbField(name="rating", type = DbFieldType.FLOAT)
    public float getRating() {
        return rating;
    }

    @DbField(name="rating", type = DbFieldType.FLOAT)
    public void setRating(float rating) {
        this.rating = rating;
    }
}
