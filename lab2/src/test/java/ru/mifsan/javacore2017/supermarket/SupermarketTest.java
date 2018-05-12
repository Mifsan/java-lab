package ru.mifsan.javacore2017.supermarket;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mifsan.javacore2017.cash_desk.CashDesk;
import ru.mifsan.javacore2017.cash_desk.CashDeskImpl;
import ru.mifsan.javacore2017.customer.Customer;
import ru.mifsan.javacore2017.customer.CustomerImpl;
import ru.mifsan.javacore2017.customer.CustomerType;
import ru.mifsan.javacore2017.product.Product;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SupermarketTest {

    private static final int supermarketWorkingTime = 5;
    private static Supermarket supermarket;

    @BeforeAll
    static void beforeAll() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
    }
    private static Field setSupermarketFieldAccessible(Supermarket supermarket, String fieldName) {
        Field field = null;
        try {
            field = supermarket.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Supermarket does not have field \"" + fieldName + "\"");
        }
        return field;
    }
    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        supermarket = new Supermarket(supermarketWorkingTime);
    }
    @Test
    void getDiscountReturnsZeroIfDiscountForCustomerTypeIsNotSet() {
        assertEquals(0, supermarket.getDiscount(CustomerType.Child).getPercentage());
        assertEquals(0, supermarket.getDiscount(CustomerType.Adult).getPercentage());
        assertEquals(0, supermarket.getDiscount(CustomerType.Retired).getPercentage());
    }
    @Test
    void addDiscount() {
        int childDiscount = 10;
        int adultDiscount = 20;
        int retiredDiscount = 30;
        supermarket.addDiscount(CustomerType.Child, childDiscount);
        supermarket.addDiscount(CustomerType.Adult, adultDiscount);
        supermarket.addDiscount(CustomerType.Retired, retiredDiscount);
        assertEquals(childDiscount, supermarket.getDiscount(CustomerType.Child).getPercentage());
        assertEquals(adultDiscount, supermarket.getDiscount(CustomerType.Adult).getPercentage());
        assertEquals(retiredDiscount, supermarket.getDiscount(CustomerType.Retired).getPercentage());
    }
    @Test
    void addCashDesks() throws IllegalAccessException {
        supermarket.addCashDesks(3);
        Field cashDesksField = setSupermarketFieldAccessible(supermarket, "cashDesks");
        List<CashDesk> cashDesks = (List<CashDesk>) cashDesksField.get(supermarket);
        assertEquals(cashDesks.size(), 3);
    }
    @Test
    void addCustomer() {
        supermarket.addCustomer(createCustomer(1));
        supermarket.addCustomer(createCustomer(2));
        supermarket.forEachCustomer((Iterator<Customer> customerIterator) -> {
            Customer customer = customerIterator.next();
            assertEquals(customer.getType().toString() + " " + customer.getId(), customer.getName());
        });
    }
    @Test
    void getProduct() {
        int productId = 1;
        int productAmount = 1;
        Customer customer = createCustomer(1);
        Product product = supermarket.getProduct(customer, productId, productAmount);
        assertEquals(productId, product.getId());
        assertEquals(productAmount, product.getAmount());

        supermarket.giveProductBack(product, customer);
    }
    @Test
    void getBestCashDesk() throws IllegalAccessException {
        CashDesk firstCashDesk = new CashDeskImpl(supermarket);
        addCustomersToCashDesk(firstCashDesk, 3);

        CashDesk secondCashDesk = new CashDeskImpl(supermarket);
        addCustomersToCashDesk(secondCashDesk, 2);

        CashDesk thirdCashDesk = new CashDeskImpl(supermarket);
        addCustomersToCashDesk(thirdCashDesk, 1);

        Field cashDesksField = setSupermarketFieldAccessible(supermarket, "cashDesks");
        List<CashDesk> cashDesks = (List<CashDesk>) cashDesksField.get(supermarket);
        cashDesks.add(firstCashDesk);
        cashDesks.add(secondCashDesk);
        cashDesks.add(thirdCashDesk);

        assertEquals(thirdCashDesk, supermarket.getOptimalCashDesk());
    }
    private void addCustomersToCashDesk(CashDesk cashDesk, int count) {
        for (int i = 0; i < count; ++i) {
            cashDesk.addCustomerToQueue(createCustomer(i));
        }
    }
    private Customer createCustomer(int id) {
        Customer customer = new CustomerImpl(CustomerType.Child, 0, 0, 0);
        customer.setId(id);

        return customer;
    }

    @Test
    void getProducts() {
        try {
            supermarket.getProducts();
        } catch (Exception exception) {
            fail("Supermarket getProducts shouldn't throw exception");
        }
    }

    @Test
    void work() {
        try {
            supermarket.work(new SupermarketWork() {
                @Override
                public void onEachTimeUnit(Supermarket supermarket) {

                }
                @Override
                public void onFinished(Supermarket supermarket) {

                }
            });
        } catch (Exception e) {
            fail("Supermarket work shouldn't throw exception");
        }
    }

}