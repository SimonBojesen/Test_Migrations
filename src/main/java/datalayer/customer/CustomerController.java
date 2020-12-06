package datalayer.customer;

import datalayer.ConnectionString;
import dto.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class CustomerController {

    public ConnectionString connectionString = new ConnectionString();

    public CustomerStorageImpl customerStorage = new CustomerStorageImpl(new ConnectionString(connectionString.getConnectionUrl(), connectionString.getUsername(), connectionString.getPassword()));


    @GetMapping("/customers")
    public ResponseEntity getParts() throws SQLException {
        Iterable<Customer> customers = customerStorage.getCustomers();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
