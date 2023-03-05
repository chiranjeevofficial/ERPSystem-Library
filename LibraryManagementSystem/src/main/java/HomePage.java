import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePage implements ActionListener {
    private final JFrame mainFrame = new JFrame("Library Management System");
    private final JMenuItem addBookMenuItem = new JMenuItem("Add Book");

    private final JMenuBar leftMenuBar = new JMenuBar();

    private final JMenu accountMenu = new JMenu("Account");
    private final JMenu bookMenu = new JMenu("Book");
    private final JMenu studentMenu = new JMenu("Student");

    private final JMenuItem loginAccountMenuItem = new JMenuItem("Log In");
    private final JMenuItem logoutAccountMenuItem = new JMenuItem("Log Out");

    private final JMenuItem updateBookMenuItem = new JMenuItem("Update Book");
    private final JMenuItem deleteBookMenuItem = new JMenuItem("Delete Book");
    private final JMenuItem viewBookMenuItem = new JMenuItem("View Book");

    private final JMenuItem addStudentMenuItem = new JMenuItem("Add Student");
    private final JMenuItem updateStudentMenuItem = new JMenuItem("Update Student");
    private final JMenuItem deleteStudentMenuItem = new JMenuItem("Delete Student");
    private final JMenuItem allotBookStudentMenuItem = new JMenuItem("Allot Book");
    private final JMenuItem collectBookStudentMenuItem = new JMenuItem("Collect Book");

    private final JPanel bookPanel = new JPanel(null);
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


    public HomePage() {
        mainFrame.setVisible(true);
        mainFrame.setSize(700,300);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setJMenuBar(leftMenuBar);
        mainFrame.setResizable(true);

        addLeftMenuBarMenuOnFrame();

        addAccountMenuItemOnFrame();

        addBookMenuItemOnFrame();

        addStudentMenuItemOnFrame();

        addActionListenerOnMenuItem();

    }

    public void addLeftMenuBarMenuOnFrame() {
        leftMenuBar.add(accountMenu);
        leftMenuBar.add(bookMenu);
        leftMenuBar.add(studentMenu);
    }

    public void addAccountMenuItemOnFrame() {
        accountMenu.add(loginAccountMenuItem);
        accountMenu.add(logoutAccountMenuItem);
    }

    public void addBookMenuItemOnFrame() {
        bookMenu.add(addBookMenuItem);
        bookMenu.add(updateBookMenuItem);
        bookMenu.add(deleteBookMenuItem);
        bookMenu.add(viewBookMenuItem);
    }

    public void addStudentMenuItemOnFrame() {
        studentMenu.add(addStudentMenuItem);
        studentMenu.add(updateStudentMenuItem);
        studentMenu.add(deleteStudentMenuItem);
        studentMenu.add(allotBookStudentMenuItem);
        studentMenu.add(collectBookStudentMenuItem);
    }

    public void addActionListenerOnMenuItem(){
        loginAccountMenuItem.addActionListener(this);
        logoutAccountMenuItem.addActionListener(this);

        addBookMenuItem.addActionListener(this);
        updateBookMenuItem.addActionListener(this);
        deleteBookMenuItem.addActionListener(this);
        viewBookMenuItem.addActionListener(this);

        addStudentMenuItem.addActionListener(this);
        updateStudentMenuItem.addActionListener(this);
        deleteStudentMenuItem.addActionListener(this);
        allotBookStudentMenuItem.addActionListener(this);
        collectBookStudentMenuItem.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginAccountMenuItem)
            System.out.println("Login Button");

        if(e.getSource() == logoutAccountMenuItem)
            System.out.println("logout Button");

        if(e.getSource() == addBookMenuItem) {
            System.out.println("Add Book Button");
            mainFrame.setSize(460,500);
            mainFrame.setVisible(true);
            mainFrame.add(bookPanel);
            mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
            mainFrame.setResizable(true);
            mainFrame.setLocationRelativeTo(null);
            setFont();
            setComponentPosition();
            setComponentsToPanel();
            addActionListenerToButton();
            addButton.addActionListener(this);
            viewButton.addActionListener(this);
            clearButton.addActionListener(this);
        }
            if(e.getSource() == addButton) {
                if(bookTitleTextField.getText().equals("")||bookAuthorTextField.getText().equals("")||bookPublisherTextField.getText().equals("")||bookEditionTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(bookPanel, "Enter Complete Data");
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
                    JOptionPane.showMessageDialog(bookPanel,"Add data successfully");
                }
                mainFrame.dispose();
            }
            if(e.getSource() == viewButton) {
                new ViewBook();
            }
            if(e.getSource() == clearButton) {
                clearForm();
            }
        if(e.getSource() == updateBookMenuItem)
            System.out.println("Update Book Button");
        if(e.getSource() == deleteBookMenuItem)
            System.out.println("Delete Book Button");
        if(e.getSource() == viewBookMenuItem) {
            System.out.println("View Book Button");
            DefaultTableModel tableModel = new DefaultTableModel();
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            Dimension size = new Dimension(mainFrame.getWidth(), mainFrame.getHeight());
            table.setPreferredScrollableViewportSize(size);

            mainFrame.add(scrollPane);

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

        if(e.getSource() == addStudentMenuItem)
            System.out.println("Add Student Button");
        if(e.getSource() == updateStudentMenuItem)
            System.out.println("Update Student Button");
        if(e.getSource() == deleteStudentMenuItem)
            System.out.println("Delete Student Button");

        if(e.getSource() == allotBookStudentMenuItem)
            System.out.println("Allot Book Button");
        if(e.getSource() == collectBookStudentMenuItem)
            System.out.println("Collect Book Button");
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

    public void clearForm(){
        bookTitleTextField.setText("");
        bookAuthorTextField.setText("");
        bookPublisherTextField.setText("");
        bookEditionTextField.setText("");
        bookQuantityTextField.setText("");
        bookSerialNumberTextField.setText("");
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

    public void variableInitialization(){
        FixConstant.bookTitle= bookTitleTextField.getText();
        FixConstant.bookAuthor=bookAuthorTextField.getText();
        FixConstant.bookPublisher=bookPublisherTextField.getText();
        FixConstant.bookEdition=bookEditionTextField.getText();
        FixConstant.bookQuantity=Integer.parseInt(bookQuantityTextField.getText());
        FixConstant.bookSerialNumber=Integer.parseInt(bookSerialNumberTextField.getText());
        FixConstant.bookId=FixConstant.getBookID(FixConstant.bookTitle,FixConstant.bookAuthor,FixConstant.bookPublisher,FixConstant.bookEdition);
    }

    public void addActionListenerToButton(){
        clearButton.addActionListener(this);
        viewButton.addActionListener(this);
        addButton.addActionListener(this);
    }


    public void setComponentsToPanel(){
        bookPanel.add(collegeLogo);
        bookPanel.add(collegeName);
        bookPanel.add(bookTitleLabel);
        bookPanel.add(bookTitleTextField);
        bookPanel.add(bookAuthorLabel);
        bookPanel.add(bookAuthorTextField);
        bookPanel.add(bookPublisherLabel);
        bookPanel.add(bookPublisherTextField);
        bookPanel.add(bookEditionLabel);
        bookPanel.add(bookEditionTextField);
        bookPanel.add(bookQuantityLabel);
        bookPanel.add(bookQuantityTextField);
        bookPanel.add(bookSerialNumberLabel);
        bookPanel.add(bookSerialNumberTextField);

        bookPanel.add(addButton);
        bookPanel.add(clearButton);
        bookPanel.add(viewButton);
    }


}
