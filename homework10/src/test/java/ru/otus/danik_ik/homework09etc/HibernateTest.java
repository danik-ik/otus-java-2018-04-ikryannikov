package ru.otus.danik_ik.homework09etc;

import org.junit.Test;
import ru.otus.danik_ik.homework09etc.hibernateStorage.DbServiceImpl;
import ru.otus.danik_ik.homework09etc.storage.DBService;
import ru.otus.danik_ik.homework09etc.storage.dataSets.PhoneDataSet;
import ru.otus.danik_ik.homework09etc.storage.dataSets.UserDataSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HibernateTest {
    
    @Test 
    public void baseTest() throws Exception {
        try (DBService dbService = new DbServiceImpl()) {
            UserDataSet user = new UserDataSet();

            user.setName("Этот, как его...");
            user.setBornDate(LocalDate.of(1900, 01, 01));
            user.setRating(.001F);
            dbService.save(user);

            testSaveAndLoad(dbService, user);
            loadAndPrintUser(dbService, 1);

            user.setName("Так это же я!");
            user.setBornDate(LocalDate.of(1978, 10, 30));
            user.setRating(100.5F);
            dbService.save(user);

            testSaveAndLoad(dbService, user);
            loadAndPrintUser(dbService, 1);
        }
    }

    @Test
    public void addressTest() throws Exception {
        try (DBService dbService = new DbServiceImpl()) {
            UserDataSet user = new UserDataSet();

            user.setName("Этот, как его...");
            user.setBornDate(LocalDate.of(1900, 01, 01));
            user.setRating(.001F);
            user.setStreet("Гдетотамовский проспект");
            dbService.save(user);

            UserDataSet loadedUser;
            loadedUser = dbService.read(user.getID());
            assertNotNull(loadedUser);

            assertNotNull(loadedUser.getAddress());
            System.out.println(loadedUser.getStreet());
            assertEquals(user.getStreet(), loadedUser.getStreet());
        }
    }

    @Test
    public void phonesTest() throws Exception {
        try (DBService dbService = new DbServiceImpl()) {
            UserDataSet user = new UserDataSet();

            user.setName("Этот, как его...");
            user.setBornDate(LocalDate.of(1900, 01, 01));
            user.setRating(.001F);
            user.addPhone(new PhoneDataSet("+7 654 321 09 87"));
            user.addPhone(new PhoneDataSet("+7 654 321 09 88"));
            dbService.save(user);

            UserDataSet loadedUser;
            loadedUser = dbService.read(user.getID());
            assertNotNull(loadedUser);

            assertNotNull(loadedUser.getPhones());
            assertEquals(2, loadedUser.getPhones().size());

            System.out.println(loadedUser.getPhones().get(0).getNumber());
            System.out.println(loadedUser.getPhones().get(1).getNumber());

            List<String> phones = new ArrayList<>(loadedUser.getPhones()
                    .stream()
                    .map(a -> a.getNumber())
                    .sorted()
                    .collect(Collectors.toList()));

            assertEquals("+7 654 321 09 87", phones.get(0));
            assertEquals("+7 654 321 09 88", phones.get(1));

        }
    }

    private void testSaveAndLoad(DBService dbService, UserDataSet user) {
        UserDataSet loadedUser;
        loadedUser = dbService.read(user.getID());
        assertNotNull(loadedUser);

        assertEquals(user.getID(), loadedUser.getID());
        assertEquals(user.getName(), loadedUser.getName());
        assertEquals(user.getBornDate(), loadedUser.getBornDate());
        assertEquals(user.getRating(), loadedUser.getRating(), .0001);
    }

    private void loadAndPrintUser(DBService dbService, long id) {
        UserDataSet loadedUser;
        loadedUser = dbService.read(id);
        assertNotNull(loadedUser);

        System.out.println("загружено из БД по id=" + id);
        System.out.println(loadedUser.getID());
        System.out.println(loadedUser.getName());
        System.out.println(loadedUser.getBornDate());
        System.out.println(loadedUser.getRating());
    }
}
