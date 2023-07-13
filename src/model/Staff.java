/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Staff {
    private int id;
    private String fullName;
    private String sex;
    private LocalDate birthDate;
    private String phoneNumber;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlySalary;
}
