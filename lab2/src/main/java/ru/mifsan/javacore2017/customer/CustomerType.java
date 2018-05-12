package ru.mifsan.javacore2017.customer;

public enum CustomerType {
    Child("Child"),
    Adult("Adult"),
    Retired("Retried");

    private final String name;

    CustomerType(final String name) {
        this.name = name;
    }

    public static CustomerType getRandom() {
        return CustomerType.values()[(int) (Math.random() * (CustomerType.values().length))];
    }

    @Override
    public String toString() {
        return this.name;
    }

}
