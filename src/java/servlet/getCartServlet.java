package servlet;

import com.google.gson.Gson;
import controller.cartDAO;
import database.MyConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cartItemBean;

@WebServlet(name = "getCartServlet", urlPatterns = {"/getCart"})
public class getCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Check if a session exists, without creating a new one
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"error\":\"Please log in to view your cart.\"}");
            out.flush();
            out.close();
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try (Connection con = MyConnection.getConnection()) {
            cartDAO cartdao = new cartDAO(con);
            List<cartItemBean> cartItems = cartdao.getCartItems(userId);
            Gson gson = new Gson();
            String jsonCartItems = gson.toJson(cartItems);
            response.setContentType("application/json");
            response.getWriter().write(jsonCartItems);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while retrieving cart items.\"}");
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
        return "Servlet for retrieving cart items";
    }
}
