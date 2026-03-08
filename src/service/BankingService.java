package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import entity.Account;
import entity.Customer;
import entity.Transaction;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BankingService {
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public BankingService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }
    public boolean createCustomer(Customer customer){
        return customerDAO.createCustomer(customer);
    }

    public Customer getCustomerById(int customerId){
        return customerDAO.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    public List<Customer> searchCustomer(String keyWord){
        return customerDAO.searchCustomers(keyWord);
    }

    public boolean createAccount(Account account){
        return accountDAO.createAccount(account);
    }

    public Account getAccountById(int accountId){
        return accountDAO.getAccountById(accountId);
    }

    public List<Account> getAccountsByCustomerId(int customerId){
        return accountDAO.getAccountsByCustomerId(customerId);
    }

    public boolean createTransaction(Transaction transaction) {

        try (Connection conn = DBConnection.getConnection()) {
            return transactionDAO.createTransaction(conn, transaction);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionsByAccountId(int accountId){
        return transactionDAO.getTransactionByAccountId(accountId);
    }

    public boolean deposit(int accountId,BigDecimal amount){

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        Account account = accountDAO.getAccountById(accountId);
        if(account == null) return false;
        BigDecimal currentBalance = account.getBalance();
        BigDecimal updatedBalance = currentBalance.add(amount);
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            boolean credit = accountDAO.updateBalance(conn,accountId, updatedBalance);
            if(!credit){
                conn.rollback();
                return false;
            }
                Transaction transaction = new Transaction("DEPOSIT", null, accountId, amount);
                boolean recorded = transactionDAO.createTransaction(conn, transaction);

                if (!recorded) {
                    conn.rollback();
                    return false;
                }
                 conn.commit();
                 return true;
        }
        catch (Exception e){
            try {
                if(conn != null) conn.rollback();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(conn != null){
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean withdraw(int accountId,BigDecimal amount){

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        Account account = accountDAO.getAccountById(accountId);
        if(account == null) return false;
        BigDecimal currentBalance = account.getBalance();

        if(currentBalance.compareTo(amount)<0){
            return false;
        }

        BigDecimal updatedBalance = currentBalance.subtract(amount);
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            boolean debit = accountDAO.updateBalance(conn, accountId, updatedBalance);
            if(!debit) {
                conn.rollback();
                return false;
            }
                Transaction transaction = new Transaction("WITHDRAW", accountId, null, amount);
                boolean recorded = transactionDAO.createTransaction(conn, transaction);

                if (!recorded) {
                    conn.rollback();
                    return false;
                }
                conn.commit();
                return true;
        }
        catch (Exception e){
            try {
                if(conn != null) conn.rollback();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean transfer(int fromAccountId,int toAccountId,BigDecimal amount){

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if(fromAccountId == toAccountId){
            return false;
        }
        Account fromAccount = accountDAO.getAccountById(fromAccountId);
        Account toAccount = accountDAO.getAccountById(toAccountId);

        if(fromAccount == null || toAccount == null) return false;

        BigDecimal currentFromAccountBalance = fromAccount.getBalance();
        BigDecimal currentToAccountBalance = toAccount.getBalance();

        if(currentFromAccountBalance.compareTo(amount)<0){
            return false;
        }
        BigDecimal updatedFromAccountBalance = currentFromAccountBalance.subtract(amount);
        BigDecimal updatedToAccountBalance = currentToAccountBalance.add(amount);

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            boolean debit = accountDAO.updateBalance(conn,fromAccountId, updatedFromAccountBalance);
            boolean credit = accountDAO.updateBalance(conn,toAccountId, updatedToAccountBalance);
            if(!debit || !credit){
                conn.rollback();
                return false;
            }
            Transaction transaction = new Transaction("TRANSFER", fromAccountId, toAccountId, amount);
            boolean recorded = transactionDAO.createTransaction(conn, transaction);

            if (!recorded) {
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
            }
        catch (Exception e){
            try {
                if(conn != null){
                    conn.rollback();
                }
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
}
