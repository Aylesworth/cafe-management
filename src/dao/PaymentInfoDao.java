/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.PaymentInfo;

/**
 *
 * @author Admin
 */
public class PaymentInfoDao {
        public void add(PaymentInfo info) {
        String query = "INSERT INTO PaymentInfo (UserId, CardNumber, ExpMonth, ExpYear, SecurityCode, OwnerName, BillingAddress1, BillingAddress2, City, ZipCode, Country) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {info.getUserId(), info.getCardNumber(), info.getExpMonth(), info.getExpYear(), info.getSecurityCode(), info.getOwnerName(), info.getBillingAddress1(), info.getBillingAddress2(), info.getCity(), info.getZipCode(), info.getCountry()};
        DbOperations.updateData(query, "Added new payment info");
    }
    
    public void remove(int id) {
        String query = "DELETE FROM PaymentInfo WHERE Id = ?";
        Object[] args = {id};
        DbOperations.updateData(query, "Removed payment info");
    }
}
