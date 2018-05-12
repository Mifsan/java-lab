package ru.mifsan.javacore2017.cash_desk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.mifsan.javacore2017.customer.Customer;
import ru.mifsan.javacore2017.customer.CustomerImpl;
import ru.mifsan.javacore2017.customer.CustomerType;
import ru.mifsan.javacore2017.payment.BucketCalculator;
import ru.mifsan.javacore2017.product.Product;
import ru.mifsan.javacore2017.product.ProductImpl;
import ru.mifsan.javacore2017.product.ProductType;
import ru.mifsan.javacore2017.reporter.Reporter;
import ru.mifsan.javacore2017.reporter.ReporterImpl;
import ru.mifsan.javacore2017.supermarket.Supermarket;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CashDeskImplTest {

    private static final int supermarketWorkingTime = 5;
    private static final int customerCount = 5;
    private static final Product processQueueProduct = new ProductImpl(1, "Продукт", 50, 2, ProductType.Packed, 10, false);
    private CashDesk cashDesk;

    @BeforeAll
    static void beforeAll() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
    }

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        cashDesk = new CashDeskImpl(new Supermarket(supermarketWorkingTime));
    }

    @Test
    void processQueueGiveProductBackIfProductIsForAdultAndCustomerIsChild() {
        Customer customer = createCustomer(100, 99, 99);
        customer.getBucket().add(new ProductImpl(1, "Продукт для взрослых", 50, 2, ProductType.Packed, 10, true));
        cashDesk.addCustomerToQueue(customer);
        cashDesk.processQueue(1);
        assertEquals(0, cashDesk.getQueueSize());
        assertTrue(customer.getBucket().isEmpty());
        assertEquals(100, customer.getCash());
    }

    @Test
    void processQueueGiveProductBackIfProductIsTooExpensiveForChild() {
        Customer customer = createCustomer(100, 99, 99);
        customer.getBucket().add(new ProductImpl(1, "Очень дорогой товар", 99999, 1, ProductType.Packed, 10, false));
        cashDesk.addCustomerToQueue(customer);
        cashDesk.processQueue(1);
        assertEquals(0, cashDesk.getQueueSize());
        assertTrue(customer.getBucket().isEmpty());
        assertEquals(100, customer.getCash());
    }

    @Test
    void processQueuePayByCash() throws IllegalAccessException {
        Product product = processQueueProduct;
        Customer customer = createCustomer(100, 99, 99);
        customer.getBucket().add(product);
        cashDesk.addCustomerToQueue(customer);
        Reporter reporter = new ReporterImpl();
        cashDesk.setReporter(reporter);
        cashDesk.processQueue(1);
        assertEquals(0, cashDesk.getQueueSize());
        assertTrue(customer.getBucket().isEmpty());
        assertEquals(0, customer.getCash());

        Field productAdderField = setReporterFieldAccessible(reporter, "productAdder");
        BucketCalculator productAdder = (BucketCalculator) productAdderField.get(reporter);
        assertFalse(productAdder.isEmpty());
        assertEquals(product.getPrice() * product.getAmount(), productAdder.calculateTotalAmount());
        assertEquals(product.getBonus() * product.getAmount(), productAdder.calculateTotalBonuses());
    }

    @Test
    void processQueuePayByCardCash() {
        Customer customer = createCustomer(99, 100, 99);
        customer.getBucket().add(processQueueProduct);
        cashDesk.addCustomerToQueue(customer);
        cashDesk.processQueue(1);
        assertEquals(0, customer.getCardCash());
    }

    @Test
    void processQueuePayByBonuses() {
        Customer customer = createCustomer(99, 99, 100);
        customer.getBucket().add(processQueueProduct);
        cashDesk.addCustomerToQueue(customer);
        cashDesk.processQueue(1);
        assertEquals(0, customer.getBonusCount());
    }

    private void addCustomersToCashDesk(CashDesk cashDesk, int count) {
        for (int i = 0; i < count; ++i) {
            cashDesk.addCustomerToQueue(createCustomer(0, 0, 0));
        }
    }

    private Customer createCustomer(double cash, double cardCash, double bonusCount) {
        Customer customer = new CustomerImpl(CustomerType.Child, cash, cardCash, bonusCount);
        customer.setId(0);

        return customer;
    }

    private static Field setReporterFieldAccessible(Reporter reporter, String fieldName) {
        Field field = null;
        try {
            field = reporter.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Reporter does not have field \"" + fieldName + "\"");
        }
        return field;
    }

}