package datalayer.booking;

import datalayer.ConnectionString;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.Customer;
import dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingStorageImpl implements BookingStorage{
    ConnectionString connection;

    public BookingStorageImpl(ConnectionString connection){
        this.connection = connection;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connection.getConnectionString(), connection.getUsername(), connection.getPassword());
    }

    /*this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.start = start;
        this.end = end;

     */


    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingToCreate.getCustomerId());
            stmt.setInt(2, bookingToCreate.getEmployeeId());
            stmt.setDate(3, new Date(bookingToCreate.getDate().getTime()));
            stmt.setString(4, bookingToCreate.getStart());
            stmt.setString(5, bookingToCreate.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }

    public List<Booking> getBookings() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {

            ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings");

            CustomerStorage cs = new CustomerStorageImpl(connection);
            EmployeeStorage es = new EmployeeStorageImpl(connection);

            List<Booking> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Customer customer = cs.getCustomerWithId(resultSet.getInt("customerId"));
                Employee employee = es.getEmployeeWithId(resultSet.getInt("employeeId"));
                Date date = resultSet.getDate("date");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                list.add(new Booking(id, customer, employee, date, start, end));
            }

            return list;
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var sql = "select id, employeeid, date, start, end from Bookings where customerId = ?";

        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);

            try (var resultSet = stmt.executeQuery()) {
                List<Booking> bookings = new ArrayList<>();
                CustomerStorage cs = new CustomerStorageImpl(connection);
                EmployeeStorage es = new EmployeeStorageImpl(connection);
                Customer customer = cs.getCustomerWithId(customerId);
                while(resultSet.next()){
                    var id = resultSet.getInt("ID");
                    Employee employee = es.getEmployeeWithId(resultSet.getInt("employeeId"));
                    var date = resultSet.getDate("date");
                    var start = resultSet.getString("start");
                    var end = resultSet.getString("end");
                    bookings.add(new Booking(id, customer, employee, date, start, end));
                }
                return bookings;
            }
        }
    }
}
