/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author Dungpc
 */
public class ConnectionProvider {
	public static Connection getCon() {
		String url = "jdbc:sqlserver://aylesw-server.database.windows.net:1433;databaseName=database-1";
		String user = "aylesw";
		String password = "Ducanh2003@";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
			return null;
		}

	}
}
