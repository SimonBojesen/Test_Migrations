package datalayer;

public class ConnectionString {

    private String connectionUrl;
    private String username;
    private String password;

    public ConnectionString() {
        this.connectionUrl = "jdbc:mysql://localhost:3307/DemoApplicationTest";
        this.username = "root";
        this.password = "testuser123";
    }

    public ConnectionString(String connectionUrl, String username, String password) {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
