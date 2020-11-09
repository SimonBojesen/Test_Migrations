package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.ConnectionString;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.BookingCreation;
import dto.Customer;
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
public class CreateAndLoadBookingTest {
    private BookingStorage bookingStorage;
    private int employeeId;
    private int customerId;

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

        var connection = new ConnectionString(url+db, "root", "testuser123");

        bookingStorage = new BookingStorageImpl(connection);

        var numBookings = bookingStorage.getBookings().size();

        CustomerStorage c = new CustomerStorageImpl(connection);
        EmployeeStorage e = new EmployeeStorageImpl(connection);
        employeeId = e.createEmployee(new EmployeeCreation("test", "testesen",new Date()));
        customerId = c.createCustomer(new CustomerCreation("test","testesten2",12345678));
        if (numBookings < 100) {
            addFakeBookings(100 - numBookings,connection);
        }
    };

    private void addFakeBookings(int numBookings,ConnectionString connection) throws SQLException {
        Faker faker = new Faker();

        for (int i = 0; i < numBookings; i++) {


            BookingCreation b = new BookingCreation(customerId,employeeId,faker.date().birthday(),"start","slut");
            bookingStorage.createBooking(b);
        }
    }

    @Test
    private void mustSaveBookingInDatabaseWhenCallingCreateBooking() throws SQLException, ParseException {
        // Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse("2020-05-05");
        // Act
        var id = bookingStorage.createBooking(new BookingCreation(1, 1, now, "start", "end"));

        // Assert
        var bookings = bookingStorage.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        x.getId() == id &&
                        x.getCustomer().getId() == (1) &&
                        x.getEmployee().getId() == (1) &&
                        x.getDate().equals(now) &&
                        x.getStart().equals("start") &&
                        x.getEnd().equals("end")
                )
        );
    }

    @Test
    private void mustReturnLatestId() throws SQLException, ParseException {
        // Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse("2021-12-08");
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(customerId,employeeId,now,"start1","slut1"));
        var id2 = bookingStorage.createBooking(new BookingCreation(customerId,employeeId,now,"start2","slut2"));
        // Assert
        assertEquals(1, id2 - id1);
    }
}
