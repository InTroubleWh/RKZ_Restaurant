package servlet;

import controller.cartDAO;
import controller.transactionDAO;
import database.MyConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.cartItemBean;
import model.transactionBean;

@WebServlet(name = "transactionServlet", urlPatterns = {"/transaction"})
public class transactionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String outlet = request.getParameter("outletId");
        int outletId = Integer.parseInt(outlet);
        String City = request.getParameter("city-select");
        String PostCode = request.getParameter("post-code");
        String streetAddress = request.getParameter("street-address");
        String address = City + " ".concat(PostCode) + " ".concat(streetAddress);
        String paymentMethod = request.getParameter("payment");
        //pass along informations
        if (request.getSession(false) == null || request.getSession(false).getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int userId = (int) request.getSession().getAttribute("userId");

        Connection con = MyConnection.getConnection();
        cartDAO cartdao = new cartDAO(con);
        transactionDAO transactiondao = new transactionDAO(con);
        try {
            //get this user's cart
            List<cartItemBean> items = cartdao.getCartItems(userId);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (cartItemBean item : items) {
                BigDecimal price = item.getPrice();
                BigDecimal qty = new BigDecimal(item.getQuantity());
                BigDecimal amount = price.multiply(qty);
                totalAmount = totalAmount.add(amount);
            }
            transactionBean transaction = new transactionBean();
            transaction.setUserId(userId);
            transaction.setOutletId(outletId);
            transaction.setItems(items);
            transaction.setTotalAmount(totalAmount);
            transaction.setTransactionDate(new Date());
            transaction.setAddress(address);
            transaction.setPaymentMethod(paymentMethod);//initialize transactionBean
            int status = transactiondao.saveTransaction(transaction); //save user's cart 
            if (status > 0) {
                response.sendRedirect("home.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
