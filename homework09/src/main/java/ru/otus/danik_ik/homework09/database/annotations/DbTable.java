package ru.otus.danik_ik.homework09.database.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DbTable {
    String name();
}
