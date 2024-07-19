package servlet;

import controller.cartDAO;
import database.MyConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "removeFromCartServlet", urlPatterns = {"/removeFromCart"})
public class removeFromCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        try {
            con = MyConnection.getConnection();
            cartDAO dao = new cartDAO(con);
            if(request.getSession(false)==null||request.getSession(false).getAttribute("userId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // Retrieve userId from session
            int userId = (int) request.getSession().getAttribute("userId");

            // Retrieve itemId from request parameter
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            // Remove item from cart
            dao.removeItem(userId, itemId);

            // Optionally, send a success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Item removed successfully from cart.");
        } catch (SQLException | NumberFormatException e) {
            // Handle exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to remove item from cart.");
            e.printStackTrace(); // Log the exception for debugging
        } finally {
            // Close database connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log connection close exception
                }
            }
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
}
