package ru.otus.danik_ik.homework11.cache;

import org.junit.Test;
import ru.otus.danik_ik.homework11.cachedstorage.DbServiceCached;
import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DbServiceCachedTest {
    @Test
    public void testDumb() {
        DbServiceDumb dumb = new DbServiceDumb();
        dumb.read(0);
        dumb.read(0);
        dumb.read(0);
        assertEquals(3, dumb.getReadCount());
        assertNotEquals( dumb.read(1), dumb.read(2));
    }

    @Test
    public void testRead() {
        DbServiceDumb dumb = new DbServiceDumb();
        DBService cached = new DbServiceCached(dumb, 100);
        cached.read(0);
        cached.read(0);
        cached.read(0);
        assertEquals(1, dumb.getReadCount());
    }

    @Test
    public void testReadAll() {
        DbServiceDumb dumb = new DbServiceDumb();
        DBService cached = new DbServiceCached(dumb, 100);
        cached.readAll(); // здесь всё попадает в кэш
        cached.read(0);
        cached.read(3);
        cached.read(5);
        assertEquals(cached.read(0), cached.read(0));
        assertEquals(0, dumb.getReadCount());
    }

    @Test
    public void testSave() {
        DbServiceDumb dumb = new DbServiceDumb();
        DBService cached = new DbServiceCached(dumb, 100);
        UserDataSet user = new UserDataSet();
        user.setID(33);
        user.setName("Ктотонекто гроза морей");
        user.setBornDate(LocalDate.of(2018,07,11));
        user.setRating(.444F);
        user.setStreet("На ступенях горисполкома");
        cached.save(user); // здесь сохраняемый объект попадает в кэш
        cached.read(33);
        cached.read(33);
        cached.read(33);
        assertEquals(user, cached.read(33));
        assertEquals(0, dumb.getReadCount());
    }
}
