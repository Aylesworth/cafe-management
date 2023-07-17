package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Rank;
import model.User;

public class UserDao {

    private static UserDao userDao;

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    private User map(ResultSet rs) {
        User user = null;
        try {
            user = new User();
            user.setId(rs.getInt("Id"));
            user.setEmail(rs.getString("Email"));
            user.setFullName(rs.getString("FullName"));
            user.setSex(rs.getString("Sex"));
            user.setBirthDate(rs.getDate("BirthDate").toLocalDate());
            user.setPhoneNumber(rs.getString("PhoneNumber"));
            user.setAddress(rs.getString("Address"));
            user.setSecurityQuestion(rs.getString("SecurityQuestion"));
            user.setAnswer(rs.getString("Answer"));
            user.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
            user.setApproved(rs.getBoolean("IsApproved"));
            user.setPoint(rs.getInt("Point"));
            user.setRank(rs.getInt("Rank"));
            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    public User getById(int userId) {
        try {
            String query = "SELECT * FROM [User] WHERE Id = ?;";
            ResultSet rs = DbOperations.getData(query, new Object[]{userId});

            User user = null;
            if (rs.next()) {
                user = map(rs);
            }
            return user;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public User getByEmail(String userEmail) {
        try {
            String query = "SELECT * FROM [User] WHERE Email = ?;";
            ResultSet rs = DbOperations.getData(query, new Object[]{userEmail});

            User user = null;
            if (rs.next()) {
                user = map(rs);
            }
            return user;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void create(User user) {
        String query = "INSERT INTO [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {
            user.getEmail(),
            user.getPassword(),
            user.getFullName(),
            user.getSex(),
            user.getBirthDate().toString(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getSecurityQuestion(),
            user.getAnswer()
        };

        DbOperations.updateData(query, args, "Registered succcessfully! Please wait for admin approval.");
    }

    public void update(User user) {
        String query = "UPDATE [User] SET FullName = ?, Sex = ?, BirthDate = ?, Email = ?, PhoneNumber = ?, Address = ? WHERE Id = ?";
        Object[] args = {
            user.getFullName(),
            user.getSex(),
            user.getBirthDate().toString(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getId()
        };

        DbOperations.updateData(query, args, "User profile updated successfully!");
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

    public List<User> findByEmail(String email) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM [User] WHERE Email LIKE ?";
        Object[] args = {"%" + email + "%"};

        try {
            ResultSet rs = DbOperations.getData(query, args);
            while (rs.next()) {
                User user = map(rs);
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
        DbOperations.updateData(query, args, "Status changed successfully!");
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
                args = new Object[]{securityQuestion, answer, email};

                DbOperations.updateData(query, args, "Security question changed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Password is wrong!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public List<Rank> getAllRanks() {
        String query = "SELECT * FROM Rank";
        ResultSet rs = DbOperations.getData(query);
        List<Rank> ranks = new ArrayList<>();
        try {
            while (rs.next()) {
                Rank rank = new Rank();
                rank.setId(rs.getInt("Id"));
                rank.setName(rs.getString("Name"));
                rank.setMinPoint(rs.getInt("MinPoint"));
                ranks.add(rank);
            }
            return ranks;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public Rank getRankById(int id) {
        String query = "SELECT * FROM Rank WHERE Id = ?";
        ResultSet rs = DbOperations.getData(query, new Object[]{id});
        try {
            Rank rank = null;
            if (rs.next()) {
                rank = new Rank();
                rank.setId(rs.getInt("Id"));
                rank.setName(rs.getString("Name"));
                rank.setMinPoint(rs.getInt("MinPoint"));
            }
            return rank;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
}
