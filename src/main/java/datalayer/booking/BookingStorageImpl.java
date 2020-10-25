package datalayer.booking;

import dto.Booking;

import java.sql.SQLException;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage{

    @Override
    public int createBooking(Booking booking) throws SQLException {
        return 0;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        return null;
    }
}
