package servicelayer.employee;

import dto.Employee;

public class EmployeeServiceException extends Exception{
    public EmployeeServiceException(String e) {
        super(e);
    }
}
