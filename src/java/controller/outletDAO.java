package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.outletBean;

public class outletDAO {

    private Connection con;
    private String sql;
    private PreparedStatement ps;
    private ResultSet rs;

    public outletDAO(Connection con) {
        this.con = con;
    }

    public List<outletBean> getOutlets() {
        sql = "SELECT * FROM outlet";
        List<outletBean> list = new ArrayList<outletBean>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                outletBean outlet = new outletBean();
                outlet.setOutletId(rs.getInt("outletId"));
                outlet.setOutletName(rs.getString("outletName"));
                outlet.setAddress(rs.getString("address"));
                list.add(outlet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
