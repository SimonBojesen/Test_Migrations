package unit.servicelayer.customer;

import datalayer.customer.CustomerStorage;
import dto.Employee;
import org.junit.jupiter.api.*;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeServiceException;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateCustomerTest {

    // SUT (System Under Test)
    private CustomerService customerService;

    // DOC (Depended-on Component)
    private CustomerStorage storageMock;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(CustomerStorage.class);
        customerService = new CustomerServiceImpl(storageMock);
    }

    @Test
    private void mustCallStorageWhenCreatingCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        // Act
        var firstName = "a";
        var lastName = "b";
        int phonenumber = 12345678;
        customerService.createCustomer(firstName, lastName, phonenumber);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .createCustomer(
                        argThat(x -> x.getFirstname().equals(firstName) &&
                                x.getLastname().equals(lastName) &&
                                x.getPhonenumber() == phonenumber)
                        );
    }

    @Test
    private void mustCallStorageWhenGettingCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var id = 1;
        // Act

        customerService.getCustomerById(id);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .getCustomerWithId(
                        intThat(x -> x.equals(id)));
    }
}
