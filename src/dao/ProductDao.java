/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.Category;
import model.Product;

/**
 *
 * @author Hasagi
 */
public class ProductDao {

    private static ProductDao productDao;

    public static ProductDao getInstance() {
        if (productDao == null) {
            productDao = new ProductDao();
        }
        return productDao;
    }

    public Product getById(int id) {
        try {
            String query = "SELECT * FROM Product WHERE Id = ?;";
            ResultSet rs = DbOperations.getData(query, new Object[]{id});

            Product product = null;
            if (rs.next()) {
                product = new Product();
                product.setCategory(new CategoryDao().getById(rs.getInt("CategoryId")));
                product.setId(rs.getInt("Id"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
            }
            return product;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void save(Product product) {
        String query = "INSERT INTO Product (Name, CategoryId, Price) VALUES (?, ?, ?)";
        Object[] args = {product.getName(), product.getCategory().getId(), product.getPrice()};

        DbOperations.updateData(query, args, "Product added successfully");
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        Map<Integer, Category> categories = new HashMap<>();
        CategoryDao.getInstance().getAllRecords().forEach(category -> {
            categories.put(category.getId(), category);
        });
        try {
            ResultSet rs = DbOperations.getData(
                    "SELECT * FROM Product"
            );
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setName(rs.getString("Name"));
                product.setCategory(categories.get(rs.getInt("CategoryId")));
                product.setPrice(rs.getDouble("Price"));
                products.add(product);
                System.out.println(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
        return products;
    }

    public void update(Product product) {
        try {
            int categoryId = 0;
            ResultSet rs = DbOperations.getData("SELECT Id FROM Category WHERE Name LIKE ?", new Object[]{product.getCategory()});

            while (rs.next()) {
                categoryId = rs.getInt("Id");
            }

            String query = "UPDATE Product SET Name = ?, CategoryId = ?, Price = ? WHERE Id = ?";
            Object[] args = {product.getName(), categoryId, product.getPrice(), product.getId()};

            DbOperations.updateData(query, args, "Product updated successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM Product WHERE Id = ?";
        Object[] args = {id};

        DbOperations.updateData(query, args, "Product deleted successfully!");
    }

    public List<Product> filterByCategory(String category) {
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

    public List<Product> filterByName(String name, String category) {
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

    public Product getByName(String name) {
        Product product = new Product();
        try {
            String query = "SELECT p.Name AS Name, c.Name AS CategoryName, p.Price "
                    + "FROM Product p JOIN Category c ON p.CategoryId = c.Id "
                    + "WHERE p.Name = ?";
            Object[] args = {name};

            ResultSet rs = DbOperations.getData(query, args);

            while (rs.next()) {
                product.setName(rs.getString("Name"));
                product.setCategory(CategoryDao.getInstance().getById(rs.getInt("CategoryId")));
                product.setPrice(rs.getDouble("Price"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return product;
    }
}
