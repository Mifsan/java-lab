package ru.mifsan.javacore2017.product;

public interface Product {
    int getId();
    String getName();
    int getAmount();
    void setAmount(int value);
    ProductType getType();
    double getBonus();
    boolean isForAdult();
    double getPrice();
    int getRandomProductAmount(int maxProductAmount);
}
