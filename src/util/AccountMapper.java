package util;

import entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {
    public static Account map(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("account_id"),
                rs.getLong("account_no"),
                rs.getInt("customer_id"),
                rs.getString("account_type"),
                rs.getBigDecimal("balance"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
                );
    }
}
