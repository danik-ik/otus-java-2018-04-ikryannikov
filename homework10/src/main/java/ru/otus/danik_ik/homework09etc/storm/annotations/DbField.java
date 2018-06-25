package ru.otus.danik_ik.homework09etc.storm.annotations;

import ru.otus.danik_ik.homework09etc.storm.DbFieldType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DbField {
    String name();
    DbFieldType type();
}
