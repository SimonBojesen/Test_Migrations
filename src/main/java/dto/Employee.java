package dto;

import java.util.Date;

public class Employee {
    private final int id;
    private final int firstname;
    private final int lastname;
    private final Date birthdate;

    public Employee(int id, int firstname, int lastname, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public int getFirstname() {
        return firstname;
    }

    public int getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
