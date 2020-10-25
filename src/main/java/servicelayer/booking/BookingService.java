package servicelayer.booking;

import dto.Booking;

import java.util.Collection;
import java.util.Date;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, String start, String end) throws BookingServiceException;
    Collection<Booking> getAllBookings() throws BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException;
}
