import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection con = null;
    public static Connection getDatabaseConnection(){
        String url = "jdbc:mysql://localhost:3306/library";
        try {
            con = DriverManager.getConnection(url, "root", "Chiranjeev@1805");
            if(con!=null)
                System.out.println("Connection Established");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return con;
    }
    public static void releaseDatabaseConnection(){
        try {
            con.close();
            if(con==null)
                System.out.println("Connection Released");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
