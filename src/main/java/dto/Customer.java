package dto;

public class Customer {
    private final int id;
    private final String firstname, lastname;
    private final int phonenumber;
    public Customer(int id, String firstname, String lastname, int phonenumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getPhonenumber() { return phonenumber; }
}
