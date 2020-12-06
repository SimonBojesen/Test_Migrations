package datalayer;

import dto.Customer;
import datalayer.customer.CustomerStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
@SpringBootApplication
public class Main {

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

            SpringApplication.run(Main.class, args);
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + ", " + c.getPhonenumber() + " }";
    }
}
