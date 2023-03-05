import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBook extends JFrame implements ActionListener {

    private final JPanel mainPanel = new JPanel();
    private final JLabel collegeLogo = new JLabel(new ImageIcon("C:\\images\\collegeLogo.jpg"));
    private final JLabel collegeName = new JLabel("Swami Purnanand Degree College of Technical Education");
    private final JLabel bookTitleLabel = new JLabel("AddBook Title");
    private final JLabel bookAuthorLabel = new JLabel("AddBook Author");
    private final JLabel bookPublisherLabel = new JLabel("AddBook Publisher");
    private final JLabel bookEditionLabel = new JLabel("AddBook Edition");
    private final JLabel bookQuantityLabel = new JLabel("Quantity");
    private final JLabel bookSerialNumberLabel = new JLabel("Serial Number");
    private final JTextField bookTitleTextField = new JTextField(20);
    private final JTextField bookAuthorTextField = new JTextField(20);
    private final JTextField bookPublisherTextField = new JTextField(20);
    private final JTextField bookEditionTextField = new JTextField(20);
    private final JTextField bookQuantityTextField = new JTextField(3);
    private final JTextField bookSerialNumberTextField = new JTextField(5);
    private final JButton addButton = new JButton("Add");
    private final JButton clearButton = new JButton("Clear");
    private final JButton viewButton = new JButton("View");

    public AddBook(){
        JFrame mainFrame = new JFrame("AddBook Details");
        mainFrame.setBounds(450,150,460,450);
        mainFrame.setVisible(true);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        setFont();
        setComponentPosition();
        setComponentsToPanel();
        addActionListenerToButton();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == addButton) {
            if(bookTitleTextField.getText().equals("")||bookAuthorTextField.getText().equals("")||bookPublisherTextField.getText().equals("")||bookEditionTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "Enter Complete Data");
            }
            else{
                variableInitialization();
                clearForm();
                try {
                    String query = "insert into book (bookId,title,author,publisher,edition,quantity,serialnumber) values(?,?,?,?,?,?,?)";
                    PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement(query);
                    ps.setString(1, FixConstant.bookId);
                    ps.setString(2,FixConstant.bookTitle);
                    ps.setString(3,FixConstant.bookAuthor);
                    ps.setString(4,FixConstant.bookPublisher);
                    ps.setString(5,FixConstant.bookEdition);
                    ps.setInt(6,FixConstant.bookQuantity);
                    ps.setInt(7,FixConstant.bookSerialNumber);
                    ps.executeUpdate();
                }catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                }
                FixConstant.getBook();
                JOptionPane.showMessageDialog(mainPanel,"Add data successfully");
            }
        }
        if(e.getSource() == viewButton) {
            new ViewBook();
        }
        if(e.getSource() == clearButton) {
            clearForm();
        }
    }

    public void addActionListenerToButton(){
        clearButton.addActionListener(this);
        viewButton.addActionListener(this);
        addButton.addActionListener(this);
    }

    public void setComponentPosition(){
        collegeLogo.setBounds(180,5,70,70);
        collegeName.setBounds(10,80,440,FixConstant.labelHeight);

        bookTitleLabel.setBounds(FixConstant.labelMarginX,120,FixConstant.labelWidth,FixConstant.labelHeight);
        bookTitleTextField.setBounds(FixConstant.textFieldMarginX,120,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookAuthorLabel.setBounds(FixConstant.labelMarginX,160,FixConstant.labelWidth,FixConstant.labelHeight);
        bookAuthorTextField.setBounds(FixConstant.textFieldMarginX,160,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookPublisherLabel.setBounds(FixConstant.labelMarginX,200,FixConstant.labelWidth,FixConstant.labelHeight);
        bookPublisherTextField.setBounds(FixConstant.textFieldMarginX,200,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookEditionLabel.setBounds(FixConstant.labelMarginX,240,FixConstant.labelWidth,FixConstant.labelHeight);
        bookEditionTextField.setBounds(FixConstant.textFieldMarginX,240,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookQuantityLabel.setBounds(FixConstant.labelMarginX,280,FixConstant.labelWidth,FixConstant.labelHeight);
        bookQuantityTextField.setBounds(FixConstant.textFieldMarginX,280,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        bookSerialNumberLabel.setBounds(FixConstant.labelMarginX,320,FixConstant.labelWidth,FixConstant.labelHeight);
        bookSerialNumberTextField.setBounds(FixConstant.textFieldMarginX,320,FixConstant.textFieldWidth,FixConstant.textFieldHeight);

        addButton.setBounds(FixConstant.buttonMarginX,360+10,FixConstant.buttonWidth,FixConstant.buttonHeight);
        clearButton.setBounds(FixConstant.buttonMarginX+100,360+10,FixConstant.buttonWidth,FixConstant.buttonHeight);
        viewButton.setBounds(FixConstant.buttonMarginX+100+100,360+10,FixConstant.buttonWidth,FixConstant.buttonHeight);
    }

    public void setComponentsToPanel(){
        mainPanel.add(collegeLogo);
        mainPanel.add(collegeName);
        mainPanel.add(bookTitleLabel);
        mainPanel.add(bookTitleTextField);
        mainPanel.add(bookAuthorLabel);
        mainPanel.add(bookAuthorTextField);
        mainPanel.add(bookPublisherLabel);
        mainPanel.add(bookPublisherTextField);
        mainPanel.add(bookEditionLabel);
        mainPanel.add(bookEditionTextField);
        mainPanel.add(bookQuantityLabel);
        mainPanel.add(bookQuantityTextField);
        mainPanel.add(bookSerialNumberLabel);
        mainPanel.add(bookSerialNumberTextField);

        mainPanel.add(addButton);
        mainPanel.add(clearButton);
        mainPanel.add(viewButton);
    }

    public void setFont(){
        bookTitleLabel.setFont(FixConstant.myFont);
        bookAuthorLabel.setFont(FixConstant.myFont);
        bookPublisherLabel.setFont(FixConstant.myFont);
        bookEditionLabel.setFont(FixConstant.myFont);
        addButton.setFont(FixConstant.myFont);
        clearButton.setFont(FixConstant.myFont);
        viewButton.setFont(FixConstant.myFont);
        collegeName.setFont(FixConstant.myFont);
        bookQuantityLabel.setFont(FixConstant.myFont);
        bookSerialNumberLabel.setFont(FixConstant.myFont);
    }

    public void variableInitialization(){
        FixConstant.bookTitle= bookTitleTextField.getText();
        FixConstant.bookAuthor=bookAuthorTextField.getText();
        FixConstant.bookPublisher=bookPublisherTextField.getText();
        FixConstant.bookEdition=bookEditionTextField.getText();
        FixConstant.bookQuantity=Integer.parseInt(bookQuantityTextField.getText());
        FixConstant.bookSerialNumber=Integer.parseInt(bookSerialNumberTextField.getText());
        FixConstant.bookId=FixConstant.getBookID(FixConstant.bookTitle,FixConstant.bookAuthor,FixConstant.bookPublisher,FixConstant.bookEdition);
    }

    public void clearForm(){
        bookTitleTextField.setText("");
        bookAuthorTextField.setText("");
        bookPublisherTextField.setText("");
        bookEditionTextField.setText("");
        bookQuantityTextField.setText("");
        bookSerialNumberTextField.setText("");
    }

}