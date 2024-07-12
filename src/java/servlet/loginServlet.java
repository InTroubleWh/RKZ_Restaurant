package servlet;

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
import database.MyConnection;
import controller.userDAO;
import model.userBean;
import tool.PasswordHasher;

@WebServlet(name = "loginServlet", urlPatterns = {"/Login"})
public class loginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");

        Connection con = MyConnection.getConnection();
        try (PrintWriter out = response.getWriter()) {
            userDAO dao = new userDAO(con);
            userBean user = dao.getUserByUsernameOrEmail(identifier);

            if (user != null && user.getPassword().equals(PasswordHasher.hashPassword(password))) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("loggedIn", true);
                boolean isAdmin = dao.isAdmin(user.getUserId());
                System.out.println("Admin is " + isAdmin);
                if (isAdmin) {
                    session.setAttribute("isAdmin", true);
                }
                out.write(isAdmin ? "admin" : "success");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.write("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "Internal server error. Please try again later.");
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
        return "Login Servlet";
    }
}
