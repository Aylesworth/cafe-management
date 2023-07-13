/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Category;

/**
 *
 * @author Hasagi
 */
public class CategoryDao {

    private static CategoryDao categoryDao;

    public static CategoryDao getInstance() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public Category getById(int id) {
        try {
            String query = "SELECT * FROM Category WHERE Id = ?;";
            ResultSet rs = DbOperations.getData(query, new Object[]{id});

            Category category = null;
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
            }
            return category;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public Category getByName(String name) {
        try {
            String query = "SELECT * FROM Category WHERE Name = ?;";
            ResultSet rs = DbOperations.getData(query, new Object[]{name});

            Category category = null;
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
            }
            return category;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void save(Category category) {
        String query = "INSERT INTO Category (Name) VALUES(?)";
        Object[] args = {category.getName()};
        DbOperations.updateData(query, args, "Category added successfully!");
    }

    public List<Category> getAllRecords() {
        List<Category> categories = new ArrayList<>();
        try {
            ResultSet rs = DbOperations.getData("SELECT * FROM Category");
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                categories.add(category);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return categories;
    }

    public void delete(String id) {
        String query = "DELETE FROM Category WHERE Id = ?";
        Object[] args = {id};
        DbOperations.updateData(query, args, "Category deleted successfully!");
    }
}
