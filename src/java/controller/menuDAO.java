package controller;

import database.MyConnection;
import java.sql.*;
import model.menuBean;
import java.util.List;
import java.util.ArrayList;

public class menuDAO {

    private Connection con;
    private String sql;
    private menuBean menu;
    private PreparedStatement ps;
    private ResultSet rs;

    public menuDAO(Connection con) {
        this.con = con;
    }

    public List<menuBean> getMenuItemByCategory(String category) throws SQLException {
        Connection con = MyConnection.getConnection();
        List<menuBean> list = new ArrayList<menuBean>();
        sql = "SELECT itemId, name, price, category FROM menu WHERE category = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                menuBean item = new menuBean();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setCategory(rs.getString("category"));
                list.add(item);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return list;
    }

    public menuBean getMenuItemById(int id) throws SQLException {
        menuBean item = new menuBean();
        sql = "SELECT itemId, name, price, category FROM menu WHERE itemId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                item.setItemId(id);
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setCategory(rs.getString("category"));
            } else {
                item = null;
            }
        } finally {
            rs.close();
            ps.close();
        }
        return item;
    }

    public List<menuBean> getAllMenuItems() throws SQLException {
        List<menuBean> list = new ArrayList<menuBean>();
        sql = "SELECT itemId, name, price, category FROM menu";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                menuBean item = new menuBean();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setCategory(rs.getString("category"));
                list.add(item);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return list;
    }

    public List<menuBean> getSpecialMenuItem() throws SQLException {
        List<menuBean> list = new ArrayList<menuBean>();
        sql = "SELECT menu.itemId, menu.name, menu.price, specialoffer.originalPrice FROM specialoffer JOIN menu ON specialoffer.itemId = menu.itemId";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                menuBean item = new menuBean();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setOriginalPrice(rs.getBigDecimal("originalPrice"));
                list.add(item);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return list;
    }

    public int deleteMenuItem(int itemId) throws SQLException {
        sql = "DELETE FROM menu WHERE itemId = ?";
        int status = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            status = ps.executeUpdate();
        } finally {
            ps.close();
        }
        return status;
    }

    public List<menuBean> searchMenuItems(String query) {
        List<menuBean> items = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE name LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                menuBean item = new menuBean();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setCategory(rs.getString("category"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<menuBean> getMenuSuggestions(String query) {
        List<menuBean> suggestions = new ArrayList<>();
        String sql = "SELECT itemId, name FROM menu WHERE name LIKE ? LIMIT 5";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                menuBean item = new menuBean();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                suggestions.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestions;
    }
}
