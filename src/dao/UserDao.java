package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.User;

public class UserDao {

    public void create(User user) {
        String query = "INSERT INTO [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, SecurityQuestion, Answer, CreatedAt, Point, Rank) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {
            user.getEmail(),
            user.getPassword(),
            user.getFullName(),
            user.getSex(),
            user.getBirthDate().toString(),
            user.getPhoneNumber(),
            user.getSecurityQuestion(),
            user.getAnswer(),
            Timestamp.valueOf(LocalDateTime.now()),
            0,
            "Regular"
        };

        DbOperations.updateData(query, args, "Registered succcessfully! Please wait for admin approval.");
    }

    public User login(String email, String password) {
        User user = null;
        try {
            String query = "SELECT * FROM [User] WHERE Email = ? AND Password = ?";
            Object[] args = {email, password};

            ResultSet rs = DbOperations.getData(query, args);
            while (rs.next()) {
                user = new User();
                user.setApproved(rs.getBoolean("IsApproved"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }

    public User getSecurityQuestion(String email) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("SELECT * FROM [User] WHERE Email = ?", new Object[]{email});
            while (rs.next()) {
                user = new User();
                user.setSecurityQuestion(rs.getString("SecurityQuestion"));
                user.setAnswer(rs.getString("Answer"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;

    }

    public void updatePassword(String email, String newPassword) {
        String query = "UPDATE [User] SET Password = ? WHERE Email = ?";
        Object[] args = {newPassword, email};

        DbOperations.updateData(query, args, "Password changed succcessfully!");
    }

    public List<User> getAll(String email) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM [User] WHERE Email LIKE ?";
        Object[] args = {"%" + email + "%"};

        try {
            ResultSet rs = DbOperations.getData(query, args);
            while (rs.next()) {
                User user = User.map(rs);
                users.add(user);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return users;
    }

    public void changeApproved(String email, boolean approved) {
        String query = "UPDATE [User] SET IsApproved = ? WHERE Email = ?";
        Object[] args = {approved, email};
        DbOperations.updateData(query, "Status changed successfully!");
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            String query = "SELECT * FROM [User] WHERE Email = ? AND Password = ?";
            Object[] args = {email, oldPassword};

            ResultSet rs = DbOperations.getData(query, args);

            if (rs.next()) {
                updatePassword(email, newPassword);
            } else {
                JOptionPane.showMessageDialog(null, "Old password is wrong!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void changeSecurityQuestion(String email, String password, String securityQuestion, String answer) {
        try {
            String query = "SELECT * FROM [User] WHERE Email = ? AND Password = ?";
            Object[] args = {email, password};

            ResultSet rs = DbOperations.getData(query, args);

            if (rs.next()) {
                query = "UPDATE [User] SET SecurityQuestion = ?, Answer = ? WHERE Email = ?";
                args = new Object[] {securityQuestion, answer, email};

                DbOperations.updateData(query, "Security question changed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Password is wrong!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
