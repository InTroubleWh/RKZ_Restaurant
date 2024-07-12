package controller;

import database.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.cartItemBean;
import model.menuBean;

public class cartDAO {

    private Connection con;
    private String sql;
    private PreparedStatement ps;
    private ResultSet rs;

    public cartDAO(Connection con) {
        this.con = con;
    }

    public int addToCart(int userId, menuBean item) throws SQLException {
        int status = 0;
        Connection con = MyConnection.getConnection();
        try {
            if (itemExistsInCart(userId, item.getItemId())) {
                int quantity = getItemQuantity(userId, item.getItemId());
                quantity++;
                sql = "UPDATE cart SET quantity = ? WHERE userId = ? AND itemId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, quantity);
                ps.setInt(2, userId);
                ps.setInt(3, item.getItemId());
                status = ps.executeUpdate();
            } else {
                sql = "INSERT INTO cart (userId, itemId, quantity) values (?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, 1);
                status = ps.executeUpdate();
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return status;
    }

    public List<cartItemBean> getCartItems(int userId) throws SQLException {
        List<cartItemBean> items = new ArrayList<>();
        Connection con = MyConnection.getConnection();
        String sql = "SELECT cart.itemId, menu.name, menu.price, cart.quantity FROM cart JOIN menu ON cart.itemId = menu.itemId WHERE cart.userId = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cartItemBean item = new cartItemBean();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // rethrow the exception after logging it
        }
        // No need to explicitly close rs and ps because try-with-resources is used
        return items;
    }

    public int clearUserCart(int userId) throws SQLException {
        sql = "DELETE FROM cart WHERE userId = ?;";
        int status = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            status = ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return status;
    }

    private boolean itemExistsInCart(int userId, int itemId) throws SQLException {
        sql = "SELECT itemId FROM cart WHERE userId = ? AND itemId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            if(rs!=null) {rs.close();}
            if(ps!=null) {ps.close();}
        }
        return false;
    }

    private int getItemQuantity(int userId, int itemId) throws SQLException {
        int qty = 0;
        sql = "SELECT quantity FROM cart WHERE userId = ? AND itemId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                qty = rs.getInt("quantity");
                rs.close();
                return qty;
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return qty;
    }

    // Method to update item quantity
    public void updateItemQuantity(cartItemBean item) throws SQLException {
        String query = "UPDATE cart SET quantity = ? WHERE userId = ? AND itemId = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getUserId());
            ps.setInt(3, item.getItemId());
            ps.executeUpdate();
        }
    }

    // Method to remove an item from the cart
    public void removeItem(int userId, int itemId) throws SQLException {
        String query = "DELETE FROM cart WHERE userId = ? AND itemId = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        }
    }

    //for debugging which sql statement last used.
    public String getCartDAOQuery() {
        return this.sql;
    }
}
