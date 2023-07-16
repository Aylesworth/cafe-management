/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.DeliveryInfo;

/**
 *
 * @author Admin
 */
public class DeliveryInfoDao {

    private static DeliveryInfoDao deliveryInfoDao;

    public static DeliveryInfoDao getInstance() {
        if (deliveryInfoDao == null) {
            deliveryInfoDao = new DeliveryInfoDao();
        }
        return deliveryInfoDao;
    }

    public List<DeliveryInfo> getByUser(int userId) {
        List<DeliveryInfo> deliveryInfos = new ArrayList<>();
        String query = "SELECT * FROM DeliveryInfo WHERE UserId = ?";
        ResultSet rs = DbOperations.getData(query, new Object[]{userId});
        try {
            while (rs.next()) {
                DeliveryInfo deliveryInfo = new DeliveryInfo();
                deliveryInfo.setId(rs.getInt("Id"));
                deliveryInfo.setRecipientName(rs.getString("RecipientName"));
                deliveryInfo.setPhoneNumber(rs.getString("PhoneNumber"));
                deliveryInfo.setAddress(rs.getString("Address"));
                deliveryInfo.setUserId(userId);
                deliveryInfos.add(deliveryInfo);
            }
            return deliveryInfos;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public DeliveryInfo getById(int id) {
        String query = "SELECT * FROM DeliveryInfo WHERE Id = ?";
        ResultSet rs = DbOperations.getData(query, new Object[]{id});
        try {
            DeliveryInfo deliveryInfo = null;
            if (rs.next()) {
                deliveryInfo = new DeliveryInfo();
                deliveryInfo.setId(rs.getInt("Id"));
                deliveryInfo.setRecipientName(rs.getString("RecipientName"));
                deliveryInfo.setPhoneNumber(rs.getString("PhoneNumber"));
                deliveryInfo.setAddress(rs.getString("Address"));
                deliveryInfo.setUserId(rs.getInt("UserId"));
            }
            return deliveryInfo;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void add(DeliveryInfo info) {
        String query = "INSERT INTO DeliveryInfo (UserId, RecipientName, PhoneNumber, Address) VALUES (?, ?, ?, ?)";
        Object[] args = {info.getUserId(), info.getRecipientName(), info.getPhoneNumber(), info.getAddress()};
        DbOperations.updateData(query, args, "Added new delivery info");
    }

    public void remove(int id) {
        String query = "DELETE FROM DeliveryInfo WHERE Id = ?";
        Object[] args = {id};
        DbOperations.updateData(query, args, "Removed delivery info");
    }
}
