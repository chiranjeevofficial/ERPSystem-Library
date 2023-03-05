import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
/*import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;**/

public class Login extends JFrame implements ActionListener {
    JLabel collegeLogo = new JLabel(new ImageIcon("C:\\images\\collegeLogoEdit.jpg"));
    private final JTextField usernameTextField = new JTextField(20);
    private final JPasswordField passwordTextField = new JPasswordField(20);
    private final JLabel messageLabel = new JLabel("");
    private final JButton loginButton = new JButton("Login");

    public Login(){ //default constructor
        JFrame mainFrame = new JFrame("Admin login");
        mainFrame.setBounds(300,50,650,400);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        JPanel mainPanel = new JPanel(null);
        mainFrame.setContentPane(mainPanel);
        collegeLogo.setBounds(250,5,150,150);
        JLabel collegeName = new JLabel("Swami Purnanand Degree College of Technical Education");
        collegeName.setBounds(10,160,620,FixConstant.labelHeight);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(FixConstant.loginLabelMarginX,210,FixConstant.labelWidth,FixConstant.labelHeight);
        usernameTextField.setBounds(FixConstant.loginTextFieldMarginX,210,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(FixConstant.loginLabelMarginX,250,FixConstant.labelWidth,FixConstant.labelHeight);
        passwordTextField.setBounds(FixConstant.loginTextFieldMarginX,250,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        messageLabel.setBounds(FixConstant.loginLabelMarginX+50,285,FixConstant.labelWidth+110,FixConstant.labelHeight-10);
        loginButton.setBounds(FixConstant.labelMarginX+230,315,FixConstant.buttonWidth,FixConstant.buttonHeight);

        /*messageLabel.setBackground(Color.yellow);
        messageLabel.setOpaque(true);**/

        mainPanel.add(collegeLogo);
        mainPanel.add(collegeName);

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTextField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordTextField);
        mainPanel.add(messageLabel);
        mainPanel.add(loginButton);

        collegeName.setFont(FixConstant.titleFont);
        usernameLabel.setFont(FixConstant.headFont);
        passwordLabel.setFont(FixConstant.headFont);
        messageLabel.setFont(FixConstant.textFont);
        messageLabel.setForeground(Color.red);
        loginButton.setFont(FixConstant.textFont);

        loginButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean accountVerification = false;
        if(e.getSource() == loginButton){
            String query = "select * from account";
            try {
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement(query);
                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    String fetchUsername = result.getString("username");
                    String fetchPassword = result.getString("password");
                    if (fetchUsername.equals(usernameTextField.getText()) && fetchPassword.equals(String.valueOf(passwordTextField.getPassword())))
                        accountVerification = true;
                }
                if(accountVerification) {
                    messageLabel.setText("username and password is correct");
                    messageLabel.setForeground(Color.green);
                    dispose();
                    new AddBook();
                }
                else {
                    messageLabel.setText("* incorrect username or password");
                    messageLabel.setForeground(Color.red);
                }
            }catch (SQLException sql) {
                System.out.println(sql.getMessage());
            }
        }
        DatabaseConnection.releaseDatabaseConnection();
    }
}
/*
    try {
                // Load the MySQL Connector/J driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a connection String                 connectionString = "jdbc:mysql://localhost:3306/library?user=root&password=Chiranjeev@1805";

                // Establish a connection to the database
                Connection con = DriverManager.getConnection(connectionString);

                // Create a statement
                Statement statement = connection.createStatement();

                // Execute a query
                ResultSet resultSet = statement.executeQuery("SELECT * FROM account");

                // Process the results
                while (resultSet.next()) {
                    String fetchUsername = resultSet.getString("username");
                    String fetchPassword = resultSet.getString("password");
                    //System.out.println(column1 + "\t" + column2);
                    if(fetchUsername.equals(usernameTextField.getText()) && fetchPassword.equals(String.valueOf(passwordTextField.getPassword())))
                        accountVerification=true;
                }
                if(accountVerification) {
                    messageLabel.setText("username and password is correct");
                    messageLabel.setForeground(Color.CYAN);
                    dispose();
                    new AddBook();
                }
                else {
                    messageLabel.setText("* incorrect username or password");
                    messageLabel.setForeground(Color.red);
                }

                // Close the connection
                connection.close();
            } catch (Exception ie) {
                System.err.println("Error: " + ie.getMessage());
**/