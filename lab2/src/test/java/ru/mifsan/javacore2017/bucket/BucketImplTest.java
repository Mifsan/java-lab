package ru.mifsan.javacore2017.bucket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.mifsan.javacore2017.product.Product;
import ru.mifsan.javacore2017.product.ProductImpl;
import ru.mifsan.javacore2017.product.ProductType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BucketImplTest {

    private BucketImpl bucket;

    @BeforeEach
    void setUp() {
        bucket = new BucketImpl();
    }

    @Test
    void add() {
        bucket.add(getProduct(1));
        assertEquals(bucket.getProducts().size(), 1);
        assertTrue(areProductsEqual(bucket.getProducts().values().iterator().next(), getProduct(1)));

        bucket.add(getProduct(2));
        assertEquals(bucket.getProducts().size(), 2);

        Iterator<Product> productIterator = bucket.getProducts().values().iterator();
        Product product = null;
        while (productIterator.hasNext()) {
            product = productIterator.next();
        }
        assertNotNull(product);
        assertTrue(areProductsEqual(product, getProduct(2)));

        int amountBefore = getProduct(2).getAmount();
        bucket.add(getProduct(2));
        assertEquals(bucket.getProducts().size(), 2);

        Product expectedProduct = getProduct(2);
        expectedProduct.setAmount(2 * amountBefore);
        Product product1 = getProductById(bucket, 2);
        assertNotNull(product1);
        assertTrue(areProductsEqual(product1, expectedProduct));
    }

    @Test
    void isEmpty() {
        assertTrue(bucket.getProducts().isEmpty());
        bucket.add(getProduct(1));
        assertFalse(bucket.getProducts().isEmpty());
    }

    @Test
    void removeByProduct() {
        Product product = getProduct(1);
        bucket.add(product);
        Product removedProduct = bucket.remove(product);
        assertTrue(bucket.isEmpty());
        assertEquals(product, removedProduct);
    }

    @Test
    void removeByProductIterator() {
        Product product = getProduct(1);
        bucket.add(product);
        Iterator<Product> productIterator = bucket.getProducts().values().iterator();
        Product removedProduct = bucket.remove(productIterator);
        assertTrue(bucket.isEmpty());
        assertEquals(product, removedProduct);
    }

    @Test
    void clear() {
        bucket.add(getProduct(1));
        bucket.add(getProduct(2));
        bucket.add(getProduct(3));
        assertEquals(bucket.getProducts().size(), 3);
        bucket.clear();
        assertTrue(bucket.isEmpty());
    }

    private Product getProductById(Bucket bucket, int id) {
        for (Product product : bucket.getProducts().values()) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }
    
    @Test
    void forEachProduct() {
        bucket.add(getProduct(1));
        bucket.add(getProduct(2));
        bucket.add(getProduct(3));
        List<Product> products = new ArrayList<>();
        bucket.forEachProduct((Iterator<Product> productIterator) -> products.add(productIterator.next()));
        for (int i = 0; i < products.size(); ++i) {
            Product product = products.get(i);
            Product expectedProduct = getProduct(i + 1);
            assertTrue(areProductsEqual(product, expectedProduct));
        }
    }

    private Product getProduct(int id) {
        return new ProductImpl(id, "Test", 2, 3, ProductType.Packed, 4, true);
    }

    private boolean areProductsEqual(Product lhs, Product rhs) {
        return lhs.getId() == rhs.getId()
                && lhs.getName().equals(rhs.getName())
                && lhs.getPrice() == rhs.getPrice()
                && lhs.getAmount() == rhs.getAmount()
                && lhs.getType() == rhs.getType()
                && lhs.getBonus() == rhs.getBonus()
                && lhs.isForAdult() == rhs.isForAdult();
    }

}