package dao;

import entity.Customer;
import util.CustomerMapper;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public boolean createCustomer(Customer customer){
        String sql = "INSERT INTO customers(name,email,phone,address) VALUES (?,?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,customer.getName());
            ps.setString(2,customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Customer getCustomerById(Integer customerId){
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return CustomerMapper.map(rs);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getAllCustomers(){
        String sql = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Customer customer = CustomerMapper.map(rs);
                customers.add(customer);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> searchCustomers(String keyWord){
        String sql = "SELECT * FROM customers WHERE name LIKE ? OR email LIKE ? OR phone LIKE ?";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,"%"+keyWord+"%");
            ps.setString(2,"%"+keyWord+"%");
            ps.setString(3,"%"+keyWord+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Customer customer = CustomerMapper.map(rs);
                customers.add(customer);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return customers;
    }
}
