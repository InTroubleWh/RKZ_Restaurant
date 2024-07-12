package servlet;

import com.google.gson.Gson;
import controller.cartDAO;
import database.MyConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cartItemBean;

@WebServlet(name = "updateCartServlet", urlPatterns = {"/updateCart"})
public class updateCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId");

            // Read the JSON data from the request body
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            // Parse the JSON data
            String jsonString = sb.toString();
            System.out.println(jsonString);
            Gson gson = new Gson();
            cartItemBean updateRequest = gson.fromJson(jsonString, cartItemBean.class);

            // Update the cart in the database
            try (Connection con = MyConnection.getConnection()) {
                cartDAO cartdao = new cartDAO(con);
                updateRequest.setUserId(userId); // Set the user ID in the cartItemBean
                System.out.println(updateRequest.getItemId());
                if (updateRequest.getQuantity() == 0) {
                    cartdao.removeItem(userId, updateRequest.getItemId());
                } else {
                    cartdao.updateItemQuantity(updateRequest);
                }
            }
            // Send a success response
            out.print("{\"status\":\"success\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while updating the cart.\"}");
        } finally {
            out.flush();
            out.close();
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
        return "Servlet for updating cart items";
    }
}

