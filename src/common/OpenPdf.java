/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class OpenPdf {

    public static void openById(int id) {
        try {
            if (new File("D:\\bill_no_" + id + ".pdf").exists()) {
                Process p = Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler D:\\bill_no_" + id + ".pdf");
            } else {
                JOptionPane.showMessageDialog(null, "File does not exist!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
