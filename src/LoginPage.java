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
    private final JLabel collegeLogo = new JLabel(new ImageIcon("D:\\Online Sync\\GitHub\\LiabraryMangementSystem\\src\\images\\collegeLogo.jpg"));
    private final JLabel collegeName = new JLabel("Swami Purnanand Degree College of Technical Education");

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
        collegeName.setFont(myFont);

        collegeLogo.setBounds(200,5,70,70);
        collegeName.setBounds(FixConstant.labelMarginX,80,440,FixConstant.labelHeight);

        bookNameLabel.setBounds(FixConstant.labelMarginX,120,FixConstant.labelWidth,FixConstant.labelHeight);
        bookNameTextField.setBounds(FixConstant.textFieldMarginX,120,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookAuthorLabel.setBounds(FixConstant.labelMarginX,160,FixConstant.labelWidth,FixConstant.labelHeight);
        bookAuthorTextField.setBounds(FixConstant.textFieldMarginX,160,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookPublisherLabel.setBounds(FixConstant.labelMarginX,200,FixConstant.labelWidth,FixConstant.labelHeight);
        bookPublisherTextField.setBounds(FixConstant.textFieldMarginX,200,FixConstant.textFieldWidth,FixConstant.textFieldHeight);
        /*bookPublisherLabel.setBackground(Color.yellow);
        bookPublisherLabel.setOpaque(true);**/


        bookEditionLabel.setBounds(FixConstant.labelMarginX,240,FixConstant.labelWidth,FixConstant.labelHeight);
        bookEditionTextField.setBounds(FixConstant.textFieldMarginX,240,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

//        submitButton.setBounds(35,170,FixConstant.buttonWidth,30);
//        clearButton.setBounds(135,170,FixConstant.buttonWidth,30);
//        viewButton.setBounds(235,170,FixConstant.buttonWidth,30);

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
        mainPanel.add(collegeLogo);
        mainPanel.add(collegeName);
    }

    public LoginPage(){
        JFrame mainFrame = new JFrame("Book Details");
        mainFrame.setSize(600,600);
        mainFrame.setVisible(true);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        initComponents();
        mainFrame.setResizable(true);
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
        //Sample text
    }
}
