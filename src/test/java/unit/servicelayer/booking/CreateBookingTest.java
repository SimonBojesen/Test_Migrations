package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.employee.EmployeeStorage;
import dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateBookingTest {
    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;


    @BeforeEach
    public void beforeEach(){
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);
    }

    @Test
    private void mustCallStorageWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        var customerId = 1;
        var employeeId = 1;
        var date = new Date(123456789l);
        var start = "start";
        var end = "end";
        // Act

        bookingService.createBooking(customerId, employeeId, date, start, end);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock)
                .createBooking(
                        argThat(x -> x.getCustomerId() == customerId &&
                                x.getEmployeeId() == employeeId &&
                                x.getDate().equals(date) &&
                                x.getStart().equals(start) &&
                                x.getEnd().equals(end)
                        ));
    }

    @Test
    private void mustCallStorageWhenGettingCustomerBookings() throws BookingServiceException, SQLException {
        // Arrange
        var customerId = 1;
        // Act

        bookingService.getBookingsForCustomer(customerId);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .getBookingsForCustomer(
                        intThat(x -> x.equals(customerId)));
    }

    @Test
    private void mustCallStorageWhenGettingBookings() throws BookingServiceException, SQLException {
        // Arrange
        // Act

        bookingService.getAllBookings();

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1)).getBookings();
    }
}
