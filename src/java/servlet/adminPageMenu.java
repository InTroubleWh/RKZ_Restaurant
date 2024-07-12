package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.menuDAO;
import model.menuBean;
import database.MyConnection;
import com.google.gson.Gson;
import java.sql.SQLException;

@WebServlet(name = "adminPageMenuServlet", urlPatterns = {"/adminPageMenu"})
public class adminPageMenu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        menuDAO dao = new menuDAO(MyConnection.getConnection());
        Gson gson = new Gson();
        String json;
        int itemId = 0;

        try {
            switch (action) {
                case "getAll":
                    List<menuBean> items = dao.getAllMenuItems();
                    json = gson.toJson(items);
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                    break;
                case "getData":
                    itemId = Integer.parseInt(request.getParameter("id"));
                    menuBean item = dao.getMenuItemById(itemId);
                    json = gson.toJson(item);
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                    break;
                case "add":
                    // Handle add menu item logic
                    break;
                case "update":
                    // Handle update menu item logic
                    break;
                case "delete":
                    itemId = Integer.parseInt(request.getParameter("itemId"));
                    int change = dao.deleteMenuItem(itemId);
                    response.getWriter().write(change + " changes made");
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
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

