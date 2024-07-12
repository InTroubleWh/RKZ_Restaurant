package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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

        // Get form parameters
        int customerId = (int) request.getSession().getAttribute("userId");
        String customerName = request.getParameter("username");
        String phoneNumber = request.getParameter("No_HP");
        int outlet = Integer.parseInt(request.getParameter("outlet"));
        String reservedDateStr = request.getParameter("reserve_date");
        String reservedTimeStr = request.getParameter("time");
        int durationHours = Integer.parseInt(request.getParameter("duration")); // Parse duration parameter

        // Validate inputs
        boolean isValid = true;
        String errorMessage = "";

        // Validate phone number (Example validation)
        if (!phoneNumber.matches("^[1-9]\\d{9,14}$")) {
            isValid = false;
            errorMessage = "Phone number invalid";
        }

        if (isValid) {
            try {
                // Parse dates and times
                LocalDate reservedDate = LocalDate.parse(reservedDateStr);
                LocalTime reservedTime = LocalTime.parse(reservedTimeStr);

                // Check availability
                reservationDAO dao = new reservationDAO(MyConnection.getConnection());
                if (dao.reservationAvailable(reservedDate, reservedTime, outlet, durationHours)) {
                    // Create reservationBean object with duration
                    reservationBean res = new reservationBean(customerName, phoneNumber, outlet, reservedDate, reservedTime);
                    res.setDurationHours(durationHours); // Set duration from parameter

                    // Attempt to save reservation
                    int result = dao.newReservation(res, customerId);

                    if (result > 0) {
                        // Successful reservation
                        JsonObject json = new JsonObject();
                        json.addProperty("success", true);
                        response.getWriter().write(json.toString());
                    } else {
                        // Failed to save reservation
                        JsonObject json = new JsonObject();
                        json.addProperty("success", false);
                        json.addProperty("message", "Reservation failed");
                        response.getWriter().write(json.toString());
                    }
                } else {
                    // Reservation slot not available
                    JsonObject json = new JsonObject();
                    json.addProperty("success", false);
                    json.addProperty("message", "Reservation slot not available");
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
            JsonObject json = new JsonObject();
            json.addProperty("success", false);
            json.addProperty("message", errorMessage);
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
