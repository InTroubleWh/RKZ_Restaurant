package servlet;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import java.sql.SQLException;
import controller.imageDAO;
import database.MyConnection;

@WebServlet(name = "imageServlet", urlPatterns = {"/image"})
public class imageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        String source = request.getParameter("source");
        imageDAO dao = new imageDAO(MyConnection.getConnection());
        switch (source) {
            case "menu":
                try {
                    Blob imageBlob = dao.getItemImageById(itemId);
                    byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                    response.setContentType("image/png");
                    OutputStream os = response.getOutputStream();
                    os.write(imageBytes);
                    os.flush();
                    os.close();
                } catch (SQLException e) {
                }
                break;
            case "banner":
                try {
                    Blob imageBlob = dao.getBannerBlobById(itemId);
                    byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                    response.setContentType("image/png");
                    OutputStream os = response.getOutputStream();
                    os.write(imageBytes);
                    os.flush();
                    os.close();
                } catch (SQLException e) {
                }
                break;
            default:
                response.sendRedirect("loginerror.jsp");
                break;
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
    }// </editor-fold>

}
