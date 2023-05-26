package dao;

import javax.swing.JOptionPane;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void save(User user) {
        String query = "INSERT INTO [user] (name, email, mobileNumber, address, password, securityQuestion, answer, status) VALUES ('"
                + user.getName() + "', '"
                + user.getEmail() + "', '"
                + user.getMobileNumber() + "', '"
                + user.getAddress() + "', '"
                + user.getPassWord() + "', '"
                + user.getSecurityQuestion() + "', '"
                + user.getAnswer() + "', '"
                + "false" + "')";
        DbOperations.setDataOrDelete(query, "Registed Succcessfully,Wait for admin approval ");
    }

    public User login(String email, String Password) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("SELECT * FROM [user] WHERE  email = '" + email + "' AND password = '" + Password + "'");
            while (rs.next()) {
                user = new User();
                user.setStatus(rs.getString("Status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }

    public User getSecurityQuestion(String email) {
        User user = null;
        try {
            ResultSet rs = DbOperations.getData("SELECT * FROM [user] WHERE  email = '" + email + "'");
            while (rs.next()) {
                user = new User();
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setAnswer(rs.getString("answer"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;

    }

    public static void updatePassWord(String Email, String newPass) {
        String query = "UPDATE [user] SET password = '" + newPass + "' WHERE email = '" + Email + "'";
        DbOperations.setDataOrDelete(query, "Changed Password Succcessfully");

    }

    public static List<User> getAll(String email) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM [user] WHERE email LIKE '%" + email + "%'";

        try {
            ResultSet rs = DbOperations.getData(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setAddress(rs.getString("address"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return users;
    }

    public static void changeStatus(String email, String status) {
        String query = String.format("UPDATE [user] SET status = '%s' WHERE email = '%s'", status, email);
        DbOperations.setDataOrDelete(query, "Status changed successfully");
    }
    
    public static void changePassword(String email, String oldPassword, String newPassword) {
        try{
            ResultSet rs = DbOperations.getData("select * from user where email = '" + email + "' and password = '" + oldPassword + "'");
            if(rs.next()){
                updatePassWord(email, newPassword);
            }
            else {
                JOptionPane.showMessageDialog(null, "Old Password is wrong");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void changeSecurityQuestion(String email, String password, String securityQuestion, String answer){
        try{
            ResultSet rs = DbOperations.getData("select * from user where email = '" + email + "' and password = '" + password + "'");
            if(rs.next()){
                update(email, securityQuestion, answer);
            }
            else {
                JOptionPane.showMessageDialog(null, "Password is wrong");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void update(String email, String securityQuestion, String answer){
        String query = "update user set securityQuestion = '" + securityQuestion + "' answer = '" + answer + "' where email = '" + email + "'";
        DbOperations.setDataOrDelete(query, "Security Question Changed Successfully");
    }
}
