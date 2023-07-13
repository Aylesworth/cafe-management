/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Cart;
import model.CartItem;
import model.User;

/**
 *
 * @author Admin
 */
public class CartDao {
    
    public Cart getCart(int userId) {
        Cart cart = new Cart();
        User user = new UserDao().getById(userId);
        cart.setUser(user);
        
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE UserId = ?";
        var rs = DbOperations.getData(query, new Object[] {userId});
        try {
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("Id"));
                item.setProduct(ProductDao.getInstance().getById(rs.getInt("ProductId")));
                item.setQuantity(rs.getInt("Quantity"));
                items.add(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        cart.setItems(items);
        return cart;
    }
    
    public void saveCart(Cart cart) {
        cart.getItems().stream()
                .filter(item -> item.getId()==0)
                .forEach(item -> {
                    String query = "INSERT INTO CartItem (UserId, ProductId, Quantity) VALUE (?, ?, ?)";
                    Object[] args = {cart.getUser().getId(), item.getProduct().getId(), item.getQuantity()};
                    DbOperations.updateData(query, args, "");
                });
    }
    
}
