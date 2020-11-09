package servicelayer.customer;

import dto.Customer;


public interface CustomerService {
    int createCustomer(String firstName, String lastName, int phonenumber) throws CustomerServiceException;
    Customer getCustomerById(int id) throws CustomerServiceException;
}
