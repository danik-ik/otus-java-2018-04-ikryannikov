package ru.otus.danik_ik.homework09.database.annotations;

import ru.otus.danik_ik.homework09.database.DbFieldType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DbField {
    String name();
    DbFieldType type();
    boolean isKey() default false;
}
