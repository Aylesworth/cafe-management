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
public class DeliveryInfo {
    private int id;
    private User user;
    private String recipientName;
    private String phoneNumber;
    private String address;
}
