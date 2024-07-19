package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import controller.reservationDAO;
import database.MyConnection;
import model.reservationBean;

@WebServlet(name = "reserveServlet", urlPatterns = {"/reserve"})
public class reserveServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        if (request.getSession(false) == null || request.getSession(false).getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // Get form parameters
        int customerId = (int) request.getSession().getAttribute("userId");
        String customerName = request.getParameter("username");
        String phoneNumber = request.getParameter("No_HP");
        int outlet = Integer.parseInt(request.getParameter("outlet"));
        String reservedDateStr = request.getParameter("reserve_date");
        String reservedTimeStr = request.getParameter("time");
        int durationHours = Integer.parseInt(request.getParameter("duration"));

        // Validate inputs
        boolean isValid = true;
        String jsonSuccess = "success";
        String jsonMessage = "message";
        String errorMessage = "";

        // Validate phone number (Example validation)
        if (!phoneNumber.matches("^[1-9]\\d{9,14}$")) { //input should be all digits plus cannot exceed 15
            isValid = false;
            errorMessage = "Phone number invalid";
            JsonObject json = new JsonObject();
            json.addProperty(jsonSuccess, false);
            json.addProperty(jsonMessage, errorMessage);
            response.getWriter().write(json.toString());
            return;
        }

        // Parse dates and times
        LocalDate reservedDate = LocalDate.parse(reservedDateStr);
        LocalTime reservedTime = LocalTime.parse(reservedTimeStr);

        //validasi waktu reservasi. Apakah hari ini? Apabila hari ini, apakah detik ini?
        boolean validTime = false;
        long daysbetween = ChronoUnit.DAYS.between(LocalDate.now(), reservedDate);
        if (daysbetween == 0) { //validasi jam/waktu apabila di hari yang sama
            long secondsbetween = ChronoUnit.SECONDS.between(LocalTime.now(), reservedTime);
            if (secondsbetween > 0) {
                //Waktu reservasi di waktu yang mendatang dari momen kini.
                validTime = true;
            } //Apabila waktu reservasi sama atau kurang dari masa sekarang, maka tetap false.
        } else if (daysbetween > 0) { //tanggal yang di reservasi it di waktu yang mendatang
            validTime = true;
        }
        if (!validTime) {
            JsonObject json = new JsonObject();
            json.addProperty(jsonSuccess, false);
            errorMessage = "Invalid time to reserve";
            json.addProperty(jsonMessage, errorMessage);//Pesan keterangan gagal reservasi waktu tidak valid untuk reservasi.
            response.getWriter().write(json.toString());
            return;
        }

        if (isValid) {
            try {

                // Check availability
                reservationDAO dao = new reservationDAO(MyConnection.getConnection());
                if (dao.reservationAvailable(reservedDate, reservedTime, outlet, durationHours)) { //metode validasi ada waktu yang berselisih.
                    // Create reservationBean object with duration
                    reservationBean res = new reservationBean(customerName, phoneNumber, outlet, reservedDate, reservedTime);
                    res.setDurationHours(durationHours); // Set duration from parameter

                    // Attempt to save reservation
                    int result = dao.newReservation(res, customerId);

                    if (result > 0) {
                        // Successful reservation
                        JsonObject json = new JsonObject();
                        json.addProperty(jsonSuccess, true);
                        response.getWriter().write(json.toString());

                    } else {
                        // Failed to save reservation
                        errorMessage = "Failed to save reservation";
                        JsonObject json = new JsonObject();
                        json.addProperty(jsonSuccess, false);
                        json.addProperty(jsonMessage, errorMessage);
                        response.getWriter().write(json.toString());
                    }
                } else {
                    // Reservation slot not available
                    errorMessage = "Failed to save reservation due to conflicting times";
                    JsonObject json = new JsonObject();
                    json.addProperty(jsonSuccess, false);
                    json.addProperty(jsonMessage, errorMessage);
                    response.getWriter().write(json.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject json = new JsonObject();
                json.addProperty("success", false);
                json.addProperty("message", "Database error");
                response.getWriter().write(json.toString());
            }
        } else {
            // Invalid input
            errorMessage = "Invalid phone number";
            JsonObject json = new JsonObject();
            json.addProperty(jsonSuccess, false);
            json.addProperty(jsonMessage, errorMessage);
            response.getWriter().write(json.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
