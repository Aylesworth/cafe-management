/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

/**
 *
 * @author Hasagi
 */
@Data
public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private String description;
}
