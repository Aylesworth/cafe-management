/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Staff;

/**
 *
 * @author Admin
 */
public class StaffDao {

    private static StaffDao staffDao;

    public static StaffDao getInstance() {
        if (staffDao == null) {
            staffDao = new StaffDao();
        }
        return staffDao;
    }

    private Staff map(ResultSet rs) {
        try {
            Staff staff = new Staff();
            staff.setId(rs.getInt("Id"));
            staff.setFullName(rs.getString("FullName"));
            staff.setSex(rs.getString("Sex"));
            staff.setBirthDate(rs.getDate("BirthDate").toLocalDate());
            staff.setPhoneNumber(rs.getString("PhoneNumber"));
            staff.setPosition(rs.getString("Position"));
            staff.setMonthlySalary(rs.getDouble("MonthlySalary"));
            staff.setStartDate(rs.getDate("StartDate").toLocalDate());
            staff.setEndDate(rs.getDate("EndDate") == null ? null : rs.getDate("EndDate").toLocalDate());
            return staff;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public List<Staff> getAllStaff() {
        ResultSet rs = DbOperations.getData("SELECT * FROM Staff ORDER BY FullName");
        List<Staff> staffList = new ArrayList<>();
        try {
            while (rs.next()) {
                staffList.add(map(rs));
            }
            return staffList;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public List<Staff> getCurrentStaff() {
        ResultSet rs = DbOperations.getData("SELECT * FROM Staff WHERE StartDate <= GETDATE() AND (EndDate IS NULL OR EndDate >= GETDATE()) ORDER BY FullName");
        List<Staff> staffList = new ArrayList<>();
        try {
            while (rs.next()) {
                staffList.add(map(rs));
            }
            return staffList;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
        public List<Staff> getShippers() {
        ResultSet rs = DbOperations.getData("SELECT * FROM Staff "
                + "WHERE Position = 'Shipper' "
                + "AND StartDate <= GETDATE() AND (EndDate IS NULL OR EndDate >= GETDATE()) "
                + "ORDER BY FullName");
        List<Staff> staffList = new ArrayList<>();
        try {
            while (rs.next()) {
                staffList.add(map(rs));
            }
            return staffList;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public Staff getById(int id) {
        ResultSet rs = DbOperations.getData("SELECT * FROM Staff WHERE Id = ?", new Object[]{id});
        try {
            if (rs.next()) {
                return map(rs);
            } else {
                return null;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void saveStaff(Staff staff) {
        String query = "INSERT INTO Staff (FullName, Sex, BirthDate, PhoneNumber, Position, StartDate, EndDate, MonthlySalary) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {
            staff.getFullName(),
            staff.getSex(),
            staff.getBirthDate().toString(),
            staff.getPhoneNumber(), staff.getPosition(),
            staff.getStartDate().toString(),
            staff.getEndDate() == null ? null : staff.getEndDate().toString(),
            staff.getMonthlySalary()
        };
        DbOperations.updateData(query, args, "New staff added successfully!");
    }

    public void updateStaff(Staff staff) {
        String query = "UPDATE Staff SET FullName = ?, Sex = ?, BirthDate = ?, PhoneNumber = ?, Position = ?, StartDate = ?, EndDate = ?, MonthlySalary = ? WHERE Id = ?";
        Object[] args = {
            staff.getFullName(),
            staff.getSex(),
            staff.getBirthDate().toString(),
            staff.getPhoneNumber(), staff.getPosition(),
            staff.getStartDate().toString(),
            staff.getEndDate() == null ? null : staff.getEndDate().toString(),
            staff.getMonthlySalary(),
            staff.getId()
        };
        DbOperations.updateData(query, args, "Staff updated successfully!");
    }

}
