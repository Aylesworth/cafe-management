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

    public static int updateData(String query, String message) {
        return updateData(query, new Object[]{}, message);
    }

    public static int updateData(String query, Object[] args, String message) {
        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < args.length; i++) {
                st.setObject(i + 1, args[i]);
            }

            int id = -1;
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }

            if (!message.isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            }
            return id;
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return -1;
        }

    }

    public static ResultSet getData(String query) {
        return getData(query, new Object[]{});
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
