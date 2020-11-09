package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
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
            throwables.printStackTrace();
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeServiceException {
        try {
            return employeeStorage.getEmployeeWithId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }
}
