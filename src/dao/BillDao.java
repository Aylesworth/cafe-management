/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Bill;

/**
 *
 * @author Admin
 */
public class BillDao {
    public static int getNextId() {
        int id = 1;
        try {
            ResultSet rs = DbOperations.getData("SELECT MAX(id) FROM bill");
            if (rs.next()) {
                id = rs.getInt(1);
                id++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    
    public static void save(Bill bill) {
        String query = String.format(
                "INSERT INTO bill VALUES(%d, '%s', '%s', '%s', '%s', %.2f, '%s')", 
                bill.getId(),
                bill.getName(),
                bill.getMobileNumber(),
                bill.getEmail(),
                bill.getDate().toString(),
                bill.getTotal(),
                bill.getCreatedBy());
        
        DbOperations.setDataOrDelete(query, "Bill details added successfully!");
    }
    
    public static ArrayList<Bill> getAllRecordsByInc(String date) {
        ArrayList<Bill> arrayList = new ArrayList<>();
        try{
            ResultSet rs = DbOperations.getData("select * from bill where date like '%" + date + "%'");
            while (rs.next()){
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setName(rs.getString("name"));
                bill.setMobileNumber(rs.getString("mobileNumber"));
                bill.setEmail(rs.getString("email"));
                bill.setDate(rs.getString("date"));
                bill.setTotal(rs.getString("total"));
                bill.setCreatedBy(rs.getString("createdBy"));
                arrayList.add(bill);
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return arrayList;
    }
    
        public static ArrayList<Bill> getAllRecordsByDesc(String date) {
        ArrayList<Bill> arrayList = new ArrayList<>();
        try{
            ResultSet rs = DbOperations.getData("select * from bill where date like '%" + date + "%' order by id desc");
            while (rs.next()){
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setName(rs.getString("name"));
                bill.setMobileNumber(rs.getString("mobileNumber"));
                bill.setEmail(rs.getString("email"));
                bill.setDate(rs.getString("date"));
                bill.setTotal(rs.getString("total"));
                bill.setCreatedBy(rs.getString("createdBy"));
                arrayList.add(bill);
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return arrayList;
    }
}
