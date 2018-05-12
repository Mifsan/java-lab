package ru.mifsan.javacore2017.customer;

import ru.mifsan.javacore2017.bucket.Bucket;
import ru.mifsan.javacore2017.payment.Bill;
import ru.mifsan.javacore2017.payment.PaymentMethod;

public interface Customer {
    int getId();
    void setId(int value);
    String getName();
    CustomerType getType();
    Bucket getBucket();
    boolean isInQueue();
    void setInQueue(boolean value);
    boolean pay(Bill bill);
    double getCash();
    double getCardCash();
    double getBonusCount();
    PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount);
}
