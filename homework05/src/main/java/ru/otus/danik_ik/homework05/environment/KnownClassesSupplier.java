package ru.otus.danik_ik.homework05.environment;

import ru.otus.danik_ik.homework05.testo.ClassesSupplier;

import java.util.*;
import java.util.stream.Collectors;

public class KnownClassesSupplier implements ClassesSupplier {
    private static final List<String> KNOWN_CLASS_NAMES = Arrays.asList(new String[]{
            "ru.otus.danik_ik.homework05.testo.test1.Class1",
            "ru.otus.danik_ik.homework05.testo.test1.Class2",
            "ru.otus.danik_ik.homework05.testo.test1.NoAfter",
            "ru.otus.danik_ik.homework05.testo.test1.NoBefore",
            "ru.otus.danik_ik.homework05.testo.test1.WithParams",
            "ru.otus.danik_ik.homework05.testo.test1.NoTest",
            "ru.otus.danik_ik.homework05.testo.test2.Class1",
    });

    private final Set<Class<?>> KNOWN_CLASSES = KNOWN_CLASS_NAMES.stream()
            .map(this::nameToClass)
            .filter(this::assigned)
            .collect(Collectors.toSet());

    @Override
    public Collection<Class<?>> getClasses(String nameOrPrefix) {
        return KNOWN_CLASSES.stream()
                .filter(it -> it.getCanonicalName().startsWith(nameOrPrefix))
                .collect(Collectors.toSet());
    }

    private Class<?> nameToClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            handleClassLoadingError(className, e);
            return null;
        }
    };

    private void handleClassLoadingError(String className, Exception cause) {
        System.err.println("Can't load class by name: «" + className + "»");
        cause.printStackTrace();
    }

    boolean assigned(Object it) { return it != null; }
}
