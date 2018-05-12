package ru.mifsan.javacore2017.bucket;

import ru.mifsan.javacore2017.product.Product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public interface Bucket {
    void add(Product product);
    boolean isEmpty();
    void clear();
    Product remove(Product product);
    Product remove(Iterator<Product> productIterator);
    void forEachProduct(Consumer<Iterator<Product>> action);
    HashMap<Integer, Product> getProducts();
}
