/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.PaymentInfo;

/**
 *
 * @author Admin
 */
public class PaymentInfoDao {

    private static PaymentInfoDao paymentInfoDao;

    public static PaymentInfoDao getInstance() {
        if (paymentInfoDao == null) {
            paymentInfoDao = new PaymentInfoDao();
        }
        return paymentInfoDao;
    }

    public List<PaymentInfo> getByUser(int userId) {
        List<PaymentInfo> paymentInfos = new ArrayList<>();
        String query = "SELECT * FROM PaymentInfo WHERE UserId = ?";
        ResultSet rs = DbOperations.getData(query, new Object[]{userId});
        try {
            while (rs.next()) {
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setId(rs.getInt("Id"));
                paymentInfo.setUserId(userId);
                paymentInfo.setCardNumber(rs.getString("CardNumber"));
                paymentInfo.setExpMonth(rs.getInt("ExpMonth"));
                paymentInfo.setExpYear(rs.getInt("ExpYear"));
                paymentInfo.setSecurityCode(rs.getString("SecurityCode"));
                paymentInfo.setOwnerName(rs.getString("OwnerName"));
                paymentInfo.setBillingAddress1(rs.getString("BillingAddress1"));
                paymentInfo.setBillingAddress2(rs.getString("BillingAddress2"));
                paymentInfo.setCity(rs.getString("City"));
                paymentInfo.setZipCode(rs.getString("ZipCode"));
                paymentInfo.setCountry(rs.getString("Country"));
                paymentInfos.add(paymentInfo);
            }
            return paymentInfos;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void add(PaymentInfo info) {
        String query = "INSERT INTO PaymentInfo (UserId, CardNumber, ExpMonth, ExpYear, SecurityCode, OwnerName, BillingAddress1, BillingAddress2, City, ZipCode, Country) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {info.getUserId(), info.getCardNumber(), info.getExpMonth(), info.getExpYear(), info.getSecurityCode(), info.getOwnerName(), info.getBillingAddress1(), info.getBillingAddress2(), info.getCity(), info.getZipCode(), info.getCountry()};
        DbOperations.updateData(query, args, "Added new payment info");
    }

    public void remove(int id) {
        String query = "DELETE FROM PaymentInfo WHERE Id = ?";
        Object[] args = {id};
        DbOperations.updateData(query, args, "Removed payment info");
    }
}
