package fr.choukas.azuria.api.data.sql;

public class DatabaseCredentials {

    private String host;
    private String username;
    private String password;
    private int port;
    private String dbName;

    public DatabaseCredentials(String host, String username, String password, int port, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.dbName = dbName;
    }

    public DatabaseCredentials() {}

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String toURI() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName + "?useSSL=false&allowPublicKeyRetrieval=true";
    }
}
