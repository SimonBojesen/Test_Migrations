package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.SQLException;
import java.util.Collection;

public interface BookingStorage {
    public int createBooking(BookingCreation booking) throws SQLException;
    public Collection<Booking> getBookings() throws SQLException;
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;
}
