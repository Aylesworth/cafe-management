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
public class CartItem {

    private int id;
    private Product product;
    private int quantity;
    
}
