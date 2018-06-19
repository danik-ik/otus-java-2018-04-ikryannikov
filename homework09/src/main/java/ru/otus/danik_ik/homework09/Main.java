package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.database.SqlExecutor;

import java.util.Date;

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
            user.setBornDate(new Date(1978, 10, 30));
            executor.save(user);
            executor.execUpdate("insert into users (name) values ('fsdfsdf')");
          
            executor.execQuery("select * from users", resultSet -> {
                System.out.println("==============================");
                while ( resultSet.next() ) {
                    System.out.printf("Name: %s; date of born: %s\n",
                            resultSet.getString("Name"),
                            resultSet.getDate("bornDate"));
                }
                System.out.println("==============================");
            });
        }
    }
}
