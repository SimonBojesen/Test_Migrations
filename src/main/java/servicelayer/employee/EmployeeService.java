package servicelayer.employee;

import dto.Employee;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;
    Employee getEmployeeById(int id) throws EmployeeServiceException;
}
