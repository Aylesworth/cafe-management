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

    private static CartDao cartDao;

    public static CartDao getInstance() {
        if (cartDao == null) {
            cartDao = new CartDao();
        }
        return cartDao;
    }

    public Cart getCart(int userId) {
        Cart cart = new Cart();
        User user = new UserDao().getById(userId);
        cart.setUser(user);

        List<CartItem> items = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE UserId = ?";
        var rs = DbOperations.getData(query, new Object[]{userId});
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
        if (cart.getItems().isEmpty()) return;
        cart.getItems().stream()
                .forEach(item -> {
//                    if (item.getId() == 0) {
                        String query = "INSERT INTO CartItem (UserId, ProductId, Quantity) VALUES (?, ?, ?)";
                        Object[] args = {cart.getUser().getId(), item.getProduct().getId(), item.getQuantity()};
                        item.setId(DbOperations.updateData(query, args, ""));
//                    } else {
//                        String query = "UPDATE CartItem SET Quantity = ? WHERE Id = ?";
//                        Object[] args = {item.getQuantity(), item.getId()};
//                        DbOperations.updateData(query, args, "");
//                    }
                });
    }

    public void clearCart(int userId) {
        DbOperations.updateData("DELETE FROM CartItem WHERE UserId = ?", new Object[]{userId}, "");
    }

}
