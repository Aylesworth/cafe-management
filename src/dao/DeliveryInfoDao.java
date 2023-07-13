/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.DeliveryInfo;

/**
 *
 * @author Admin
 */
public class DeliveryInfoDao {
    public void add(DeliveryInfo info) {
        String query = "INSERT INTO DeliveryInfo (UserId, RecipientName, PhoneNumber, Address) VALUES (?, ?, ?, ?)";
        Object[] args = {info.getUser().getId(), info.getRecipientName(), info.getPhoneNumber(), info.getAddress()};
        DbOperations.updateData(query, "Added new delivery info");
    }
    
    public void remove(int id) {
        String query = "DELETE FROM DeliveryInfo WHERE Id = ?";
        Object[] args = {id};
        DbOperations.updateData(query, "Removed delivery info");
    }
}
