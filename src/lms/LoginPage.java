package lms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JPanel implements ActionListener, KeyListener {
    private final Connection con;
    private final JFrame mainFrame = new JFrame("Home Page");
    private JLabel passwordLabel;
    private JLabel verificationMessageLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    LoginPage() {
        con = Util.getConnectionWithMySQL("library","root","admin@2023");
        setLeftPanel();
        setRightPanel();
        setMainFrame();
    }

    void setLeftPanel() {
        int leftPanelWidth = 300, leftPanelHeight = 500;

        JLabel imageIconLabel = Util.getImageOnLabel("C:\\icon\\user.png",200),
               adminPanelLabel = new JLabel("Admin Panel");

        JPanel leftPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Util.orangeColor, 135, getHeight(), Util.whiteColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        leftPanel.setSize(leftPanelWidth,leftPanelHeight);

        Util.setLabelOnCenter(imageIconLabel,leftPanelWidth,300);
        Util.setLabelOnCenter(adminPanelLabel,0,250,leftPanelWidth,100);

        Util.labelTextDecoration(adminPanelLabel,false);

        leftPanel.add(imageIconLabel);
        leftPanel.add(adminPanelLabel);
        mainFrame.add(leftPanel);
    }

    void setRightPanel() {
        JPanel rightPanel = new JPanel(null);
        JLabel companyLogo = Util.getImageOnLabel("C:\\icon\\apple.png", 70);
        JLabel companyName = new JLabel("Apple Inc. Pvt Limited");
        JLabel usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameTextField = new JTextField(50);
        passwordField = new JPasswordField(50);
        loginButton = new JButton("Login");
        verificationMessageLabel = new JLabel("");

        bornStageForm();

        Util.labelTextDecoration(usernameLabel);
        Util.labelTextDecoration(passwordLabel);
        Util.headerTextDecoration(companyName);
        Util.textFieldDecoration(usernameTextField);
        Util.textFieldDecoration(passwordField);
        Util.loginButtonDecoration(loginButton);
        Util.verificationLabelDecoration(verificationMessageLabel);

        Util.setLabelOnCenter(companyLogo,600,80);
        Util.setLabelOnCenter(companyName,0,80,600,30);


        loginButton.addActionListener(this);
        usernameTextField.addKeyListener(this);

        rightPanel.setBounds(300,0,600,500);

        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        usernameLabel.setBounds(Util.labelHeaderX,Util.labelHeaderY,Util.labelHeaderWidth,Util.labelHeaderHeight);
        usernameTextField.setBounds(Util.textFieldX,Util.labelHeaderY+8,Util.textFieldWidth,Util.textFieldHeight);

        passwordLabel.setBounds(Util.labelHeaderX,Util.labelHeaderY*2-50,Util.labelHeaderWidth,Util.labelHeaderHeight);
        passwordField.setBounds(Util.textFieldX,Util.labelHeaderY*2-50+8,Util.passwordFieldWidth,Util.textFieldHeight);

        /*MyFont.setLabelHighlighted(loginButton,Color.YELLOW);
        MyFont.setLabelOnCenter(loginButton,0,300,(int)loginButton.getPreferredSize().getWidth()+20,(int)loginButton.getPreferredSize().getHeight()+10);**/

        loginButton.setBounds(passwordLabel.getX()+150,passwordLabel.getY()+80,200,40);

        rightPanel.add(companyLogo);
        rightPanel.add(companyName);
        rightPanel.add(usernameLabel);
        rightPanel.add(passwordLabel);
        rightPanel.add(passwordField);
        rightPanel.add(usernameTextField);
        rightPanel.add(loginButton);
        rightPanel.add(verificationMessageLabel);
        mainFrame.add(rightPanel);
    }
    
    void setMainFrame() {
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setSize(900,530);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
    }

    void clearForm() {
        usernameTextField.setText("");
        passwordField.setText("");
    }

    void bornStageForm(){
        passwordLabel.setEnabled(false);
        passwordField.setEnabled(false);
        loginButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            boolean validate = false;
            try {
                PreparedStatement prepareStatement = con.prepareStatement("SELECT * from account");
                ResultSet resultSet = prepareStatement.executeQuery();
                while (resultSet.next()) {
                    String fetchUsername = resultSet.getString("username");
                    String fetchPassword = resultSet.getString("password");
                    if (fetchUsername.equals(usernameTextField.getText()) && fetchPassword.equals(String.valueOf(passwordField.getPassword()))) {
                        validate = true;
                        clearForm();
                        mainFrame.dispose();
                        new HomePage();
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
            if (!validate) {
                verificationMessageLabel.setText(" * Incorrect username or password");
                verificationMessageLabel.setForeground(Color.RED);
                clearForm();
                bornStageForm();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == usernameTextField) {
            passwordField.setEnabled(usernameTextField.getText().length() > 2);
            passwordLabel.setEnabled(usernameTextField.getText().length() > 2);
            loginButton.setEnabled(usernameTextField.getText().length() > 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

