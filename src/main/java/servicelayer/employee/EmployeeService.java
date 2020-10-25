package servicelayer.employee;

import dto.Customer;
import dto.Employee;
import servicelayer.customer.CustomerServiceException;

import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;
    Employee getEmployeeById(int id) throws EmployeeServiceException;
}
