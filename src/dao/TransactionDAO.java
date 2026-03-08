package dao;


import entity.Transaction;
import util.DBConnection;
import util.TransactionMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean createTransaction(Connection conn,Transaction transaction) throws SQLException {

        String sql = "INSERT INTO transactions(transaction_type,from_account_id,to_account_id,amount,created_at) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setString(1,transaction.getTransactionType());
            if(transaction.getFromAccountId() == null){
                ps.setNull(2, Types.BIGINT);
            }
            else {
                ps.setInt(2, transaction.getFromAccountId());
            }
            if(transaction.getToAccountId() == null){
                ps.setNull(3, Types.BIGINT);
            }
            else {
                ps.setInt(3, transaction.getToAccountId());
            }

            ps.setBigDecimal(4,transaction.getAmount());
            ps.setTimestamp(5,Timestamp.valueOf(transaction.getCreatedAt()));

            return ps.executeUpdate() > 0;
        }
    }
    public List<Transaction> getTransactionByAccountId(int accountId){
        String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Transaction transaction =  TransactionMapper.map(rs);
                transactions.add(transaction);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }
}
