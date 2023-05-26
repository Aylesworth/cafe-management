/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 *
 * @author Dungpc
 */
public class DbOperations {
	public static void updateData(String query, String message) {
		updateData(query, new Object[] {}, message);
	}

	public static void updateData(String query, Object[] args, String message) {
		try {
			Connection con = ConnectionProvider.getCon();
			PreparedStatement st = con.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				st.setObject(i + 1, args[i]);
			}

			st.executeUpdate();
			if (!message.isBlank()) {
				JOptionPane.showMessageDialog(null, message);
			}
		} catch (HeadlessException | SQLException e) {
			JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	public static ResultSet getData(String query) {
		return getData(query, new Object[] {});
	}

	public static ResultSet getData(String query, Object[] args) {
		try {
			Connection con = ConnectionProvider.getCon();
			PreparedStatement st = con.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				st.setObject(i + 1, args[i]);
			}

			ResultSet rs = st.executeQuery();
			return rs;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

}
