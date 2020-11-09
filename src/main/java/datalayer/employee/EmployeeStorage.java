package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeStorage {
    int createEmployee(EmployeeCreation employee) throws SQLException;
    List<Employee> getEmployees() throws SQLException;
    Employee getEmployeeWithId(int employeeId) throws SQLException;
}
