package ru.mifsan.javacore2017.payment;

import ru.mifsan.javacore2017.bucket.BucketImpl;
import ru.mifsan.javacore2017.product.Product;

public class BucketCalculator extends BucketImpl {

    public double calculateTotalAmount() {
        double result = 0;
        for (Product product : getProducts().values()) {
            result += product.getAmount() * product.getPrice();
        }
        return result;
    }

    public double calculateTotalBonuses() {
        double result = 0;
        for (Product product : getProducts().values()) {
            result += product.getAmount() * product.getBonus();
        }
        return result;
    }

}
