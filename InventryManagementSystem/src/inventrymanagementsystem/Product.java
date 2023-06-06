package inventrymanagementsystem;

import java.awt.*;
import javax.swing.*;

public class Product {

    JFrame mainFrame;
    JPanel mainPanel;
    JLabel[] ProductJLabels;
    JTextField[] ProductJTextField;

    public Product() {
        mainFrame = new JFrame("Product");
        mainFrame.setSize(1500, 800);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1500, 800);
        mainPanel.setBackground(Color.GRAY);
        mainFrame.add(mainPanel);
        mainPanel.setVisible(true);

        ProductJLabels = new JLabel[7];
        ProductJLabels[0] = new JLabel("Product Id");
        ProductJLabels[1] = new JLabel("Product Name");
        ProductJLabels[2] = new JLabel("Description");
        ProductJLabels[3] = new JLabel("Stock Level");
        ProductJLabels[4] = new JLabel("Out of Stock");
        ProductJLabels[5] = new JLabel("No. of Order");
        ProductJLabels[6] = new JLabel("Cost");

        ProductJLabels[0].setBounds(0, 10, 250, 30);
        ProductJLabels[0].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[1].setBounds(0, 70, 250, 30);
        ProductJLabels[1].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[2].setBounds(0, 130, 250, 30);
        ProductJLabels[2].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[3].setBounds(0, 210, 250, 30);
        ProductJLabels[3].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[4].setBounds(0, 270, 250, 30);
        ProductJLabels[4].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[5].setBounds(0, 330, 250, 30);
        ProductJLabels[5].setFont(new Font("Times New Roman", Font.BOLD, 25));
        ProductJLabels[6].setBounds(0, 390, 250, 30);
        ProductJLabels[6].setFont(new Font("Times New Roman", Font.BOLD, 25));

        mainPanel.add(ProductJLabels[0]);
        mainPanel.add(ProductJLabels[1]);
        mainPanel.add(ProductJLabels[2]);
        mainPanel.add(ProductJLabels[3]);
        mainPanel.add(ProductJLabels[4]);
        mainPanel.add(ProductJLabels[5]);
        mainPanel.add(ProductJLabels[6]);

        ProductJTextField = new JTextField[7];
        ProductJTextField[0] = new JTextField("");
        //ProductJTextField.setLayout(null)
        ProductJTextField[1] = new JTextField("");
        ProductJTextField[2] = new JTextField("");
        ProductJTextField[3] = new JTextField("");
        ProductJTextField[4] = new JTextField("");
        ProductJTextField[5] = new JTextField("");
        ProductJTextField[6] = new JTextField("");

        ProductJTextField[0].setBounds(270, 10, 250, 40);
        ProductJTextField[1].setBounds(270, 70, 250, 40);
        ProductJTextField[2].setBounds(270, 130, 250, 40);
        ProductJTextField[3].setBounds(270, 190, 250, 40);
        ProductJTextField[4].setBounds(270, 250, 250, 40);
        ProductJTextField[5].setBounds(270, 310, 250, 40);
        ProductJTextField[6].setBounds(270, 370, 250, 40);

        mainPanel.add(ProductJTextField[0]);
        mainPanel.add(ProductJTextField[1]);
        mainPanel.add(ProductJTextField[2]);
        mainPanel.add(ProductJTextField[3]);
        mainPanel.add(ProductJTextField[4]);
        mainPanel.add(ProductJTextField[5]);
        mainPanel.add(ProductJTextField[6]);
        mainFrame.setVisible(true);
    }
   

}
