import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnector {

    private Connection conn;

    public Connection connect() {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "Chiranjeev@1805";
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Connected to database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection failed");
        }
        
        return conn;
    }

    public void disconnect() {
        try {
            conn.close();
            JOptionPane.showMessageDialog(null, "Disconnected from database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection");
        }
    }
    public static void main(String[] args){
        DatabaseConnector connector = new DatabaseConnector();
        Connection conn = connector.connect();
        // Use the connection...
        connector.disconnect();    }
}
