package controller;

import java.sql.*;
import model.userBean;

public class userDAO {

    private Connection con;
    private PreparedStatement ps;
    private Statement stm;
    private ResultSet rs;
    private String sql;
    private userBean bean;

    public userDAO(Connection connection) {
        this.con = connection;
    }

    public userBean getUserByUsernameOrEmail(String identifier) throws SQLException {
        sql = "SELECT userId, username, email, password FROM user_accounts WHERE email = ? OR username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, identifier);
            ps.setString(2, identifier);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userBean user = new userBean();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null; // No user found
            }
        } finally {
        }
    }

    public int insertNewAccount(userBean user) throws SQLException {
        String sql = "INSERT INTO user_accounts (username, email, userPNum, password) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone_number());
            ps.setString(4, user.getPassword());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after logging it
        }
    }
}
