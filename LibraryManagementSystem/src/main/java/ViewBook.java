import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewBook {
    //components objects
    public ViewBook(){
        JFrame mainFrame = new JFrame("View Book");
        JPanel mainPanel = new JPanel(null);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setSize(1000,500);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        Dimension size = new Dimension(mainFrame.getWidth(), mainFrame.getHeight());
        table.setPreferredScrollableViewportSize(size);

        mainFrame.add(scrollPane);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Publisher");
        tableModel.addColumn("Edition");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Serial Number");
        try{
            String query = "select * from book;";
            PreparedStatement statement = DatabaseConnection.getDatabaseConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String[] data ={result.getString("title"),
                            result.getString("author"),
                            result.getString("publisher"),
                            result.getString("edition"),
                            String.valueOf(result.getInt("quantity")),
                            String.valueOf(result.getInt("serialnumber"))};
                tableModel.addRow(data);
            }
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }
}
