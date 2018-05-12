package ru.mifsan.javacore2017.cash_desk;

import ru.mifsan.javacore2017.customer.Customer;
import ru.mifsan.javacore2017.reporter.Reporter;

public interface CashDesk {
    int getQueueSize();
    void addCustomerToQueue(Customer customer);
    void processQueue(int customerCount);
    void setReporter(Reporter reporter);
}
