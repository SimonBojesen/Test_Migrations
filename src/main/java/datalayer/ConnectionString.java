package datalayer;

public class ConnectionString {

    private String connectionString;
    private String username;
    private String password;

    public ConnectionString() {
        this.connectionString = "jdbc:mysql://localhost:3307/DemoApplicationTest";
        this.username = "root";
        this.password = "testuser123";
    }

    public ConnectionString(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
