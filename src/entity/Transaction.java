package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer transactionId;
    private String transactionType;
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(String transactionType, Integer fromAccountId, Integer toAccountId, BigDecimal amount, LocalDateTime createdAt) {
        this.transactionType = transactionType;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Transaction(Integer transactionId, String transactionType, Integer fromAccountId, Integer toAccountId, BigDecimal amount, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
