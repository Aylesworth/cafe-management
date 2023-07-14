/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Order {
    public Order() {
        createdAt = LocalDateTime.now();
    }
    
    public Order(Cart cart) {
        this();
        items = cart.getItems().stream()
                .map(item -> {
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setProduct(item.getProduct());
                    orderDetails.setQuantity(item.getQuantity());
                    orderDetails.setUnitPrice(item.getProduct().getPrice());
                    return orderDetails;
                }).toList();
    }
    
    private int id;
    private User user;
    private LocalDateTime createdAt;
    private double totalCost;
    private double shipCost;
    private double discount;
    private double finalCost;
    private DeliveryInfo deliveryInfo;
    private int paymentMethodId;
    private PaymentInfo paymentInfo;
    private Staff shipper;
    private Status status;
    private List<OrderDetails> items;
}
