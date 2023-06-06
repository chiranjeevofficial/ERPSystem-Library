package inventrymanagementsystem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login implements ActionListener {

    JFrame mainFrame;
    JLabel[] loginJLabels;
    JTextField usernameJTextField;
    JPasswordField passwordJField;
    JButton[] loginJButtons;
    Connection con = null;

    String[] labelString = {
        "Icon",
        "Namaste Coder, I am Title of this Frame",
        "Username",
        "Password",
        ""
    };

    public Login() {
        String url = "jdbc:mysql://localhost:3306/inventory",
                username = "root",
                password = "admin@2023";
        try {
            con = DriverManager.getConnection(url,username,password);
            if (con != null)
                System.out.println("Connection Established");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        mainFrame = new JFrame("Login Page - Inventory Management System");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        initLoginPage();
        mainFrame.repaint();
    }

    public void initLoginPage() {
        loginJLabels = new JLabel[5];
        int x = 10, y = 10;
        for (int i = 0; i < loginJLabels.length; i++) {
            loginJLabels[i] = new JLabel(labelString[i]);
            if (i == 0) {
                loginJLabels[i].setBounds(x + 390, y, 100, 100);
            }

            if (i == 1) {
                loginJLabels[i].setBounds(x + 190, y + 100, 400, 30);
                y = 160;
            }

            if (i > 1 && i != 4) {
                loginJLabels[i].setBounds(x + 50, y, 170, 30);
                y += 60; // y = y + 40;
            }

            if (i == 4) {
                loginJLabels[4].setBounds(270, 320, 250, 30);
            }

            loginJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 25));

            mainFrame.add(loginJLabels[i]);
        }
        usernameJTextField = new JTextField();
        usernameJTextField.setBounds(300, 160, 300, 40);
        mainFrame.add(usernameJTextField);

        passwordJField = new JPasswordField();
        passwordJField.setBounds(300, 220, 300, 40);

        mainFrame.add(passwordJField);

        loginJButtons = new JButton[2];

        loginJButtons[0] = new JButton("Login");
        loginJButtons[1] = new JButton("Clear");

        loginJButtons[0].setBounds(150, 380, 100, 40);
        loginJButtons[1].setBounds(450, 380, 100, 40);

        loginJButtons[0].setFont(new Font("Corbel", Font.BOLD, 20));
        loginJButtons[1].setFont(new Font("Corbel", Font.BOLD, 20));
        
        usernameJTextField.setFont(new Font("Corbel", Font.BOLD, 20));
        passwordJField.setFont(new Font("Corbel", Font.BOLD, 20));

        loginJButtons[0].addActionListener(this);
        loginJButtons[1].addActionListener(this);

        mainFrame.add(loginJButtons[0]);
        mainFrame.add(loginJButtons[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginJButtons[0]) {
            String username = usernameJTextField.getText();
            String password = String.valueOf(passwordJField.getPassword());
            usernameJTextField.setText("");
            passwordJField.setText("");
            
            String query = "select * from account";
            boolean validate = false;
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    if (rs.getString("username").equals(username) && rs.getString("password").equals(password))
                        validate = true;
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
            if (validate) {
                new Demo();
                mainFrame.dispose();
            }
            else
                loginJLabels[4].setText("Login Failed");
        }
        if (e.getSource() == loginJButtons[1]) {
            usernameJTextField.setText("");
            passwordJField.setText("");
            loginJLabels[4].setText("");
        }
    }

}
