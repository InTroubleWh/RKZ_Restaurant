package controller;

import java.sql.*;

public class imageDAO {
    private Connection con;
    private String sql;
    private PreparedStatement ps;
    private ResultSet rs;

    public imageDAO(Connection con) {
        this.con = con;
    }
    
    public Blob getBannerBlobById (int id) throws SQLException {
        Blob banner = null;
        sql = "SELECT img FROM banner WHERE bannerId = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                banner = rs.getBlob("img");
            }
        }
        finally {
            if(rs!=null) {rs.close();}
            if(ps!=null) {ps.close();}
        }
        return banner;
    }
    
    public Blob getItemImageById (int id) throws SQLException {
        Blob itemImage = null;
        sql = "SELECT img FROM menu WHERE itemId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                itemImage = rs.getBlob("img");
            }
        }
        finally {
            if(rs!=null) {rs.close();}
            if(ps!=null) {ps.close();}
        }
        return itemImage;
    }
}
