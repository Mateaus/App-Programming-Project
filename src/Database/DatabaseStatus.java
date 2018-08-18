package Database;

import java.sql.*;

public class DatabaseStatus {
    Connection connection;
    public DatabaseStatus() {
        connection = SqliteConnection.Connector();
        if(connection == null) {
            System.out.printf("Connection null, leaving program\n");
            System.exit(1);
        }
    }

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
