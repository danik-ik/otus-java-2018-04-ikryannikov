package ru.otus.danik_ik.homework07;

import java.util.function.Supplier;

public class Factories {
    private Supplier <RemoteAtm> newRemoteAtmFn;
    private Supplier <Department> newDepartmentFn;

    public Factories(Supplier<RemoteAtm> newRemoteAtmFn, Supplier<Department> newDepartmentFn) {
        this.newRemoteAtmFn = newRemoteAtmFn;
        this.newDepartmentFn = newDepartmentFn;
    }
    
    public RemoteAtm newRemoteAtm() {
        return newRemoteAtmFn.get();
    }

    public Department newDepartment() {
        return newDepartmentFn.get();
    }
}
