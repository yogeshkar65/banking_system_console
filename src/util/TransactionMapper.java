package util;

import entity.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper {
    public static Transaction map(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getString("transaction_type"),
                rs.getInt("from_account_id"),
                rs.getInt("to_account_id"),
                rs.getBigDecimal("amount"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );

    }
}
