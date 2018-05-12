package ru.mifsan.javacore2017;

import ru.mifsan.javacore2017.customer.CustomerType;
import ru.mifsan.javacore2017.supermarket.Supermarket;
import ru.mifsan.javacore2017.supermarket.SupermarketWorkImpl;

public class SupermarketSimulator
{
    private static final int WORKING_TIME_MINUTES = 30;
    private static final int RETIRED_DISCOUNT_PERCENT = 15;

    public static void main(String[] args) {
        Supermarket supermarket = new Supermarket(WORKING_TIME_MINUTES);
        supermarket.addDiscount(CustomerType.Retired, RETIRED_DISCOUNT_PERCENT);
        supermarket.addCashDesks(2);
        supermarket.work(new SupermarketWorkImpl());
    }
}
