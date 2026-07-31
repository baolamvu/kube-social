package com.kubesocial.modules.study;

public class Order {

    private Integer orderId;

    private String customerName;

    private PaymentMethod paymentMethod;

    public Order(Integer orderId, String customerName, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.paymentMethod = paymentMethod;
    }

    public void processOrder(double amount) {
        System.out.println("Processing order #" + orderId + " for customer: " + customerName);
        paymentMethod.pay(amount);
    }

}
