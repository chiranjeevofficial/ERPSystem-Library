package inventrymanagementsystem;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class homepage {

    JFrame mainFrame;
    JPanel mainPanel;
    JButton[] homepageJButtons;

    public homepage() {
        mainFrame = new JFrame("Homepage");
        mainFrame.setSize(1500, 800);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 300, 900);
        mainPanel.setBackground(Color.BLACK);
        mainFrame.add(mainPanel);
        mainPanel.setVisible(true);

        homepageJButtons = new JButton[7];
        String[] buttonString = {
            "Sales", 
            "Customer",
            "Invoice",
            "Product",
            "Return's",
            "Setting",
            "Logout"
        };

        homepageJButtons[0] = new JButton("Sales");
        homepageJButtons[1] = new JButton("Customer");
        homepageJButtons[2] = new JButton("Invoice");
        homepageJButtons[3] = new JButton("Product");
        homepageJButtons[4] = new JButton("Return's");
        homepageJButtons[5] = new JButton("Setting");
        homepageJButtons[6] = new JButton("Logout");

        homepageJButtons[0].setSize(80, 30);
        homepageJButtons[0].setLocation(100, 50);
        homepageJButtons[0].setFont(new Font("Corbel", Font.BOLD, 20));
        homepageJButtons[1].setSize(150, 30);
        homepageJButtons[1].setLocation(100, 100);
        homepageJButtons[1].setFont(new Font("Corbel", Font.BOLD, 20));

        homepageJButtons[2].setSize(150, 30);
        homepageJButtons[2].setLocation(100, 150);
        homepageJButtons[2].setFont(new Font("Corbel", Font.BOLD, 20));
        homepageJButtons[3].setSize(150, 30);
        homepageJButtons[3].setLocation(100, 200);
        homepageJButtons[3].setFont(new Font("Corbel", Font.BOLD, 20));

        homepageJButtons[4].setSize(150, 30);
        homepageJButtons[4].setLocation(100, 250);
        homepageJButtons[4].setFont(new Font("Corbel", Font.BOLD, 20));
        homepageJButtons[5].setSize(150, 30);
        homepageJButtons[5].setLocation(100, 300);
        homepageJButtons[5].setFont(new Font("Corbel", Font.BOLD, 20));

        homepageJButtons[6].setSize(100, 30);
        homepageJButtons[6].setLocation(150, 550);
        homepageJButtons[6].setFont(new Font("Corbel", Font.BOLD, 20));
      
        mainPanel.add(homepageJButtons[0]);
        mainPanel.add(homepageJButtons[1]);
        mainPanel.add(homepageJButtons[2]);
        mainPanel.add(homepageJButtons[3]);
        mainPanel.add(homepageJButtons[4]);
        mainPanel.add(homepageJButtons[5]);
        mainPanel.add(homepageJButtons[6]);
       

    }
}
