package ru.otus.danik_ik.homework05.environment;

import ru.otus.danik_ik.homework05.testo.ClassesSupplier;
import ru.otus.danik_ik.homework05.testo.TestEnvironment;

import java.lang.reflect.Method;
import java.util.Collection;

public class SimpleTestEnvironment implements TestEnvironment {
    private final ClassesSupplier classesSupplier;

    public SimpleTestEnvironment(ClassesSupplier classesSupplier) {
        this.classesSupplier = classesSupplier;
    }

    @Override
    public Collection<Class<?>> getClasses(String nameOrPrefix) {
        return classesSupplier.getClasses(nameOrPrefix);
    }

    @Override
    public void beforeClass(boolean isTest, String description) {
        System.out.println("==============================================");
        System.out.println(description);
        System.out.println("==============================================");

    }

    @Override
    public void createdInstance(Object instance, Method method) {
        System.out.println("---------------------------------");
        System.out.println("running: " + instance.getClass().getSimpleName() + "." + method.getName());
    }

    @Override
    public void runningBefore(Object instance, Method method) {
        System.out.println("@Before method in " + instance.getClass().getSimpleName());
    }

    @Override
    public void runningTest(Object instance, Method method) {
        System.out.println("Running test: " + method.getName());
    }

    @Override
    public void runningAfter(Object instance, Method method) {
        System.out.println("@After method in " + instance.getClass().getSimpleName());
    }

    @Override
    public void testIsOk(Object instance, Method method) {
        System.out.println("[OK]");
    }

    @Override
    public void testIsFail(Object instance, Method method, String message) {
        System.out.println("[FAILED]: " + message);
    }

    @Override
    public void testThrewException(Object instance, Method method, Throwable e) {
        System.out.println("[EXCEPTION] " + e.getClass().getCanonicalName());
        e.printStackTrace();
    }
}
