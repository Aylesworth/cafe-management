/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Product;

/**
 *
 * @author Hasagi
 */
public class ProductDao {

	public static void save(Product product) {
		String query = "INSERT INTO product (name, category, price) VALUES (?, ?, ?)";
		Object[] args = { product.getName(), product.getCategory(), product.getPrice() };

		DbOperations.updateData(query, args, "Product added successfully");
	}

	public static List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		try {
			ResultSet rs = DbOperations.getData("SELECT * FROM product");
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				products.add(product);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		return products;
	}

	public static void update(Product product) {
		String query = "UPDATE product SET name = ?, category = ?, price = ? WHERE id = ?";
		Object[] args = { product.getName(), product.getCategory(), product.getPrice(), product.getId() };

		DbOperations.updateData(query, args, "Product updated successfully!");
	}

	public static void delete(int id) {
		String query = "DELETE FROM product WHERE id = ?";
		Object[] args = { id };

		DbOperations.updateData(query, args, "Product deleted successfully!");
	}

	public static List<Product> filterByCategory(String category) {
		List<Product> products = new ArrayList<>();
		try {
			String query = "SELECT * FROM product WHERE category = ?";
			Object[] args = { category };

			ResultSet rs = DbOperations.getData(query, args);

			while (rs.next()) {
				Product product = new Product();
				product.setName(rs.getString("name"));
				products.add(product);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return products;
	}

	public static List<Product> filterByName(String name, String category) {
		List<Product> products = new ArrayList<>();
		try {
			String query = "SELECT * FROM product WHERE name LIKE ? AND category = ?";
			Object[] args = { "%" + name + "%", category };

			ResultSet rs = DbOperations.getData(query, args);

			while (rs.next()) {
				Product product = new Product();
				product.setName(rs.getString("name"));
				products.add(product);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return products;
	}

	public static Product getByName(String name) {
		Product product = new Product();
		try {
			String query = "SELECT * FROM product WHERE name = ?";
			Object[] args = { name };

			ResultSet rs = DbOperations.getData(query, args);

			while (rs.next()) {
				product.setName(rs.getString(2));
				product.setCategory(rs.getString(3));
				product.setPrice(rs.getDouble(4));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return product;
	}
}
