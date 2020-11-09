package datalayer.customer;

import dto.Customer;
import dto.CustomerCreation;

import java.sql.SQLException;
import java.util.List;

public interface CustomerStorage {
    Customer getCustomerWithId(int customerId) throws SQLException;
    List<Customer> getCustomers() throws SQLException;
    int createCustomer(CustomerCreation customerToCreate) throws SQLException;
}
