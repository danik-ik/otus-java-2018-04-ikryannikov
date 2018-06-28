package ru.otus.danik_ik.homework09etc.storage.dataSets;

import ru.otus.danik_ik.homework09etc.storm.DbFieldType;
import ru.otus.danik_ik.homework09etc.storm.annotations.DbField;
import ru.otus.danik_ik.homework09etc.storm.annotations.DbTable;
import ru.otus.danik_ik.homework09etc.storage.DataSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
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

    @Id
    @Override
    public long getID() {
        return super.getID();
    }

    @Override
    public void setID(long ID) {
        super.setID(ID);
    }
}
