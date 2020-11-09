package dto;

public class CustomerCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getPhonenumber() { return phonenumber; }

    private final String firstname, lastname;
    private final int phonenumber;

    public CustomerCreation(String firstname, String lastname, int phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }
}
