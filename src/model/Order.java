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
    private int id;
    private User user;
    private LocalDateTime createdAt;
    private double totalCost;
    private double shipCost;
    private double discount;
    private double finalCost;
    private DeliveryInfo deliveryInfo;
    private String paymentMethod;
    private PaymentInfo paymentInfo;
    private Staff shipper;
    private LocalDateTime deliveredAt;
    private List<OrderDetails> items;
}
