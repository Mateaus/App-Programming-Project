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

    public boolean isLogin(String email, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from students where email = ? and password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch(Exception e) {

            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
