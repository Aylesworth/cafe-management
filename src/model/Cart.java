/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Cart {
    private User user;
    private List<CartItem> items;
}
