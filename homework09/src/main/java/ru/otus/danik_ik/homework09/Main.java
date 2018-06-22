package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.database.SqlExecutor;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main
{
    public static void main( String[] args ) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            ResourceHelper rh = new ResourceHelper(Main.class);

            String query = rh.getString("sql/Create_table_users.sql");
            System.out.println(query);
            executor.execUpdate(query);
        }
        try (SqlExecutor executor = new SqlExecutor()) {
          
            executor.execQuery("select * from users", result -> System.out.println("QUERY OK"));
        }
        try (SqlExecutor executor = new SqlExecutor()) {
            UserDataSet user = new UserDataSet();
            
            user.setName("Этот, как его...");
            user.setBornDate(LocalDate.of(1900, 01, 01));
            executor.save(user);
            printUsers(executor);
            
            user.setName("Так это же я!");
            user.setBornDate(LocalDate.of(1978, 10, 30));
            executor.save(user);
            printUsers(executor);

            UserDataSet loadedUser = executor.load(1, UserDataSet.class);

            System.out.println("загружено из БД по id=1");
            System.out.println(loadedUser.getID());
            System.out.println(loadedUser.getName());
            System.out.println(loadedUser.getBornDate());
        }
    }

    private static void printUsers(SqlExecutor executor) throws SQLException {
        executor.execQuery("select * from users", resultSet -> {
            System.out.println("==============================");
            while (resultSet.next()) {
                System.out.printf("ID: %d; Name: %s; date of born: %s\n",
                        resultSet.getLong("Id"),
                        resultSet.getString("Name"),
                        resultSet.getDate("bornDate"));
            }
            System.out.println("------------------------------");
        });
    }
}
