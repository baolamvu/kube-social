package com.kubesocial.modules.study;

public class CreditCardPayment implements PaymentMethod{

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " using Credit Card.");
    }
}
