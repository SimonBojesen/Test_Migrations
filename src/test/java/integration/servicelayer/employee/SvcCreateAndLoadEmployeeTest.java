package integration.servicelayer.employee;

import datalayer.ConnectionString;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class SvcCreateAndLoadEmployeeTest {
    private EmployeeService svc;
    private EmployeeStorage storage;

    private static final int PORT = 3306;
    private static final String PASSWORD = "testuser1234";

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

        storage = new EmployeeStorageImpl(new ConnectionString(url + db,"root", PASSWORD));
        svc = new EmployeeServiceImpl(storage);
    }

    @Test
    public void mustSaveEmployeeToDatabaseWhenCallingCreateEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        int id = svc.createEmployee(firstName, lastName, bday);

        // Act
        var createdEmployee = svc.getEmployeeById(id);

        // Assert
        assertEquals(firstName, createdEmployee.getFirstname());
        assertEquals(lastName, createdEmployee.getLastname());
    }
}
