import java.sql.*;

public class ConnectMySQL {
    public static void main(String[] args) {
        try {
            // Load the MySQL Connector/J driver
            Class.forName("com.mysql.jdbc.Driver");

            // Create a connection string
            String connectionString = "jdbc:mysql://localhost:3306/library?user=root&password=Chiranjeev@1805";

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(connectionString);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");

            // Process the results
            while (resultSet.next()) {
                String column1 = resultSet.getString("column1");
                Strin column2 = resultSet.getString("column2");
                System.out.println(column1 + "\t" + column2);
            }

            // Close the connection
            connection.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
