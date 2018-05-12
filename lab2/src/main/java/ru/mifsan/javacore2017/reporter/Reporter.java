package ru.mifsan.javacore2017.reporter;

import ru.mifsan.javacore2017.product.Product;

public interface Reporter {
    void addProductSelling(Product product);
    void printReport();
}
