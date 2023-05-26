/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.time.LocalDate;
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
		String query = "INSERT INTO bill VALUES (?, ?, ?, ?, ?, ?, ?)";
		Object[] args = { bill.getId(), bill.getName(), bill.getMobileNumber(), bill.getEmail(),
				bill.getDate().toString(), bill.getTotal(), bill.getCreatedBy() };

		DbOperations.updateData(query, args, "Bill details added successfully!");
	}

	public static ArrayList<Bill> getAllRecordsByInc(String date) {
		ArrayList<Bill> arrayList = new ArrayList<>();
		try {
			ResultSet rs = DbOperations.getData("SELECT * FROM bill WHERE date LIKE ?",
					new Object[] { "%" + date + "%" });
			while (rs.next()) {
				Bill bill = new Bill();
				bill.setId(rs.getInt("id"));
				bill.setName(rs.getString("name"));
				bill.setMobileNumber(rs.getString("mobileNumber"));
				bill.setEmail(rs.getString("email"));
				bill.setDate(LocalDate.parse(rs.getString("date")));
				bill.setTotal(Double.parseDouble(rs.getString("total")));
				bill.setCreatedBy(rs.getString("createdBy"));
				arrayList.add(bill);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return arrayList;
	}

	public static ArrayList<Bill> getAllRecordsByDesc(String date) {
		ArrayList<Bill> arrayList = new ArrayList<>();
		try {
			ResultSet rs = DbOperations.getData("SELECT * FROM bill WHERE date LIKE ? ORDER BY id DESC",
					new Object[] { "%" + date + "%" });
			while (rs.next()) {
				Bill bill = new Bill();
				bill.setId(rs.getInt("id"));
				bill.setName(rs.getString("name"));
				bill.setMobileNumber(rs.getString("mobileNumber"));
				bill.setEmail(rs.getString("email"));
				bill.setDate(LocalDate.parse(rs.getString("date")));
				bill.setTotal(Double.parseDouble(rs.getString("total")));
				bill.setCreatedBy(rs.getString("createdBy"));
				arrayList.add(bill);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return arrayList;
	}
}
