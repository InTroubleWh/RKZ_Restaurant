package servlet;

import tool.GenericValidation;
import model.userBean;
import controller.userDAO;
import database.MyConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import tool.PasswordHasher;

@WebServlet(name = "registerServlet", urlPatterns = {"/Register"})
public class registerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        userBean user = new userBean();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String pnum = request.getParameter("phone_number");
        String pass = request.getParameter("password");
        pass = PasswordHasher.hashPassword(pass);
        
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone_number(pnum);
        user.setPassword(pass);
        
        Connection con = MyConnection.getConnection();
        try {
            userDAO dao = new userDAO(con);
            int result = dao.insertNewAccount(user);
            if (result > 0) {
                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("registererror.jsp");
            }
        } catch (SQLException e) {
            // Log the exception and redirect to an error page
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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

    @Override
    public String getServletInfo() {
        return "Register Servlet";
    }
}
