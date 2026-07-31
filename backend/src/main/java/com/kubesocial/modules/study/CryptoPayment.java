package com.kubesocial.modules.study;

public class CryptoPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " using Crypto.");
    }

}
