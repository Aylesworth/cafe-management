package dao;

import javax.swing.JOptionPane;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

	public void save(User user) {
		String query = "INSERT INTO [user] (name, email, mobileNumber, address, password, securityQuestion, answer, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] args = { user.getName(), user.getEmail(), user.getMobileNumber(), user.getAddress(),
				user.getPassword(), user.getSecurityQuestion(), user.getAnswer(), "false" };

		DbOperations.updateData(query, args, "Registered succcessfully! Please wait for admin approval.");
	}

	public User login(String email, String password) {
		User user = null;
		try {
			String query = "SELECT * FROM [user] WHERE email = ? AND password = ?";
			Object[] args = { email, password };

			ResultSet rs = DbOperations.getData(query, args);
			while (rs.next()) {
				user = new User();
				user.setStatus(rs.getString("Status"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return user;
	}

	public static User getSecurityQuestion(String email) {
		User user = null;
		try {
			ResultSet rs = DbOperations.getData("SELECT * FROM [user] WHERE  email = ?", new Object[] { email });
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

	public static void updatePassword(String email, String newPassword) {
		String query = "UPDATE [user] SET password = ? WHERE email = ?";
		Object[] args = { newPassword, email };

		DbOperations.updateData(query, args, "Password changed succcessfully!");
	}

	public static List<User> getAll(String email) {
		List<User> users = new ArrayList<>();

		String query = "SELECT * FROM [user] WHERE email LIKE ?";
		Object[] args = { "%" + email + "%" };

		try {
			ResultSet rs = DbOperations.getData(query, args);
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
		String query = "UPDATE [user] SET status = ? WHERE email = ?";
		Object[] args = { status, email };
		DbOperations.updateData(query, "Status changed successfully!");
	}

	public static void changePassword(String email, String oldPassword, String newPassword) {
		try {
			String query = "SELECT * FROM [user] WHERE email = ? AND password = ?";
			Object[] args = { email, oldPassword };

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

	public static void changeSecurityQuestion(String email, String password, String securityQuestion, String answer) {
		try {
			String query = "SELECT * FROM [user] WHERE email = ? AND password = ?";
			Object[] args = { email, password };

			ResultSet rs = DbOperations.getData(query, args);

			if (rs.next()) {
				update(email, securityQuestion, answer);
			} else {
				JOptionPane.showMessageDialog(null, "Password is wrong!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void update(String email, String securityQuestion, String answer) {
		String query = "UPDATE [user] SET securityQuestion = ?, answer = ? WHERE email = ?";
		Object[] args = { securityQuestion, answer, email };

		DbOperations.updateData(query, "Security question changed successfully!");
	}
}
