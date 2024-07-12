package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.sql.Connection;

public class reservationBean {
    private int reserveId, userId, outletId, durationHours;
    private String customerName, phoneNumber, status;
    private LocalDate reservedDate;
    private LocalTime reservedTime;
    private LocalDateTime dateOnReservation;
 
    public int getDurationHours() {
        return durationHours;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public reservationBean() {
    }

    public reservationBean(String customerName, String phoneNumber, int outlet, LocalDate reservedDate, LocalTime reservedTime) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.outletId = outlet;
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
        this.dateOnReservation = LocalDateTime.now();
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDate reservedDate) {
        this.reservedDate = reservedDate;
    }

    public LocalTime getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(LocalTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    public LocalDateTime getDateOnReservation() {
        return dateOnReservation;
    }

    public void setDateOnReservation(LocalDateTime dateOnReservation) {
        this.dateOnReservation = dateOnReservation;
    }
}
