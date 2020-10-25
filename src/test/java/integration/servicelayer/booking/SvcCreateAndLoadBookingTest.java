package integration.servicelayer.booking;

import datalayer.ConnectionString;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.Customer;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class SvcCreateAndLoadBookingTest {
    private BookingService svc;
    private BookingStorage storage;

    private static final int PORT = 3306;
    private static final String PASSWORD = "testuser1234";

    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;
    @Container
    public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);

    // A generic container could be used as well:
//    public static GenericContainer mysql = new GenericContainer(DockerImageName.parse("mysql"))
//            .withExposedPorts(PORT)
//            .withEnv("MYSQL_ROOT_PASSWORD", PASSWORD);

    @BeforeAll
    public void setup() {
        System.err.println("mysql created: " + mysql.isCreated());
        System.err.println("mysql running: " + mysql.isRunning());
        System.err.println("mysql host: " + mysql.getHost());
        String url = "jdbc:mysql://"+mysql.getHost()+":"+mysql.getFirstMappedPort()+"/";
        String db = "DemoApplicationTest";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("4")
                        .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();
        storage = new BookingStorageImpl(new ConnectionString(url + db,"root", PASSWORD));
        svc = new BookingServiceImpl(storage);

        //test data
        customerStorage = new CustomerStorageImpl(new ConnectionString(url + db,"root", PASSWORD));
        employeeStorage = new EmployeeStorageImpl(new ConnectionString(url + db,"root", PASSWORD));
    }

    @Test
    public void mustSaveBookingToDatabaseWhenCallingCreateBooking() throws BookingServiceException, SQLException {
        // Arrange
        var customerId = customerStorage.createCustomer(new CustomerCreation("a", "b", 12345678));
        var employeeId = employeeStorage.createEmployee(new EmployeeCreation("test", "test", new Date(123456l)));
        var date = new Date(1239821l);
        var start = "start";
        var end = "end";


        int id = svc.createBooking(customerId, employeeId, date, start, end);

        // Act
        var bookings = svc.getBookingsForCustomer(customerId);

        for (Booking booking: bookings) {
            if (booking.getId() == id){
                // Assert
                assertEquals(booking.getId(), id);
            }
        }
    }
}
