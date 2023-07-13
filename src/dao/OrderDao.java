/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.Order;

/**
 *
 * @author Admin   
 */
public class OrderDao {
    public void placeOrder(Order order) {
        int paymentMethodId = 0;
        try {
            String getPaymentMethodId = "SELECT Id FROM PaymentMethod WHERE Name = ?";
            ResultSet rs = DbOperations.getData(getPaymentMethodId, new Object[] {order.getPaymentMethod()});
            while (rs.next()) {
                paymentMethodId = rs.getInt("Id");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        }
        
        String insertOrder = "INSERT INTO Order (UserId, CreatedAt, TotalCost, ShipCost, Discount, FinalCost, DeliveryInfoId, PaymentMethod, PaymentInfoId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {order.getUser().getId(), order.getTotalCost(), order.getShipCost(), order.getDiscount(), order.getFinalCost(), order.getDeliveryInfo().getId(), paymentMethodId, order.getPaymentInfo().getId()};
        
        int orderId = DbOperations.updateData(insertOrder, "");
        
        String insertItem = "INSERT INTO OrderDetails (OrderId, ProductId, Quantity, UnitPrice, TotalAmount) VALUES (?, ?, ?, ?, ?)";
        order.getItems().forEach(orderDetails -> {
            Object[] args2 = {orderId, orderDetails.getProduct().getId(), orderDetails.getQuantity(), orderDetails.getUnitPrice(), orderDetails.getTotalAmount()};
            DbOperations.updateData(insertItem, "");
        });
        
        JOptionPane.showMessageDialog(null, "Your order was placed successfully! Thanks for purchasing!");
    }
    
    
}
