package controller;

import database.MyConnection;
import model.transactionBean;
import model.cartItemBean;
import java.util.List;
import java.sql.*;

public class transactionDAO {

    private Connection con;

    public transactionDAO(Connection con) {
        this.con = con;
    }

    public int saveTransaction(transactionBean transaction) throws SQLException {
        Connection conn = MyConnection.getConnection();
        PreparedStatement pstmt = null;
        int userId = transaction.getUserId();
        int status = 0;

        try {
            String insertTransactionSQL = "INSERT INTO transactions (userId, outletId, transactionDate, totalAmount, address, paymentMethod) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userId);
            System.out.println(transaction.getOutletId());
            pstmt.setInt(2, transaction.getOutletId());
            pstmt.setTimestamp(3, new java.sql.Timestamp(transaction.getTransactionDate().getTime()));
            pstmt.setBigDecimal(4, transaction.getTotalAmount());
            pstmt.setString(5, transaction.getAddress());
            pstmt.setString(6, transaction.getPaymentMethod());
            status = pstmt.executeUpdate();
            if(status<=0) {
                return 0;
            }
            // Get the generated transactionId
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int transactionId = rs.getInt(1);

                // Insert into transaction_items table
                String insertItemSQL = "INSERT INTO transactionitems (transactionId, itemId, itemName, quantity, price) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertItemSQL);

                for (cartItemBean item : transaction.getItems()) {
                    pstmt.setInt(1, transactionId);
                    pstmt.setInt(2, item.getItemId());
                    pstmt.setString(3, item.getItemName());
                    pstmt.setInt(4, item.getQuantity());
                    pstmt.setBigDecimal(5, item.getPrice());
                    pstmt.addBatch();
                }
                int[] updates = pstmt.executeBatch();
                for(int i = 0;i < updates.length;i++) {
                    status += updates[i];
                }
                if (status>0) {
                    cartDAO cartDAO = new cartDAO(conn);
                    cartDAO.clearUserCart(userId);
                }
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return status;
    }
}
