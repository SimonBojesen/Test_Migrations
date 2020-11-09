package main;

import datalayer.ConnectionString;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;

import java.sql.SQLException;

public final class Main {

    public static void main(String[] args) throws SQLException {
        final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
        final String user = "root";
        final String pass = "testuser123";
        CustomerStorageImpl storage = new CustomerStorageImpl(new ConnectionString(conStr, user, pass));

        System.out.println("Got customers: ");
        for(Customer c : storage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + ", " + c.getPhonenumber() + " }";
    }
    private Main(){
    }
}
