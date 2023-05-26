/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Category;

/**
 *
 * @author Hasagi
 */
public class CategoryDao {

	public static void save(Category category) {
		String query = "INSERT INTO category (name) VALUES(?)";
		Object[] args = { category.getName() };
		DbOperations.updateData(query, args, "Category added successfully!");
	}

	public static ArrayList<Category> getAllRecords() {
		ArrayList<Category> arrayList = new ArrayList<>();
		try {
			ResultSet rs = DbOperations.getData("SELECT * FROM category");
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				arrayList.add(category);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return arrayList;
	}

	public static void delete(String id) {
		String query = "DELETE FROM category WHERE id = ?";
		Object[] args = { id };
		DbOperations.updateData(query, args, "Category deleted successfully!");
	}
}
