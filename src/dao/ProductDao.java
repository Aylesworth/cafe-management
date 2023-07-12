/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
            int categoryId = 0;
            ResultSet rs = DbOperations.getData("SELECT Id FROM Category WHERE Name LIKE ?", new Object[]{product.getCategory()});

            while (rs.next()) {
                categoryId = rs.getInt("Id");
            }

            String query = "INSERT INTO Product (Name, CategoryId, Price, Description) VALUES (?, ?, ?, ?)";
            Object[] args = {product.getName(), categoryId, product.getPrice(), product.getDescription()};

            DbOperations.updateData(query, args, "Product added successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try {
            ResultSet rs = DbOperations.getData(
                    "SELECT p.Id AS Id, p.Name AS ProductName, c.Name AS CategoryName, Price, Description"
                    + " FROM Product p JOIN Category c ON p.CategoryId = c.Id"
            );
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setName(rs.getString("ProductName"));
                product.setCategory(rs.getString("CategoryName"));
                product.setPrice(rs.getDouble("Price"));
                product.setDescription(rs.getString("Description"));
                products.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
        return products;
    }

    public static void update(Product product) {
        try {
            int categoryId = 0;
            ResultSet rs = DbOperations.getData("SELECT Id FROM Category WHERE Name LIKE ?", new Object[]{product.getCategory()});

            while (rs.next()) {
                categoryId = rs.getInt("Id");
            }

            String query = "UPDATE Product SET Name = ?, CategoryId = ?, Price = ?, Description = ? WHERE Id = ?";
            Object[] args = {product.getName(), categoryId, product.getPrice(), product.getDescription(), product.getId()};

            DbOperations.updateData(query, args, "Product updated successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM Product WHERE Id = ?";
        Object[] args = {id};

        DbOperations.updateData(query, args, "Product deleted successfully!");
    }

    public static List<Product> filterByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT p.Name AS Name "
                    + "FROM Product p JOIN Category c ON p.CategoryId = c.Id WHERE c.Name = ?";
            Object[] args = {category};

            ResultSet rs = DbOperations.getData(query, args);

            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("Name"));
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
            String query = "SELECT p.Name AS Name "
                    + "FROM Product p JOIN Category c ON p.CategoryId = c.Id "
                    + "WHERE p.Name LIKE ? AND c.Name = ?";
            Object[] args = {"%" + name + "%", category};

            ResultSet rs = DbOperations.getData(query, args);

            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("p.Name"));
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
            String query = "SELECT p.Name AS Name, c.Name AS CategoryName, p.Price "
                    + "FROM Product p JOIN Category c ON p.CategoryId = c.Id "
                    + "WHERE p.Name = ?";
            Object[] args = {name};

            ResultSet rs = DbOperations.getData(query, args);

            while (rs.next()) {
                product.setName(rs.getString("Name"));
                product.setCategory(rs.getString("CategoryName"));
                product.setPrice(rs.getDouble("Price"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return product;
    }
}
