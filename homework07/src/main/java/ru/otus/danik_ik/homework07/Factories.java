package ru.otus.danik_ik.homework07;

import java.util.function.Function;
import java.util.function.Supplier;

public class Factories {
    private Function<String, RemoteAtm> newRemoteAtmFn;
    private Supplier <Department> newDepartmentFn;

    public Factories(Function<String, RemoteAtm> newRemoteAtmFn, Supplier<Department> newDepartmentFn) {
        this.newRemoteAtmFn = newRemoteAtmFn;
        this.newDepartmentFn = newDepartmentFn;
    }
    
    public RemoteAtm newRemoteAtm(String name) {
        return newRemoteAtmFn.apply(name);
    }

    public Department newDepartment() {
        return newDepartmentFn.get();
    }
}
