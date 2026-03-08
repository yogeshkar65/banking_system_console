package dao;

import entity.Account;
import entity.Transaction;
import util.AccountMapper;
import util.DBConnection;
import util.TransactionMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean createTransaction(Transaction transaction) {

        String sql = "INSERT INTO transactions(transaction_type,from_account_id,to_account_id,amount,created_at) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setString(1,transaction.getTransactionType());
            ps.setInt(2,transaction.getFromAccountId());
            ps.setInt(3,transaction.getToAccountId());
            ps.setBigDecimal(4,transaction.getAmount());
            ps.setTimestamp(5,transaction.getCreatedAt());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Transaction> getTransactionByAccountId(int accountId){
        String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id";
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
