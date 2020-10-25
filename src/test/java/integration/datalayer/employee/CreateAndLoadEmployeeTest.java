package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.ConnectionString;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class CreateAndLoadEmployeeTest {
    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("4")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();


        employeeStorage = new EmployeeStorageImpl(new ConnectionString(url+db, "root", "testuser123"));

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }

    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), faker.date().birthday());
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException, ParseException {
        // Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse("2020-05-05");
        // Act
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn", now));

        // Assert
        var employees = employeeStorage.getEmployees();
        assertTrue(
                employees.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                                x.getLastname().equals("Carlssonn") &&
                                x.getBirthdate().equals(now)));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = employeeStorage.createEmployee(new EmployeeCreation("a", "b", new Date()));
        var id2 = employeeStorage.createEmployee(new EmployeeCreation("c", "d", new Date()));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
