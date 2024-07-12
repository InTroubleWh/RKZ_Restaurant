package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.reservationBean;

public class reservationDAO {

    private Connection con;
    private String sql;
    private PreparedStatement ps;
    private ResultSet rs;

    public reservationDAO(Connection con) {
        this.con = con;
    }

    public int newReservation(reservationBean res, int userId) throws SQLException {
        int status = 0;
        sql = "INSERT INTO reservation (userId, customerName, phoneNumber, outletId, reservedDate, reservedTime, duration_hours, dateOnReservation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, res.getCustomerName());
            ps.setString(3, res.getPhoneNumber());
            ps.setInt(4, res.getOutletId());
            ps.setDate(5, java.sql.Date.valueOf(res.getReservedDate()));
            ps.setTime(6, java.sql.Time.valueOf(res.getReservedTime()));
            ps.setInt(7, res.getDurationHours());
            ps.setTimestamp(8, java.sql.Timestamp.valueOf(res.getDateOnReservation()));

            status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return status;
    }

    public boolean reservationAvailable(LocalDate date, LocalTime time, int outletId, int durationHours) throws SQLException {
        sql = "SELECT reservedTime, duration_hours FROM reservation WHERE reservedDate = ? AND outletId = ? AND status = 'reserved'";
        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setInt(2, outletId);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalTime reservedTime = rs.getTime("reservedTime").toLocalTime();
                int existingDurationHours = rs.getInt("duration_hours");

                LocalTime endTime = reservedTime.plusHours(existingDurationHours);
                LocalTime requestedEndTime = time.plusHours(durationHours);

                // Check if the requested time is within the existing reservation period
                if ((time.isAfter(reservedTime) && time.isBefore(endTime)) ||
                    (requestedEndTime.isAfter(reservedTime) && requestedEndTime.isBefore(endTime)) ||
                    time.equals(reservedTime) || requestedEndTime.equals(endTime)) {
                    return false;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return true;
    }

    public List<reservationBean> getReservations() throws SQLException {
        List<reservationBean> reservations = new ArrayList<>();
        sql = "SELECT * FROM reservation";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                reservationBean res = new reservationBean();
                res.setReserveId(rs.getInt("reserveId"));
                res.setUserId(rs.getInt("userId"));
                res.setOutletId(rs.getInt("outletId"));
                res.setCustomerName(rs.getString("customerName"));
                res.setPhoneNumber(rs.getString("phoneNumber"));
                res.setReservedDate(rs.getDate("reservedDate").toLocalDate());
                res.setReservedTime(rs.getTime("reservedTime").toLocalTime());
                res.setDurationHours(rs.getInt("duration_hours"));
                res.setDateOnReservation(rs.getTimestamp("dateOnReservation").toLocalDateTime());
                res.setStatus(rs.getString("status"));
                reservations.add(res);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return reservations;
    }
}
