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

    public static void openByPath(String path) {
        try {
            if (new File(path).exists()) {
                Process p = Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler \"%s\"".formatted(path));
            } else {
                JOptionPane.showMessageDialog(null, "File does not exist!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
