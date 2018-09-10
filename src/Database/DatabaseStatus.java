package Database;



//import com.sun.xml.internal.ws.util.StringUtils;

import java.sql.*;

public class DatabaseStatus {

    // Connect to the student.db database
    Connection connection;

    /*
     * Checks if the project is connected to the DataBase correctly,
     * else, it exits the project.
     */

    public DatabaseStatus() {
        connection = SqliteConnection.Connector();
        if(connection == null) {
            System.out.printf("Connection null, leaving program\n");
            System.exit(1);
        }
    }

    /*
     * Checks if DataBase is connected or not.
     * Returns boolean;
     */

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the student whose table is equals to the email and password
     * @param email
     * @param pass
     */

    public boolean isLogin(String email, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM students WHERE email = ? AND password = ?";
        //System.out.printf("%s\n", query);
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

    /**
     * Get the student whose student_name is equals to the email and password
     * @param email
     * @param pass
     */

    public String searchName(String email, String pass) throws SQLException {
        String studentName = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT student_name FROM students WHERE email = ? AND password = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            // set the string parameters for the DB from the string passed as parameters through the function
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                //System.out.println(resultSet.getString("student_name"));
                studentName = resultSet.getString("student_name");
            }
            return studentName;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Query not found";
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    /**
     * Insert a new row into the students table
     *
     * @param name
     * @param email
     * @param pass
     */

    public void registerAccount(String name, String email, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO students(student_name, email, password) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, pass);
            preparedStatement.executeUpdate();
            return;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return;
        } finally {
            preparedStatement.close();
        }
    }
}
