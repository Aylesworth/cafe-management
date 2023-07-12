/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author Dungpc
 */
@Data
public class User {

    private int id;
    private String email;
    private String password;
    private String fullName;
    private String sex;
    private LocalDate birthDate;
    private String phoneNumber;
    private String securityQuestion;
    private String answer;
    private LocalDateTime createdAt;
    private boolean isApproved;
    private int point;
    private String rank;

    public static User map(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("Id"));
            user.setEmail(rs.getString("Email"));
            user.setFullName(rs.getString("FullName"));
            user.setSex(rs.getString("Sex"));
            user.setBirthDate(rs.getDate("BirthDate").toLocalDate());
            user.setPhoneNumber(rs.getString("PhoneNumber"));
            user.setSecurityQuestion(rs.getString("SecurityQuestion"));
            user.setAnswer(rs.getString("Answer"));
            user.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
            user.setApproved(rs.getBoolean("IsApproved"));
            user.setPoint(rs.getInt("Point"));
            user.setRank(rs.getString("Rank"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return user;
    }
}
