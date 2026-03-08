package dao;

import entity.Account;
import util.AccountMapper;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public boolean createAccount(Account account) {

        String sql = "INSERT INTO accounts(account_no,customer_id,account_type,balance,status,created_at) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, account.getAccountNumber());
            ps.setInt(2, account.getCustomerId());
            ps.setString(3, account.getAccountType());
            ps.setBigDecimal(4, account.getBalance());
            ps.setString(5, account.getStatus());
            ps.setTimestamp(6, account.getCreatedAt());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Account getAccountById(int accountId){
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return AccountMapper.map(rs);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAccountsByCustomerId(int customerId){
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        List<Account> accounts = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account =  AccountMapper.map(rs);
                accounts.add(account);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean updateBalance(Connection conn, int accountId, BigDecimal balance) throws SQLException {

        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, balance);
            ps.setInt(2, accountId);

            return ps.executeUpdate() > 0;
        }
    }

}
