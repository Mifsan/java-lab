package ru.mifsan.javacore2017.payment;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mifsan.javacore2017.product.Product;
import ru.mifsan.javacore2017.product.ProductImpl;
import ru.mifsan.javacore2017.product.ProductType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BucketCalculatorTest {

    private static final BucketCalculator bucketCalculator  = new BucketCalculator();

    private static final List<Product> products = new ArrayList<Product>() {{
        add(createProduct(1, 10, 1, ProductType.Packed, 1));
        add(createProduct(2, 20, 5, ProductType.Packed, 10));
        add(createProduct(3, 1.5, 100, ProductType.Bulk, 15));
        add(createProduct(4, 15, 10, ProductType.Packed, 20));
        add(createProduct(5, 2.3, 150, ProductType.Bulk, 5));
        add(createProduct(6, 30, 20, ProductType.Packed, 100));
    }};

    @BeforeAll
    static void setUp() {
        products.forEach(bucketCalculator::add);
    }
    private static Product createProduct(int id, double price, int amount, ProductType type, int bonus) {
        return new ProductImpl(id, "Продукт", price, amount, type, bonus, false);
    }
    @Test
    void calculateTotalAmount() {
        double totalAmount = 0;
        for (Product product : products) {
            totalAmount += product.getPrice() * product.getAmount();
        }
        assertEquals(totalAmount, bucketCalculator.calculateTotalAmount());
    }
    @Test
    void calculateTotalBonuses() {
        double totalBonuses = 0;
        for (Product product : products) {
            totalBonuses += product.getBonus() * product.getAmount();
        }
        assertEquals(totalBonuses, bucketCalculator.calculateTotalBonuses());
    }

}