package ru.otus.danik_ik.homework11;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.otus.danik_ik.homework11.cachedstorage.DbServiceCached;
import ru.otus.danik_ik.homework11.hibernateStorage.DbServiceImpl;
import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.PhoneDataSet;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class HibernateTest {
    private final String caption;
    private final Supplier<DBService> dbServiceSupplier;

    public HibernateTest(String caption, Supplier<DBService> dbServiceSupplier) {
        this.caption = caption;
        this.dbServiceSupplier = dbServiceSupplier;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {"Без кэша", (Supplier<DBService>)() -> new DbServiceImpl()},
                {"С кэшем", (Supplier<DBService>)() -> new DbServiceCached(new DbServiceImpl(), 100)},
        });
    }

    @Test
    public void baseTest() throws Exception {
        try (DBService dbService = dbServiceSupplier.get()) {
            UserDataSet user = new UserDataSet();

            user.setName("Этот, как его...");
            user.setBornDate(LocalDate.of(1900, 01, 01));
            user.setRating(.001F);
            dbService.save(user);

            // ВАЖНО: возможны проблемы с сохраннением дат без времени (необратимое изменение
            // со сдвигом на сутки) из-за настройки тайм-зоны сервара.
            // У меня работает, если таймзона сервера совпадает с клиентом или если таймзона
            // сервера явно (и при этом правильно) указана в строке подключения

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
        try (DBService dbService = dbServiceSupplier.get()) {
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
        try (DBService dbService = dbServiceSupplier.get()) {
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
