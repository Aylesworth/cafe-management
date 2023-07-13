/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class PaymentInfo {
    private int id;
    private int userId;
    private String cardNumber;
    private int expMonth;
    private int expYear;
    private String securityCode;
    private String ownerName;
    private String billingAddress1;
    private String billingAddress2;
    private String city;
    private String zipCode;
    private String country;
}
