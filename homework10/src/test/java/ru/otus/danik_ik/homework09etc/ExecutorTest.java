package ru.otus.danik_ik.homework09etc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.danik_ik.homework09etc.storage.dataSets.UserDataSet;
import ru.otus.danik_ik.homework09etc.stormStorage.SqlExecutor;
import ru.otus.danik_ik.homework09etc.storage.DataSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ExecutorTest {
    @Before
    public void before () {
        try (SqlExecutor executor = new SqlExecutor()) {
            ResourceHelper rh = new ResourceHelper(Main.class);

            String query = rh.getString("sql/Create_table_users.sql");
            executor.execUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void after () {
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.execUpdate("drop table users");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void save() throws Exception {
        UserDataSet user = new UserDataSet();
        user.setName("Этот, как его...");
        user.setBornDate(LocalDate.of(1900, 01, 01));
        user.setRating(7.1F);

        SaveUser(user);
        CompareDbWithObject(user);
    }

    @Test
    public void update() throws Exception {
        final long USER_UD = 100500;

        UserDataSet user = new UserDataSet();
        user.setID(USER_UD);
        user.setName("Этот, как его...");
        user.setBornDate(LocalDate.of(1900, 01, 01));
        user.setRating(7.1F);

        InsertEmptyUserIntoDB(USER_UD);
        SaveUser(user);
        CompareDbWithObject(user);
    }

    @Test
    public void load() throws Exception {
        final long USER_UD = 100500;

        UserDataSet user = new UserDataSet();
        user.setID(USER_UD);
        user.setName("Этот, как его...");
        user.setBornDate(LocalDate.of(1900, 01, 01));
        user.setRating(7.1F);

        SaveUserDirectly(user);

        try (SqlExecutor executor = new SqlExecutor()) {

            UserDataSet anotherUser = executor.load(USER_UD, UserDataSet.class);

            assertTrue(user != anotherUser);
            assertEquals(user.getID(), anotherUser.getID());
            assertEquals(user.getName(), anotherUser.getName());
            assertEquals(user.getBornDate(), anotherUser.getBornDate());
        }
    }

    public void SaveUserDirectly(UserDataSet user) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.execUpdate("insert into users (id, name, bornDate, rating) values (?, ?, ?, ?)",
                    stmt -> {
                        try {
                            stmt.setLong(1, user.getID());
                            stmt.setString(2, user.getName());
                            stmt.setDate(3, java.sql.Date.valueOf(user.getBornDate()));
                            stmt.setFloat(4, user.getRating());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    public void CompareDbWithObject(UserDataSet user) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.execQuery("select count(*) from users", resultSet -> {
                resultSet.first();
                assertEquals(1, resultSet.getLong(1));
            });
            executor.execQuery("select * from users", resultSet -> {
                resultSet.first();
                assertNotEquals(user.getID(), DataSet.UNDEFINED_ID);
                assertEquals(resultSet.getLong("id"), user.getID());
                assertEquals(user.getName(), resultSet.getString("name"));
                assertEquals(user.getBornDate(), resultSet.getDate("borndate").toLocalDate());
            });
        }
    }

    public void SaveUser(UserDataSet user) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.save(user);
        }
    }

    public void InsertEmptyUserIntoDB(long USER_UD) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.execUpdate("insert into users (id) values (?)",
                    stmt -> LongParamSet(stmt, 1, USER_UD));
        }
    }

    private void LongParamSet(PreparedStatement stmt, int parameterIndex, long USER_UD) {
        try {
            stmt.setLong(1, USER_UD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
