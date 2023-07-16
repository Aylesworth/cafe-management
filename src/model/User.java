/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
    private String address;
    private String securityQuestion;
    private String answer;
    private LocalDateTime createdAt;
    private boolean isApproved;
    private int point;
    private int rank;
    
}