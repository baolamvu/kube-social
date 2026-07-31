package com.kubesocial.modules.study;

public class PaypalPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " using Paypal.");
    }

}
