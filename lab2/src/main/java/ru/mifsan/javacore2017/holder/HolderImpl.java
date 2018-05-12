package ru.mifsan.javacore2017.holder;

public class HolderImpl implements Holder {

    private double value;

    public HolderImpl(double value) {
        this.value = value;
    }

    public void increase(double value) {
        this.value += value;
    }

    public void decrease(double value) {
        this.value -= value;
    }

    public double getValue() {
        return value;
    }

}
