/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Order;
import model.OrderDetails;
import model.Status;

/**
 *
 * @author Admin
 */
public class OrderDao {

    private static OrderDao orderDao;

    public static OrderDao getInstance() {
        if (orderDao == null) {
            orderDao = new OrderDao();
        }
        return orderDao;
    }

    public List<Order> getOrders(int userId, LocalDate fromDate, LocalDate toDate) {
        try {
            List<Order> orders = new ArrayList<>();

            String query = null;
            Object[] args = null;
            if (fromDate != null && toDate != null) {
                query = "SELECT * FROM [Order] WHERE CONVERT(DATE, CreatedAt) BETWEEN ? AND ? ";
            } else {
                if (fromDate == null) {
                    fromDate = LocalDate.of(2000, 1, 1);
                }
                if (toDate == null) {
                    toDate = LocalDate.now();
                }
                query = "SELECT TOP 30 * FROM [Order] WHERE CONVERT(DATE, CreatedAt) BETWEEN ? AND ? ";
            }
            if (userId != -1) {
                query += "AND UserId = ? ";
                args = new Object[]{fromDate.toString(), toDate.toString(), userId};
            } else {
                args = new Object[]{fromDate.toString(), toDate.toString()};
            }
            query += "ORDER BY CreatedAt ASC";
            ResultSet rs = DbOperations.getData(query, args);
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("Id"));
                order.setUser(UserDao.getInstance().getById(rs.getInt("UserId")));
                order.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                order.setFinalCost(rs.getDouble("FinalCost"));
                order.setShipper(StaffDao.getInstance().getById(rs.getInt("ShipperId")));
                order.setStatus(getStatusById(rs.getInt("StatusId")));
                orders.add(order);
            }
            return orders;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public Order getById(int id) {
        String query = "SELECT * FROM [Order] WHERE Id = ?";
        ResultSet rs = DbOperations.getData(query, new Object[]{id});
        try {
            Order order = null;
            if (rs.next()) {
                order = new Order();
                order.setId(id);
                order.setUser(UserDao.getInstance().getById(rs.getInt("UserId")));
                order.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                order.setTotalCost(rs.getDouble("TotalCost"));
                order.setShipCost(rs.getDouble("ShipCost"));
                order.setDiscount(rs.getDouble("Discount"));
                order.setFinalCost(rs.getDouble("FinalCost"));
                order.setDeliveryInfo(DeliveryInfoDao.getInstance().getById(rs.getInt("DeliveryInfoId")));
                order.setShipper(StaffDao.getInstance().getById(rs.getInt("ShipperId")));
                order.setStatus(getStatusById(rs.getInt("StatusId")));
                order.setItems(new ArrayList<>());

                rs = DbOperations.getData("SELECT * FROM OrderDetails WHERE OrderId = ?", new Object[]{id});
                while (rs.next()) {
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setId(rs.getInt("Id"));
                    orderDetails.setProduct(ProductDao.getInstance().getById(rs.getInt("ProductId")));
                    orderDetails.setQuantity(rs.getInt("Quantity"));
                    orderDetails.setUnitPrice(rs.getDouble("UnitPrice"));
                    orderDetails.setTotalAmount(rs.getDouble("TotalAmount"));
                    order.getItems().add(orderDetails);
                }
            }
            return order;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public void saveOrder(Order order) {
        String insertOrder = "INSERT INTO [Order] (UserId, TotalCost, ShipCost, Discount, DeliveryInfoId, PaymentMethod, PaymentInfoId, StatusId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {order.getUser().getId(), order.getTotalCost(), order.getShipCost(), order.getDiscount(), order.getDeliveryInfo().getId(), order.getPaymentMethodId(), order.getPaymentInfo() == null ? null : order.getPaymentInfo().getId(), 1};

        DbOperations.updateData(insertOrder, args, "");

        final int orderId;
        ResultSet rs = DbOperations.getData("SELECT MAX(Id) AS OrderId FROM [Order]");
        try {
            if (rs.next()) {
                orderId = rs.getInt("OrderId");
            } else {
                orderId = -1;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        }

        String insertItem = "INSERT INTO OrderDetails (OrderId, ProductId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
        order.getItems().forEach(orderDetails -> {
            Object[] args2 = {orderId, orderDetails.getProduct().getId(), orderDetails.getQuantity(), orderDetails.getUnitPrice()};
            DbOperations.updateData(insertItem, args2, "");
        });

        if (order.getVoucher() != null) {
            String updateVoucherUsage = "UPDATE VoucherUsage SET OrderId = ? WHERE UserId = ? AND VoucherId = ?";
            DbOperations.updateData(updateVoucherUsage, new Object[]{orderId, order.getUser().getId(), order.getVoucher().getId()}, "");
        }

        JOptionPane.showMessageDialog(null, "Your order was placed successfully! Thanks for purchasing!");
    }

    public void updateOrder(Order order) {
        String query = "UPDATE [Order] SET StatusId = ?, ShipperId = ? WHERE Id = ?";
        Object[] args = {order.getStatus().getId(), order.getShipper() == null ? null : order.getShipper().getId(), order.getId()};
        DbOperations.updateData(query, args, "Order updated successfully");
    }

    public List<Status> getAllStatus() {
        try {
            List<Status> statusList = new ArrayList<>();
            ResultSet rs = DbOperations.getData("SELECT * FROM Status");
            while (rs.next()) {
                Status status = new Status();
                status.setId(rs.getInt("Id"));
                status.setValue(rs.getString("Value"));
                statusList.add(status);
            }
            return statusList;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    public Status getStatusById(int id) {
        try {
            ResultSet rs = DbOperations.getData("SELECT * FROM Status WHERE Id = ?", new Object[]{id});
            Status status = null;
            if (rs.next()) {
                status = new Status();
                status.setId(rs.getInt("Id"));
                status.setValue(rs.getString("Value"));
            }
            return status;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

}
