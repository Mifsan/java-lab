package ru.mifsan.javacore2017.supermarket;

import ru.mifsan.javacore2017.product.Product;
import ru.mifsan.javacore2017.product.ProductImpl;
import ru.mifsan.javacore2017.product.ProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Warehouse {
    private final HashMap<Integer, Product> products = new HashMap<Integer, Product>() {{
        put(1, new ProductImpl(1, "Bread", 20, 100, ProductType.Packed, 10, false));
        put(2, new ProductImpl(2, "Milk", 50, 100, ProductType.Packed, 10, false));
        put(3, new ProductImpl(3, "Buckwheat", 0.06, 10000, ProductType.Bulk, 10, false));
        put(4, new ProductImpl(4, "Rice groats", 0.05, 15000, ProductType.Bulk, 10, false));
        put(5, new ProductImpl(5, "Curd", 200, 50, ProductType.Packed, 15, false));
        put(6, new ProductImpl(6, "Kvass", 100, 60, ProductType.Packed, 20, true));
        put(7, new ProductImpl(7, "Crisp", 100, 50, ProductType.Packed, 20, true));
    }};

    private static Warehouse instance = null;
    private Warehouse() { }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public Product fetchProduct(int productId, int productAmount) {
        if (!products.containsKey(productId) || products.get(productId).getAmount() - productAmount < 0) {
            return null;
        }
        Product product = products.get(productId);
        product.setAmount(product.getAmount() - productAmount);
        return new ProductImpl(
                product.getId(),
                product.getName(),
                product.getPrice(),
                productAmount,
                product.getType(),
                product.getBonus(),
                product.isForAdult()
        );
    }

    public void giveProductBack(Product productToGiveBack) {
        Product product = products.get(productToGiveBack.getId());
        product.setAmount(product.getAmount() + productToGiveBack.getAmount());
    }
}
