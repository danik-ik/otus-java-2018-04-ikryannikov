package ru.otus.danik_ik.homework09;

import ru.otus.danik_ik.homework09.database.SqlExecutor;

public class Main
{
    public static void main( String[] args ) throws Exception {
        try (SqlExecutor executor = new SqlExecutor()) {
            ResourceHelper rh = new ResourceHelper(Main.class);

            String query = rh.getString("sql/Create_table_user.sql");
            System.out.println(query);
            executor.execUpdate(query);
        }
        try (SqlExecutor executor = new SqlExecutor()) {
            executor.execQuery("select * from user", result -> System.out.println("QUERY OK"));
        }
    }
}
