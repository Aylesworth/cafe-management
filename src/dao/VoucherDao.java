/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import common.Utils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Voucher;

/**
 *
 * @author Admin
 */
public class VoucherDao {

    private static VoucherDao voucherDao;

    public static VoucherDao getInstance() {
        if (voucherDao == null) {
            voucherDao = new VoucherDao();
        }
        return voucherDao;
    }

    public List<Voucher> getAllNonExpired() {
        String query = "SELECT * FROM Voucher WHERE ExpDate >= GETDATE() ORDER BY ExpDate ASC";
        ResultSet rs = DbOperations.getData(query);

        List<Voucher> vouchers = new ArrayList<>();
        try {
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setId(rs.getInt("Id"));
                voucher.setMinRank(UserDao.getInstance().getRankById(rs.getInt("MinRank")));
                voucher.setMinCost(rs.getDouble("MinCost"));
                voucher.setDiscountPercentage(rs.getDouble("DiscountPercentage") == 0.0 ? -1 : rs.getDouble("DiscountPercentage"));
                voucher.setMaxDiscountAmount(rs.getDouble("MaxDiscountAmount") == 0.0 ? -1 : rs.getDouble("MaxDiscountAmount"));
                voucher.setExpDate(rs.getDate("ExpDate").toLocalDate());
                vouchers.add(voucher);
            }
            return vouchers;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void save(Voucher voucher) {
        String query = "INSERT INTO Voucher (MinRank, MinCost, DiscountPercentage, MaxDiscountAmount, ExpDate) "
                + "VALUES (?, ?, ?, ?, ?)";
        Object[] args = {
            voucher.getMinRank().getId(),
            voucher.getMinCost(),
            voucher.getDiscountPercentage() == -1 ? null : voucher.getDiscountPercentage(),
            voucher.getMaxDiscountAmount() == -1 ? null : voucher.getMaxDiscountAmount(),
            voucher.getExpDate().toString()
        };
        DbOperations.updateData(query, args, "New voucher added successfully!");
    }

    public List<Voucher> getVouchersOfUser(int userId) {
        String query = """
                        SELECT * FROM Voucher v
                        WHERE v.Id IN 
                            (SELECT u.VoucherId FROM VoucherUsage u
                            WHERE u.UserId = ? AND u.OrderId IS NULL)
                       """;
        ResultSet rs = DbOperations.getData(query, new Object[]{userId});
        try {
            List<Voucher> vouchers = new ArrayList<>();
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setId(rs.getInt("Id"));
                voucher.setMinRank(UserDao.getInstance().getRankById(rs.getInt("MinRank")));
                voucher.setMinCost(rs.getDouble("MinCost"));
                voucher.setDiscountPercentage(rs.getDouble("DiscountPercentage") == 0.0 ? -1 : rs.getDouble("DiscountPercentage"));
                voucher.setMaxDiscountAmount(rs.getDouble("MaxDiscountAmount") == 0.0 ? -1 : rs.getDouble("MaxDiscountAmount"));
                voucher.setExpDate(rs.getDate("ExpDate").toLocalDate());
                vouchers.add(voucher);
            }
            return vouchers;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
}
