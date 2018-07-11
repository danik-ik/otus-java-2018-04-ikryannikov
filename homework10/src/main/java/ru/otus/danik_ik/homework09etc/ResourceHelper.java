package ru.otus.danik_ik.homework09etc;

import java.io.IOException;

public class ResourceHelper {
    private final char SEPARATOR = '/';
    private final String basePath;
    private final ClassLoader classLoader;

    public ResourceHelper(Class base) {
        this.basePath = base.getPackageName().replace('.', SEPARATOR) + SEPARATOR;
        this.classLoader = base.getClassLoader();
    }

    public ResourceHelper(Object base) {
        this(base.getClass());
    }

    public String getString(String relativePath) {
        String query = null;
        try {
            return new String(
                    classLoader.getResourceAsStream(basePath + relativePath)
                        .readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
