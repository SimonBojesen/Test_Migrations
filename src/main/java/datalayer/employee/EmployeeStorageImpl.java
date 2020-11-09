package datalayer.employee;

import datalayer.ConnectionString;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeStorageImpl implements EmployeeStorage{
    private ConnectionString connection;

    public EmployeeStorageImpl(ConnectionString connection){
        this.connection = connection;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connection.getConnectionUrl(), connection.getUsername(), connection.getPassword());
    }

    @Override
    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException {
        var sql = "insert into Employees(firstname, lastname, birthdate) values (?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employeeToCreate.getFirstname());
            stmt.setString(2, employeeToCreate.getLastname());
            stmt.setDate(3, new Date(employeeToCreate.getBirthdate().getTime()));

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Employee>();

            ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, birthdate from Employees");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                Date birthdate = resultSet.getDate("birthdate");
                Employee e = new Employee(id, firstname, lastname, birthdate);
                results.add(e);
            }

            return results;
        }
    }

    @Override
    public Employee getEmployeeWithId(int employeeId) throws SQLException{
        var sql = "select ID, firstname, lastname, birthdate from Employees where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var birthdate = resultSet.getDate("birthdate");
                    return new Employee(id, firstname, lastname, birthdate);
                }
                return null;
            }
        }
    }
}
