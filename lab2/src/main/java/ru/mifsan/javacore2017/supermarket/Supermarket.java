package ru.mifsan.javacore2017.supermarket;

import ru.mifsan.javacore2017.cash_desk.CashDesk;
import ru.mifsan.javacore2017.cash_desk.CashDeskImpl;
import ru.mifsan.javacore2017.customer.Customer;
import ru.mifsan.javacore2017.customer.CustomerType;
import ru.mifsan.javacore2017.payment.Discount;
import ru.mifsan.javacore2017.product.Product;
import ru.mifsan.javacore2017.product.ProductType;
import ru.mifsan.javacore2017.reporter.Reporter;
import ru.mifsan.javacore2017.reporter.ReporterImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

public class Supermarket {
    static final int TIME_UNIT_MINUTES = 5;

    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MILLISECONDS_IN_SECOND = 1000;

    private final List<Customer> customers = new ArrayList<>();
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final Date startDate = new Date();
    private final List<CashDesk> cashDesks = new ArrayList<>();
    private final HashMap<CustomerType, Discount> customerTypeToDiscount = new HashMap<>();
    private final Reporter reporter = new ReporterImpl();

    private int customerId = 0;
    private int workingTimeLeft = 0;
    private int workingTimeMinutes;
    private List<Product> products;

    public Supermarket(int workingTimeMinutes) {
        this.workingTimeMinutes = workingTimeMinutes;
        products = Warehouse.getInstance().getProducts();
        System.out.println("Supermarket products have been formed:");
        for (Product product : products) {
            System.out.printf("%d %s %.2f %d %s %.2f %s \n", product.getId(), product.getName(), product.getPrice(), product.getAmount(), product.getType().toString(), product.getBonus(), product.isForAdult() ? "For adult" : "");
        }
        System.out.println();
    }

    List<Product> getProducts() {
        return products;
    }

    public Discount getDiscount(CustomerType customerType) {
        return customerTypeToDiscount.containsKey(customerType)
                ? customerTypeToDiscount.get(customerType)
                : new Discount(0);
    }

    public void addDiscount(CustomerType customerType, int percentage) {
        customerTypeToDiscount.put(customerType, new Discount(percentage));
    }

    public void addCashDesks(int count) {
        while (count != 0) {
            CashDesk cashDesk = new CashDeskImpl(this);
            cashDesk.setReporter(reporter);
            cashDesks.add(cashDesk);
            --count;
        }
    }

    void forEachCustomer(Consumer<Iterator<Customer>> action) {
        Iterator<Customer> customerIterator = customers.iterator();
        while (customerIterator.hasNext()) {
            action.accept(customerIterator);
        }
    }

    void addCustomer(Customer customer) {
        ++customerId;
        customers.add(customer);
        customer.setId(customerId);
        System.out.printf("New customer `%s` arrived\n", customer.getName());
    }

    Product getProduct(Customer customer, int productId, int productAmount) {
        Product result = Warehouse.getInstance().fetchProduct(productId, productAmount);
        if (result != null) {
            System.out.printf(
                    "Customer %s picked up %s %s of %s\n",
                    customer.getName(),
                    result.getAmount(),
                    result.getType() == ProductType.Packed ? "units" : "gr",
                    result.getName()
            );
        }
        return result;
    }

    public void removeCustomer(Iterator<Customer> customerIterator, Customer customer) {
        String customerName = customer.getName();
        if (!customer.getBucket().isEmpty()) {
            System.out.printf("Attempt of leaving %s with products prevented\n", customerName);
            return;
        }
        System.out.printf("Customer %s left\n", customerName);
        if (customerIterator == null) {
            customers.remove(customer);
        } else {
            customerIterator.remove();
        }
    }

    public void giveProductBack(Product product, Customer customer) {
        Warehouse.getInstance().giveProductBack(product);
        System.out.printf("%s gave %s back\n", customer.getName(), product.getName());
    }

    public void work(SupermarketWork supermarketWorkInterface) {
        System.out.println("Supermarket is opened");
        while (workingTimeMinutes > 0) {
            System.out.printf("Now is %s\n", dateFormat.format(startDate.getTime() + workingTimeLeft * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND));
            supermarketWorkInterface.onEachTimeUnit(this);
            workingTimeMinutes -= TIME_UNIT_MINUTES;
            workingTimeLeft += TIME_UNIT_MINUTES;
        }
        System.out.println("Supermarket is closed");
        supermarketWorkInterface.onFinished(this);
    }

    CashDesk getOptimalCashDesk() {
        CashDesk result = cashDesks.get(0);
        for (CashDesk cashDesk : cashDesks) {
            if (cashDesk.getQueueSize() < result.getQueueSize()) {
                result = cashDesk;
            }
        }
        return result;
    }

    void processCashDesks() {
        cashDesks.forEach((CashDesk cashDesk) -> {
            cashDesk.processQueue(Supermarket.TIME_UNIT_MINUTES);
        });
    }

    void printReport() {
        reporter.printReport();
    }
}
