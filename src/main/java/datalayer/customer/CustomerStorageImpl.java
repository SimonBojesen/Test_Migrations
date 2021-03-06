package datalayer.customer;

import datalayer.ConnectionString;
import dto.Customer;
import dto.CustomerCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerStorageImpl implements CustomerStorage {
    private ConnectionString connection;

    public CustomerStorageImpl(ConnectionString connection){
       this.connection = connection;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connection.getConnectionUrl(), connection.getUsername(), connection.getPassword());
    }

    @Override
    public Customer getCustomerWithId(int customerId) throws SQLException {
        var sql = "select ID, firstname, lastname, birthdate, phonenumber from Customers where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var phonenumber = resultSet.getInt("phonenumber");
                    return new Customer(id, firstname, lastname, phonenumber);
                }
                return null;
            }
        }
    }

    public List<Customer> getCustomers() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Customer>();

            ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, phonenumber from Customers");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int phonenumber = resultSet.getInt("phonenumber");
                Customer c = new Customer(id, firstname, lastname, phonenumber);
                results.add(c);
            }

            return results;
        }
    }

    public int createCustomer(CustomerCreation customerToCreate) throws SQLException {
        var sql = "insert into Customers(firstname, lastname, phonenumber) values (?, ?, ?)";
        try (var con = getConnection();
            var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customerToCreate.getFirstname());
            stmt.setString(2, customerToCreate.getLastname());
            stmt.setInt(3, customerToCreate.getPhonenumber());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }
}
