package servicelayer.employee;

import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.CustomerCreation;
import dto.Employee;
import dto.EmployeeCreation;
import servicelayer.customer.CustomerServiceException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName, birthdate));
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeServiceException {
        try {
            return employeeStorage.getEmployeeWithId(id);
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }
}
