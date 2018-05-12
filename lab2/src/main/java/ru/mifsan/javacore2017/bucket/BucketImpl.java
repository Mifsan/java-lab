package ru.mifsan.javacore2017.bucket;

import ru.mifsan.javacore2017.product.Product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class BucketImpl implements Bucket {

    private final HashMap<Integer, Product> products = new HashMap<>();

    public HashMap<Integer, Product> getProducts() {
        return products;
    }

    public void add(Product product) {
        if (products.containsKey(product.getId())) {
            Product productInContainer = products.get(product.getId());
            productInContainer.setAmount(productInContainer.getAmount() + product.getAmount());
        } else {
            products.put(product.getId(), product);
        }
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Product remove(Product product) {
        return products.remove(product.getId());
    }

    public Product remove(Iterator<Product> productIterator) {
        Product result = productIterator.next();
        productIterator.remove();
        return result;
    }

    public void clear() {
        products.clear();
    }

    public void forEachProduct(Consumer<Iterator<Product>> action) {
        Iterator<Product> productIterator = getProducts().values().iterator();
        while (productIterator.hasNext()) {
            action.accept(productIterator);
        }
    }



}
