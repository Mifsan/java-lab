package ru.mifsan.javacore2017.reporter;

import ru.mifsan.javacore2017.payment.BucketCalculator;
import ru.mifsan.javacore2017.product.Product;

public class ReporterImpl implements Reporter {

    private final BucketCalculator productAdder = new BucketCalculator();

    public void addProductSelling(Product product) {
        productAdder.add(product);
    }

    public void printReport() {
        System.out.println("Report:");
        System.out.println("---------------------------------------");
        System.out.printf("| %-15s | %-10s | %-7s |%n", "Product", "Amount", "Earning");
        System.out.println("---------------------------------------");
        productAdder.getProducts().values().forEach((Product product) ->
                System.out.printf("| %-15s | %-10d | %-7.2f |%n",
                        product.getName(), product.getAmount(), product.getAmount() * product.getPrice())
        );
        System.out.println("---------------------------------------");
        System.out.printf("Total earning: %.2f\n", productAdder.calculateTotalAmount());
    }

}
