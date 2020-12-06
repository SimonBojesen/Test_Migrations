package unit.servicelayer.employee;

import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;
import org.junit.jupiter.api.*;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateEmployeeTest {

    // SUT (System Under Test)
    private EmployeeService employeeService;

    // DOC (Depended-on Component)
    private EmployeeStorage storageMock;


    @BeforeEach
    public void beforeEach(){
        storageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(storageMock);
    }

    @Test
    public void mustCallStorageWhenCreatingEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "a";
        var lastName = "b";
        var birthdate = new Date(123456789l);
        // Act

        employeeService.createEmployee(firstName, lastName, birthdate);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .createEmployee(
                        argThat(x -> x.getFirstname().equals(firstName) &&
                                x.getLastname().equals(lastName) &&
                                x.getBirthdate().equals(birthdate)
                        ));
    }

    @Test
    public void mustCallStorageWhenGettingEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        var id = 1;
        // Act

        Employee e = employeeService.getEmployeeById(id);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .getEmployeeWithId(
                        intThat(x -> x.equals(id)));
    }
}
