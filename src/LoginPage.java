import java.awt.*;

import javax.swing.*;

public class LoginPage { //default constructor

    private String bookName, bookAuthor, bookPublisher, bookEdition;

    private final JPanel mainPanel = new JPanel();

    private final JLabel bookNameLabel = new JLabel("Book Name");

    private final JLabel bookAuthorLabel = new JLabel("Book Author");

    private final JLabel bookPublisherLabel = new JLabel("Book Publisher");

    private final JLabel bookEditionLabel = new JLabel("Book Edition");
     
    private final JTextField bookNameTextField = new JTextField(20);

    private final JTextField bookAuthorTextField = new JTextField(20);

    private final JTextField bookPublisherTextField = new JTextField(20);

    private final JTextField bookEditionTextField = new JTextField(20);
     
    private final JButton submitButton = new JButton("Submit");

    private final JButton clearButton = new JButton("Clear");

    private final JButton viewButton = new JButton("View");

    Font myFont = new Font("Century Gothic",Font.BOLD,15);

    public void initComponents(){

        mainPanel.setLayout(null);

        bookNameLabel.setFont(myFont);

        bookAuthorLabel.setFont(myFont);

        bookPublisherLabel.setFont(myFont);

        bookEditionLabel.setFont(myFont);

        submitButton.setFont(myFont);

        clearButton.setFont(myFont);

        viewButton.setFont(myFont);

        bookNameLabel.setBounds(10,10,FixConstant.labelWidth,30);

        bookNameTextField.setBounds(FixConstant.textFieldX,10,FixConstant.textFieldWidth,30);

        bookAuthorLabel.setBounds(10,45,FixConstant.labelWidth,30);

        bookAuthorTextField.setBounds(FixConstant.textFieldX,45,FixConstant.textFieldWidth,30);

        bookPublisherLabel.setBounds(10,80,FixConstant.labelWidth,30);

        bookPublisherTextField.setBounds(FixConstant.textFieldX,80,FixConstant.textFieldWidth,30);

        bookEditionLabel.setBounds(10,115,FixConstant.labelWidth,30);

        bookEditionTextField.setBounds(FixConstant.textFieldX,115,FixConstant.textFieldWidth,30);

        submitButton.setBounds(35,170,FixConstant.buttonWidth,30);

        clearButton.setBounds(135,170,FixConstant.buttonWidth,30);

        viewButton.setBounds(235,170,FixConstant.buttonWidth,30);

        clearButton.addActionListener(e -> {

            bookNameTextField.setText("");

            bookAuthorTextField.setText("");

            bookPublisherTextField.setText("");

            bookEditionTextField.setText("");

        });

        submitButton.addActionListener(e -> {

            bookName=bookNameTextField.getText();

            bookAuthor=bookAuthorTextField.getText();

            bookPublisher=bookPublisherTextField.getText();

            bookEdition=bookEditionTextField.getText();

            getBook();

            clearForm();

        });
         
        mainPanel.add(bookNameLabel);

        mainPanel.add(bookNameTextField);

        mainPanel.add(bookAuthorLabel);

        mainPanel.add(bookAuthorTextField);

        mainPanel.add(bookPublisherLabel);

        mainPanel.add(bookPublisherTextField);

        mainPanel.add(bookEditionLabel);

        mainPanel.add(bookEditionTextField);

        mainPanel.add(submitButton);

        mainPanel.add(clearButton);

        mainPanel.add(viewButton);

    }

    public LoginPage(){

        JFrame mainFrame = new JFrame("Book Details");

        mainFrame.setSize(380,400);

        mainFrame.setVisible(true);

        mainFrame.add(mainPanel);

        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        initComponents();

        mainFrame.setResizable(false);

    }
     
    public void clearForm(){

        bookNameTextField.setText("");

        bookAuthorTextField.setText("");

        bookPublisherTextField.setText("");

        bookEditionTextField.setText("");

    }

    public void getBook(){

         System.out.println("Book Name: "+bookName+

                 "\nBook Author: "+bookAuthor+

                 "\nBook Publisher: "+bookPublisher+

                 "\nBook Edition: "+bookEdition);

    }

    @Override

    public String toString(){

        return "Book Name: "+bookName+"\nBook Author: "+bookAuthor+"\nBook Publisher: "+bookPublisher+"\nBook Edition: "+bookEdition;

    }
     
    public static void main(String[] args) {

        new LoginPage();

    }

}
