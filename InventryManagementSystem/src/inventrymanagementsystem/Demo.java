package inventrymanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Demo implements ActionListener {

    //variable declaration
    //final int 
    static int rightPanelIndex = 0;
    JFrame frame;
    JPanel leftPanel, topPanel, rightPanel, bottomPanel;
    JButton[] leftJButtons, topJButtons, CustomerJButtons, SellerJButtons;
    JButton SettingJButton, ProductEditJButton;
    JLabel[] SellerJLabels, CustomerJLabels, ProductJLabels, SettingJLabels, SellerEditJLabels, CustomerEditJLabels, ProductEditJLabels;
    JTextField[] SellerJTextField, CustomerJTextField, ProductJTextField, SettingJTextField, SellerEditJTextField, CustomerEditJTextField, ProductEditJTextField;
    JTextArea CustomerJTextArea, ProductJTextArea;
    JTable ProductJTable;
    JTextArea[] SellerJTextArea;

    String[] sellerlabelString = {
        "Seller iD",
        "Firstname",
        "Middlename",
        "Lastname",
        "Address",
        "Company name",
        "Company phno.",
        "Mobile no.",
        "Email address",
        "Company Email",
        "Company Address"
    };
    String[] customerlabelString = {
        "Customer id",
        "Firstname",
        "Middlename",
        "Lastname",
        "Address",
        "Mobile no.",
        "Email address",
        "Date of Birth"
    };
    String[] productlabelString = {
        "Product id",
        "Product name",
        "Description",
        "Brand name",
        "Unit of mesurment"
    };
    String[] productEditlabelString = {
        "Product id",
        "Product name",
        "Brand name",
        "Description",
        "Group",
        "Units "
    };
    Object[][] Data = {};
    String[] ColumnNames = {"Product Name", "Size", "color"};

    String[] settinglabelString = {
        "User id",
        "User name",
        "Password",
        "Mobile no.",
        "Voter no."
    };

    public Demo() {
        initMainFrame();
        initRightPanel();
        initLeftPanel();
        initTopPanel();
        initBottomPanel();
        frame.revalidate();
        frame.repaint();
    }

    public void initMainFrame() {
        frame = new JFrame("Inventry Management System");
        frame.setSize(1200, 740);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void initRightPanel() {
        rightPanel = new JPanel(null);
        rightPanel.setBounds(200, 100, 985, 500);
        rightPanel.setBackground(new Color(127, 45, 67));
        frame.add(rightPanel);
    }

    public void initLeftPanel() {
        String[] buttonString = {
            "Seller",
            "Customer",
            "Invoice",
            "Product",
            "Return's",
            "Setting",
            "Logout"
        };
        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 190, 700);
        leftPanel.setBackground(new Color(23, 109, 66));
        leftJButtons = new JButton[7];
        int yAxis = 30;
        for (int i = 0; i < leftJButtons.length; i++) {
            leftJButtons[i] = new JButton(buttonString[i]);
            leftJButtons[i].setBounds(20, yAxis, 140, 40);
            leftJButtons[i].setFont(new Font("Segoe UI", Font.BOLD, 15));
            leftJButtons[i].addActionListener(this);
            leftPanel.add(leftJButtons[i]);
            yAxis += 100; // yAxis = yAxis + 100;
        }
        frame.add(leftPanel);
    }

    public void initTopPanel() {
        String[] buttonString = {
            "Add",
            "View",
            "Edit",
            "Delete"
        };
        topPanel = new JPanel(null);
        topPanel.setBounds(200, 0, 985, 90);
        topPanel.setBackground(new Color(65, 195, 20));
        topJButtons = new JButton[4];
        int xAxis = 30;
        for (int i = 0; i < topJButtons.length; i++) {
            topJButtons[i] = new JButton(buttonString[i]);
            topJButtons[i].setBounds(xAxis, 25, 80, 40);
            topJButtons[i].setFont(new Font("Segoe UI", Font.BOLD, 15));
            topJButtons[i].addActionListener(this);
            topPanel.add(topJButtons[i]);
            xAxis += 280;
        }
        frame.add(topPanel);
    }

    public void initBottomPanel() {
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(200, 610, 985, 90);
        bottomPanel.setBackground(new Color(30, 50, 155));

        frame.add(bottomPanel);
    }

    public void initSellerPanel() {
        SellerJLabels = new JLabel[11];
        int x = 20, y = 20;
        for (int i = 0; i < SellerJLabels.length; i++) {
            SellerJLabels[i] = new JLabel(sellerlabelString[i]);
            if (i == 0) {
                SellerJLabels[i].setBounds(x, y + 10, 120, 50);
            }

            if (i == 1) {
                SellerJLabels[i].setBounds(x, y + 50, 120, 50);
            }
            if (i == 2) {
                SellerJLabels[i].setBounds(x, y + 90, 120, 50);
            }

            if (i == 3) {
                SellerJLabels[i].setBounds(x, y + 130, 120, 50);
            }
            if (i == 4) {
                SellerJLabels[i].setBounds(x, y + 170, 120, 50);
            }

            if (i == 5) {
                SellerJLabels[i].setBounds(x, y + 250, 130, 50);
            }

            if (i > 5) {
                SellerJLabels[i].setBounds(x + 400, y + 5, 160, 50);
                y += 40;
            }

            SellerJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
            rightPanel.add(SellerJLabels[i]);

        }
        SellerJTextField = new JTextField[11];
        int a = 20, b = 20;
        for (int j = 0; j < SellerJTextField.length; j++) {
            SellerJTextField[j] = new JTextField();

            if (j == 0) {
                SellerJTextField[j].setBounds(a + 150, b + 25, 130, 25);
            }

            if (j == 1) {
                SellerJTextField[j].setBounds(a + 150, b + 65, 130, 25);
            }
            if (j == 2) {
                SellerJTextField[j].setBounds(a + 150, b + 105, 130, 25);
            }

            if (j == 3) {
                SellerJTextField[j].setBounds(a + 150, b + 145, 130, 25);
            }
            if (j == 5) {
                SellerJTextField[j].setBounds(a + 150, b + 265, 130, 25);
            }
            if (j == 6) {
                SellerJTextField[j].setBounds(a + 600, b + 20, 130, 25);
            }
            if (j == 7) {
                SellerJTextField[j].setBounds(a + 600, b + 60, 130, 25);
            }
            if (j == 8) {
                SellerJTextField[j].setBounds(a + 600, b + 95, 130, 25);
            }
            if (j == 9) {
                SellerJTextField[j].setBounds(a + 600, b + 135, 130, 25);
                b += 40;
            }

            rightPanel.add(SellerJTextField[j]);
        }

        SellerJTextArea = new JTextArea[2];
        {
            SellerJTextArea[0] = new JTextArea("");
            SellerJTextArea[0].setBounds(170, 200, 130, 70);
            SellerJTextArea[0].setFont(new Font("Corbel", Font.BOLD, 20));
            rightPanel.add(SellerJTextArea[0]);
            SellerJTextArea[1] = new JTextArea("");
            SellerJTextArea[1].setBounds(620, 200, 130, 80);
            SellerJTextArea[1].setFont(new Font("Corbel", Font.BOLD, 20));
            //rightPanel.add(SellerJTextArea[0]);
            rightPanel.add(SellerJTextArea[1]);
        }
        SellerJButtons = new JButton[2];
        {
            SellerJButtons[0] = new JButton("Save");
            SellerJButtons[1] = new JButton("New");

            SellerJButtons[0].setBounds(450, 400, 100, 40);
            SellerJButtons[1].setBounds(750, 400, 100, 40);

            SellerJButtons[0].setFont(new Font("Corbel", Font.BOLD, 20));
            SellerJButtons[1].setFont(new Font("Corbel", Font.BOLD, 20));

            SellerJButtons[0].addActionListener(this);
            SellerJButtons[1].addActionListener(this);

            rightPanel.add(SellerJButtons[0]);
            rightPanel.add(SellerJButtons[1]);

        }

    }

    public void initCustomerPanel() {
        CustomerJLabels = new JLabel[8];
        int x = 20, y = 20;
        for (int i = 0; i < CustomerJLabels.length; i++) {
            CustomerJLabels[i] = new JLabel(customerlabelString[i]);
            if (i == 0) {
                CustomerJLabels[i].setBounds(x + 40, y + 10, 120, 50);
            }

            if (i == 1) {
                CustomerJLabels[i].setBounds(x + 40, y + 60, 120, 50);
            }
            if (i == 2) {
                CustomerJLabels[i].setBounds(x + 40, y + 110, 120, 50);
            }

            if (i == 3) {
                CustomerJLabels[i].setBounds(x + 40, y + 160, 120, 50);
            }
            if (i == 4) {
                CustomerJLabels[i].setBounds(x + 40, y + 210, 120, 50);
            }
            if (i > 4) {
                CustomerJLabels[i].setBounds(x + 500, y + 5, 150, 50);
                y += 50;
            }

            CustomerJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 22));
            rightPanel.add(CustomerJLabels[i]);
        }

        CustomerJTextField = new JTextField[6];
        int a = 20, b = 20;
        for (int j = 0; j < CustomerJTextField.length; j++) {
            CustomerJTextField[j] = new JTextField();
            if (j == 0) {
                CustomerJTextField[j].setBounds(a + 170, b + 25, 150, 25);
            }
            if (j == 1) {
                CustomerJTextField[j].setBounds(a + 170, b + 75, 150, 25);
            }
            if (j == 2) {
                CustomerJTextField[j].setBounds(a + 170, b + 120, 150, 25);
            }

            if (j == 3) {
                CustomerJTextField[j].setBounds(a + 170, b + 170, 150, 25);
            }

            if (j == 4) {
                CustomerJTextField[j].setBounds(a + 660, b + 20, 150, 25);
            }
            if (j == 5) {
                CustomerJTextField[j].setBounds(a + 660, b + 65, 150, 25);
            }
            CustomerJTextField[0].setFont(new Font("Corbel", Font.BOLD, 20));
            rightPanel.add(CustomerJTextField[j]);
        }

        CustomerJTextArea = new JTextArea();
        {
            CustomerJTextArea.setBounds(190, 245, 150, 100);
            CustomerJTextArea.setFont(new Font("Corbel", Font.BOLD, 20));
            rightPanel.add(CustomerJTextArea);

        }

        CustomerJButtons = new JButton[2];
        {

            CustomerJButtons[0] = new JButton("Save");
            CustomerJButtons[1] = new JButton("New");

            CustomerJButtons[0].setBounds(160, 380, 100, 40);
            CustomerJButtons[1].setBounds(550, 380, 100, 40);

            CustomerJButtons[0].setFont(new Font("Corbel", Font.BOLD, 20));
            CustomerJButtons[1].setFont(new Font("Corbel", Font.BOLD, 20));

            CustomerJButtons[0].addActionListener(this);
            CustomerJButtons[1].addActionListener(this);

            rightPanel.add(CustomerJButtons[0]);
            rightPanel.add(CustomerJButtons[1]);
        }

    }

    public void initProductPanel() {
        ProductJLabels = new JLabel[5];
        int x = 20, y = 20;
        for (int i = 0; i < ProductJLabels.length; i++) {
            ProductJLabels[i] = new JLabel(productlabelString[i]);
            if (i == 0) {
                ProductJLabels[i].setBounds(x + 40, y + 10, 120, 50);
            }

            if (i == 1) {
                ProductJLabels[i].setBounds(x + 40, y + 60, 140, 50);
            }
            if (i == 2) {
                ProductJLabels[i].setBounds(x + 40, y + 110, 120, 50);
            }

            if (i == 3) {
                ProductJLabels[i].setBounds(x + 500, y + 10, 120, 50);
            }
            if (i == 4) {
                ProductJLabels[i].setBounds(x + 500, y + 60, 180, 50);
            }
            ProductJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 22));
            rightPanel.add(ProductJLabels[i]);
        }

        ProductJTextField = new JTextField[6];
        int a = 20, b = 20;
        for (int j = 0; j < ProductJTextField.length; j++) {
            ProductJTextField[j] = new JTextField();
            if (j == 0) {
                ProductJTextField[j].setBounds(a + 200, b + 25, 150, 25);
            }
            if (j == 1) {
                ProductJTextField[j].setBounds(a + 200, b + 75, 150, 25);
            }
            if (j == 2) {
                ProductJTextField[j].setBounds(a + 750, b + 20, 150, 25);
            }

            if (j == 3) {
                ProductJTextField[j].setBounds(a + 750, b + 70, 150, 25);
            }
            rightPanel.add(ProductJTextField[j]);
        }

        ProductJTextArea = new JTextArea();
        {
            ProductJTextArea.setBounds(220, 150, 150, 80);
            ProductJTextArea.setFont(new Font("Corbel", Font.BOLD, 20));
            rightPanel.add(ProductJTextArea);
        }
        ProductJTable = new JTable();
        {
            ProductJTable = new JTable(Data, ColumnNames);
            rightPanel.add(ProductJTable);
        }

    }

    public void initSettingPanel() {
        SettingJLabels = new JLabel[5];
        int x = 20, y = 20;
        for (int i = 0; i < SettingJLabels.length; i++) {
            SettingJLabels[i] = new JLabel(settinglabelString[i]);
            if (i == 0) {
                SettingJLabels[i].setBounds(x + 40, y + 10, 120, 50);
            }

            if (i == 1) {
                SettingJLabels[i].setBounds(x + 40, y + 60, 140, 50);
            }
            if (i == 2) {
                SettingJLabels[i].setBounds(x + 40, y + 110, 120, 50);
            }
            if (i == 3) {
                SettingJLabels[i].setBounds(x + 40, y + 160, 120, 50);
            }
            if (i == 4) {
                SettingJLabels[i].setBounds(x + 40, y + 210, 120, 50);
            }

            SettingJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 22));
            rightPanel.add(SettingJLabels[i]);

            SettingJTextField = new JTextField[5];
            int a = 20, b = 20;
            for (int j = 0; j < SettingJTextField.length; j++) {
                SettingJTextField[j] = new JTextField();
                if (j == 0) {
                    SettingJTextField[j].setBounds(a + 200, b + 25, 150, 25);
                }
                if (j == 1) {
                    SettingJTextField[j].setBounds(a + 200, b + 75, 150, 25);
                }
                if (j == 2) {
                    SettingJTextField[j].setBounds(a + 200, b + 120, 150, 25);
                }
                if (j == 3) {
                    SettingJTextField[j].setBounds(a + 200, b + 170, 150, 25);
                }
                if (j == 4) {
                    SettingJTextField[j].setBounds(a + 200, b + 220, 150, 25);
                }

                rightPanel.add(SettingJTextField[j]);
            }
            SettingJButton = new JButton();
            {
                SettingJButton = new JButton("Summit");
                SettingJButton.setBounds(160, 380, 100, 40);
                SettingJButton.setFont(new Font("Corbel", Font.BOLD, 15));

                rightPanel.add(SettingJButton);

            }
        }
    }

    public void initSellerEditPanel() {
        rightPanel.removeAll();
        SellerEditJLabels = new JLabel[11];
        int x = 20, y = 20;
        for (int i = 0; i < SellerEditJLabels.length; i++) {
            SellerEditJLabels[i] = new JLabel(sellerlabelString[i]);
            if (i >= 0) {
                SellerEditJLabels[i].setBounds(x, y + 10, 150, 50);
                y += 40;
            }

            SellerEditJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
            rightPanel.add(SellerEditJLabels[i]);
        }

        SellerEditJTextField = new JTextField[11];
        int a = 20, b = 20;
        for (int j = 0; j < SellerEditJTextField.length; j++) {
            SellerEditJTextField[j] = new JTextField();
            if (j >= 0) {
                SellerEditJTextField[j].setBounds(a + 160, b + 25, 130, 25);
                b += 40;
            }
            rightPanel.add(SellerEditJTextField[j]);
            rightPanel.repaint();
            rightPanel.revalidate();
        }

    }

    public void initCustomerEditPanel() {
        CustomerEditJLabels = new JLabel[8];
        int x = 20, y = 20;
        for (int i = 0; i < CustomerEditJLabels.length; i++) {
            CustomerEditJLabels[i] = new JLabel(customerlabelString[i]);
            if (i >= 0) {
                CustomerEditJLabels[i].setBounds(x + 10, y + 20, 150, 50);
                y += 40;
            }

            CustomerEditJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
            rightPanel.add(CustomerEditJLabels[i]);
        }

        CustomerEditJTextField = new JTextField[8];
        int a = 20, b = 20;
        for (int j = 0; j < CustomerEditJTextField.length; j++) {
            CustomerEditJTextField[j] = new JTextField();
            if (j >= 0) {
                CustomerEditJTextField[j].setBounds(a + 160, b + 30, 130, 25);
                b += 40;
            }
            rightPanel.add(CustomerEditJTextField[j]);
        }
        rightPanel.repaint(); 
        rightPanel.revalidate();
    }

    public void initProductEditPanel() {
        ProductEditJLabels = new JLabel[6];
        int x = 20, y = 20;
        for (int i = 0; i < ProductEditJLabels.length; i++) {
            ProductEditJLabels[i] = new JLabel(productEditlabelString[i]);
            if (i >= 0) {
                ProductEditJLabels[i].setBounds(x + 10, y + 10, 160, 80);
                y += 40;
            }

            ProductEditJLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
            rightPanel.add(ProductEditJLabels[i]);
        }
        ProductEditJTextField = new JTextField[6];
        int a = 20, b = 20;
        for (int j = 0; j < ProductEditJTextField.length; j++) {
            ProductEditJTextField[j] = new JTextField();
            if (j >= 0) {
                ProductEditJTextField[j].setBounds(a + 160, b + 30, 130, 25);
                b += 40;
            }
            rightPanel.add(ProductEditJTextField[j]);
        }
        ProductEditJButton = new JButton();
        {
            ProductEditJButton = new JButton("Modify");
            ProductEditJButton.setBounds(160, 380, 100, 40);
            ProductEditJButton.setFont(new Font("Corbel", Font.BOLD, 15));

            rightPanel.add(ProductEditJButton);
        }
        rightPanel.repaint(); 
        rightPanel.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftJButtons[0]) {
            rightPanelIndex = 0;
            rightPanel.removeAll();
            rightPanel.setBackground(new Color(143, 80, 155));
            initSellerPanel();
        }

        if (e.getSource() == leftJButtons[1]) {
            rightPanelIndex = 1;
            rightPanel.removeAll();
            rightPanel.setBackground(new Color(120, 70, 155));
            initCustomerPanel();
        }
        
        if (e.getSource() == leftJButtons[3]) {
            rightPanelIndex = 3;
            rightPanel.removeAll();
            rightPanel.setBackground(new Color(100, 75, 155));
            initProductPanel();

        }
        if (e.getSource() == leftJButtons[5]) {
            rightPanelIndex = 5;
            rightPanel.removeAll();
            rightPanel.setBackground(new Color(143, 80, 155));
            initSettingPanel();
        }
        
        if (e.getSource() == topJButtons[0]) {
            rightPanel.removeAll();
//            if (rightPanelIndex == 0)
//                rightPanel.setBackground(new Color(100, 80, 155));
//            else if (rightPanelIndex == 1)
//                rightPanel.setBackground(new Color(20, 80, 155));
//            else if (rightPanelIndex == 2)
//                rightPanel.setBackground(new Color(60, 20, 105));
//            else if (rightPanelIndex == 3)
//                rightPanel.setBackground(new Color(73, 55, 35));
//            else if (rightPanelIndex == 4)
//                rightPanel.setBackground(new Color(80, 80, 32));
//            else if (rightPanelIndex == 5)
//                rightPanel.setBackground(new Color(12, 120, 155));
        }
        
        if (e.getSource() == topJButtons[1]) {
            rightPanel.removeAll();
            
        }
        
        if (e.getSource() == topJButtons[2]) {
            rightPanel.removeAll();
            if (rightPanelIndex == 0)
                initSellerEditPanel();
            else if (rightPanelIndex == 1)
                initCustomerEditPanel();
            else if (rightPanelIndex == 3)
                initProductEditPanel();
        }
        
        if (e.getSource() == topJButtons[3]) {
            System.out.println("This is topJButton[3]");            
            rightPanel.removeAll();
        }
        
    }
}
