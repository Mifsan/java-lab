package ru.mifsan.javacore2017.product;

import static ru.mifsan.javacore2017.util.RandomHelper.getRandomNumber;

public class ProductImpl implements Product {

    private final int id;
    private final String name;
    private final double price;
    private final ProductType type;
    private final double bonus;
    private final boolean forAdult;
    private int amount;

    public ProductImpl(
            int id, String name, double price, int amount, ProductType type, double bonus, boolean forAdult
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.amount = amount;
        this.bonus = bonus;
        this.forAdult = forAdult;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int value) {
        amount = value;
    }

    public double getBonus() {
        return bonus;
    }

    public boolean isForAdult() {
        return forAdult;
    }

    public int getRandomProductAmount(int maxProductAmount) {
        if (type == ProductType.Packed) {
            return getRandomNumber(1, maxProductAmount);
        } else {
            return getRandomNumber(100, maxProductAmount);
        }
    }

}
