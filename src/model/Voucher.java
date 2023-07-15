/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Voucher {

    private int id;
    private Rank minRank;
    private double minCost;
    private double discountPercentage;
    private double maxDiscountAmount;
    private LocalDate expDate;

    public String getName() {
        String name = "";
        if (discountPercentage != -1 && maxDiscountAmount != -1) {
            name = "Discount %.0f%% max $%.2f".formatted(discountPercentage, maxDiscountAmount);
        } else if (discountPercentage != -1) {
            name = "Discount %.0f%%".formatted(discountPercentage);
        } else if (maxDiscountAmount != -1) {
            name = "Discount $%.2f".formatted(maxDiscountAmount);
        }

        if (minCost > 0) {
            name += " for orders with min value of $%.2f"
                    .formatted(minCost, expDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")));
        } else {
            name += " for all orders";
        }

        return name;
    }
}
