package lms;

import BrahmasmiLiabrary.util;
import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage implements ActionListener, KeyListener, ItemListener {
    private final Connection con;
    private PreparedStatement preStmt;
    String[] course = {
            "Select the course",
            "B.Sc Food Technology",
            "B.Sc Home Science",
            "B.Sc Information Technology",
            "Bachelor of Arts",
            "Bachelor of Arts in Yoga",
            "Bachelor of Commerce"
    };
    private Student std;
    private Book book;
    private final JPanel newStudentPanel, newBookPanel, issuedBookPanel;
    private final JLabel[] studentInfoLabel  = new JLabel[7];
    private JTextField[] studentInfoTextField;
    private JComboBox<String> bookCourseComboBox;
    private JComboBox<String> courseComboBox;
    private ButtonGroup genderButtonGroup;
    private JRadioButton male, female;
    private JDateChooser studentDateChooser, bookDateChooser;
    private final JButton addStudentButton = new JButton("Submit");
    private final JButton clearStudentButton = new JButton("Clear");
    private final JButton addBookButton = new JButton("Submit");
    private final JButton clearBookButton = new JButton("Clear");
    private JTextField[] bookTextField;
    JCheckBox accessionIdCheckBox;
    int latestAccessionId;

    public HomePage() { // Non-Parameterized Constructor
        con = util.getConnectionWithMySQL("library","root","admin@2023");
        JFrame mainFrame = new JFrame("Home Page");
        JTabbedPane mainTabbedPanel = new JTabbedPane();
        mainTabbedPanel.setSize(700,500);
        newStudentPanel = new JPanel(null);
        newBookPanel = new JPanel(null);
        issuedBookPanel = new JPanel(null);
        mainTabbedPanel.add("Add Student", newStudentPanel);
        mainTabbedPanel.add("Add Book", newBookPanel);
        mainTabbedPanel.add("Issue Book",issuedBookPanel);
        mainTabbedPanel.setSelectedIndex(1);
        initializeNewStudentFormPanel();
        initializeNewBookFormPanel();
        initializeIssuedBookFormPanel();
        initializeAllotBookFormPanel();
        BrahmasmiLiabrary.util.setMainFrame(mainFrame, mainTabbedPanel.getWidth(), mainTabbedPanel.getHeight());
        mainFrame.add(mainTabbedPanel);
    }

    void initializeNewStudentFormPanel() {
        courseComboBox = new JComboBox<>(course);
        courseComboBox.setSelectedIndex(0);
        courseComboBox.setBounds(120,71,200,27);
        genderButtonGroup = new ButtonGroup();
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        genderButtonGroup.add(male);
        genderButtonGroup.add(female);
        male.setBounds(120,130,100,30);
        female.setBounds(220,130,100,30);
        newStudentPanel.add(male);
        newStudentPanel.add(female);
        studentInfoTextField = new JTextField[4];
        studentDateChooser = new JDateChooser();

        String[] labelNames = {"Student Name", "Father Name", "Course", "Date of Birth", "Gender", "Phone Number", "Address"};
        int labelYAxisGap = 10, textFieldYAxisGap = 10;
        for (int i = 0; i < labelNames.length; i++) {
            studentInfoLabel[i] = new JLabel(labelNames[i]);
            studentInfoLabel[i].setBounds(10, labelYAxisGap, 100, 30);
            newStudentPanel.add(studentInfoLabel[i]);
            labelYAxisGap += studentInfoLabel[i].getHeight();
            textFieldYAxisGap+=i==2?90:0;
            if (i < studentInfoTextField.length){ // overtake the ArrayIndexOutOfBoundsException
                studentInfoTextField[i] = new JTextField(20);
                studentInfoTextField[i].setBounds(120, textFieldYAxisGap, 200, 30);
                newStudentPanel.add(studentInfoTextField[i]);
                textFieldYAxisGap += 30;
            }
        }
        studentInfoTextField[studentInfoTextField.length-2].addKeyListener(this);
        studentDateChooser.setBounds(120,studentInfoTextField[1].getY()+60,200,27);
        addStudentButton.setBounds(studentInfoTextField[studentInfoTextField.length-1].getX(),studentInfoTextField[studentInfoTextField.length-1].getY()+35,90,30);
        clearStudentButton.setBounds(addStudentButton.getX()+110, addStudentButton.getY(),addStudentButton.getWidth(),addStudentButton.getHeight());
        JTextField studentDateChooserTextField = (JTextField) studentDateChooser.getDateEditor().getUiComponent();
        studentDateChooserTextField.setDisabledTextColor(Color.BLACK);
        studentDateChooser.getDateEditor().setEnabled(false);
        addStudentButton.addActionListener(this);
        clearStudentButton.addActionListener(this);
        newStudentPanel.add(courseComboBox);
        newStudentPanel.add(addStudentButton);
        newStudentPanel.add(clearStudentButton);
        newStudentPanel.add(studentDateChooser);
    }

    void initializeNewBookFormPanel() {
        String query = "SELECT MAX(`Accession ID`) FROM book;";
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                latestAccessionId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String[] labelNamesString = {"Accession Id","Title","Author","Publisher","Edition","Course","Date","Quantity","Price"};
        String checkBoxMessage = "Change Accession Number";
        JLabel[] bookLabel = new JLabel[labelNamesString.length];
        bookCourseComboBox = new JComboBox<>(course);
        bookTextField = new JTextField[bookLabel.length-2];
        accessionIdCheckBox = new JCheckBox(checkBoxMessage);
        bookDateChooser = new JDateChooser();
        bookDateChooser.setDate(new Date());
        JTextField bookDateChooserTextField = (JTextField) bookDateChooser.getDateEditor().getUiComponent();
        bookDateChooserTextField.setDisabledTextColor(Color.BLACK);
        bookDateChooser.setEnabled(false);
        int textFieldYAxisGap = 10, yAxisGap = 10;
        for (int i = 0 ; i < bookLabel.length ; i++) {
            bookLabel[i] = new JLabel(labelNamesString[i]);
            bookLabel[i].setBounds(10,yAxisGap,100,30);
            textFieldYAxisGap+=i==5?60:0;
            if (i == 5) {
                bookCourseComboBox.setBounds(120, yAxisGap+1, 200, 27);
            }
            if ( i == 6) {
                bookDateChooser.setBounds(120, yAxisGap+1, 200, 27);
            }
            if (i < bookTextField.length) {
                bookTextField[i] = new JTextField(50);
                bookTextField[i].setBounds(120, textFieldYAxisGap, 200, 30);
                newBookPanel.add(bookTextField[i]);
            }
            newBookPanel.add(bookCourseComboBox);
            newBookPanel.add(bookLabel[i]);
            yAxisGap+=30;
            textFieldYAxisGap+=30;
            if (i == 0 || i == 5 || i == 6)
                bookTextField[i].addKeyListener(this);
        }
        bookTextField[0].setText(String.valueOf(latestAccessionId));
        bookTextField[0].setEnabled(false);
        bookTextField[0].setDisabledTextColor(Color.BLACK);
        accessionIdCheckBox.setFocusable(false);
        addBookButton.setBounds(120,bookTextField[bookTextField.length-1].getY()+5+30,90,30);
        clearBookButton.setBounds(addBookButton.getX()+addBookButton.getWidth()+20,addBookButton.getY(),addBookButton.getWidth(),addBookButton.getHeight());
        accessionIdCheckBox.setBounds(5,addBookButton.getY()+30,accessionIdCheckBox.getPreferredSize().width ,30);
        addBookButton.addActionListener(this);
        clearBookButton.addActionListener(this);
        accessionIdCheckBox.addItemListener(this);
        newBookPanel.add(addBookButton);
        newBookPanel.add(clearBookButton);
        newBookPanel.add(bookDateChooser);
        newBookPanel.add(accessionIdCheckBox);
    }

    public void initializeIssuedBookFormPanel() {
        JTextField[] issuedBookTextField = new JTextField[4];
        String[] labelString = {"Student Id", "Student Name", "Book Id", "Book Name", "Issue Date"};
        String[] buttonLabel = {"Submit","Clear"};
        JLabel[] issuedBookLabel = new JLabel[labelString.length];
        JDateChooser issuedBookDateChooser = new JDateChooser();
        JButton[] issuedBookButton = new JButton[2];
        String confirmationString = "I agree, all above details are correct.";
        JCheckBox confirmationCheckBox = new JCheckBox(confirmationString);
        int yAxisGap = 10, xAxisGapButton = 120;
        for(int i = 0 ; i < labelString.length ; i++) {
            issuedBookLabel[i] = new JLabel(labelString[i]);
            issuedBookLabel[i].setBounds(10,yAxisGap,100,30);
            issuedBookPanel.add(issuedBookLabel[i]);
            if (i < issuedBookTextField.length) {
                issuedBookTextField[i] = new JTextField(20);
                issuedBookTextField[i].setBounds(120,yAxisGap,200,30);
                issuedBookPanel.add(issuedBookTextField[i]);
            }
            /*if (i < issuedBookDateChooser.length) {
                issuedBookDateChooser[i] = new JDateChooser();
                issuedBookDateChooser[i].setBounds(120,yAxisGapDC,200,30);
                issuedBookPanel.add(issuedBookDateChooser[i]);
                yAxisGapDC += 30;
            }**/
            if (i < issuedBookButton.length) {
                issuedBookButton[i] = new JButton(buttonLabel[i]);
                issuedBookButton[i].setBounds(xAxisGapButton,135,95,30);
                issuedBookPanel.add(issuedBookButton[i]);
                xAxisGapButton += 105;
            }
            yAxisGap += 30;
        }
        issuedBookDateChooser.setBounds(120,issuedBookTextField[issuedBookTextField.length-1].getY()+30,200,30);
        issuedBookTextField[1].setText("Namaste");
        issuedBookTextField[3].setText("Java");
        issuedBookTextField[1].setEditable(false);
        issuedBookTextField[3].setEditable(false);
        confirmationCheckBox.setBounds(5,165,300,30);
        issuedBookPanel.add(confirmationCheckBox);
        
    }

    public void initializeAllotBookFormPanel() {
        
    }

    public void initializeStudentObject() {
        std = new Student();
        std.setStudentId(0);
        std.setStudentName(studentInfoTextField[0].getText());
        std.setFatherName(studentInfoTextField[1].getText());
        std.setCourse(courseComboBox.getSelectedIndex());
        std.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(studentDateChooser.getDate()));
        std.setGender(getGenderSelected());
        std.setPhoneNumber(studentInfoTextField[2].getText());
        std.setAddress(studentInfoTextField[3].getText());
        System.out.println(std);
    }

    public void initializeBookObject() {
        book = new Book();
        book.setAccessionId(Integer.parseInt(bookTextField[0].getText()));
        book.setTitle(bookTextField[1].getText());
        book.setAuthor(bookTextField[2].getText());
        book.setPublisher(bookTextField[3].getText());
        book.setEdition(bookTextField[4].getText());
        book.setCourse(bookCourseComboBox.getSelectedIndex());
        book.setDate(new SimpleDateFormat("yyyy-MM-dd").format(bookDateChooser.getDate()));
        book.setQuantity(Integer.parseInt(bookTextField[5].getText()));
        book.setPrice(Double.parseDouble(bookTextField[6].getText()));
        System.out.println(book);
    }

    public void clearStudentForm() {
        for (JTextField jTextField : studentInfoTextField) jTextField.setText("");
        genderButtonGroup.clearSelection();
        courseComboBox.setSelectedIndex(0);
        studentDateChooser.setDate(null);
    }

    public void clearBookForm() {
        for (JTextField jTextField : bookTextField)
            jTextField.setText("");
        if (bookCourseComboBox.getSelectedIndex() != 0)
            bookCourseComboBox.setSelectedIndex(0);
        if (accessionIdCheckBox.isSelected())
            accessionIdCheckBox.setSelected(false);
        bookTextField[0].setText(String.valueOf(latestAccessionId));
    }

    public char getGenderSelected() {
        return male.isSelected()?'M':'F';
    }

    public boolean studentFormValidation() {
        boolean validate = true;
        for (JTextField jTextField : studentInfoTextField) {
            if (jTextField.getText().equals("") || jTextField.getText().length() < 3) {
                validate = false;
                break;
            }
        }
        validate = validate && (male.isSelected() || female.isSelected());
        if (courseComboBox.getSelectedIndex() == 0)
            validate = false;
        if (studentDateChooser.getDate() == null)
            validate = false;
        System.out.println(validate ? "true" : "false");
        return validate;
    }

    public boolean bookFormValidation() {
        boolean validate = true;
        for (int i = 1 ; i < bookTextField.length ; i++) {
            if (i != 5 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 3)) {
                validate = false;
                break;
            }
            if (i == 5 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 1)) {
                validate = false;
                break;
            }
        }
        if (accessionIdCheckBox.isSelected()) {
            if (bookTextField[0].getText().equals("") || bookTextField[0].getText().length() < 3) {
                validate = false;
            }
        }
        if (bookCourseComboBox.getSelectedIndex() == 0)
            validate = false;
        if (bookDateChooser.getDate() == null)
            validate = false;
        if (accessionIdCheckBox.isSelected() && bookTextField[0].getText().equals(String.valueOf(latestAccessionId)))
            validate = false;
        System.out.println(validate ? "true" : "false");
        return validate;
    }

    public boolean generateStudentObjectQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO student (`Student Name`, `Father Name`, Course, `Date Of Birth`, Gender, `Phone Number`, Address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            // Set the values for the placeholders in the statement
            preStmt.setString(1, std.getStudentName());
            preStmt.setString(2, std.getFatherName());
            preStmt.setInt(3, std.getCourse());
            preStmt.setString(4, std.getDateOfBirth());
            preStmt.setString(5, std.getGender());
            preStmt.setString(6, std.getPhoneNumber());
            preStmt.setString(7, std.getAddress());

            // Execute the statement to insert the data
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            System.out.println(e.getMessage());
        }
        return validate;
    }

    public boolean generateBookObjectQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO book (`Accession Id`, Title, Author, Publisher, Edition, Course, Date, Quantity, Price) "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            preStmt.setInt(1,book.getAccessionId());
            preStmt.setString(2,book.getTitle());
            preStmt.setString(3,book.getAuthor());
            preStmt.setString(4,book.getPublisher());
            preStmt.setString(5,book.getEdition());
            preStmt.setInt(6,book.getCourse());
            preStmt.setString(7,book.getDate());
            preStmt.setInt(8,book.getQuantity());
            preStmt.setDouble(9,book.getPrice());

            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            System.out.println(e.getMessage());
        }
        return validate;
    }
    
    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (addStudentButton == e.getSource()) {
            if (studentFormValidation()) {
                initializeStudentObject();
                if (generateStudentObjectQuery())
                    clearStudentForm();
                else
                    System.out.println("false");
            }
            else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        if (clearStudentButton == e.getSource())
            clearStudentForm();
        if (addBookButton == e.getSource()) {
            if (bookFormValidation()) {
                initializeBookObject();
                if (generateBookObjectQuery())
                    clearBookForm();
                else
                    System.out.println("Action Listener false");
            }
            else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        if (clearBookButton == e.getSource()) {
            clearBookForm();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == studentInfoTextField[studentInfoTextField.length-2]) { //textField validation for phone number
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
            if (studentInfoTextField[studentInfoTextField.length-2].getText().length() >= 10)
                e.consume();
        }
        if (e.getSource() == bookTextField[0]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[5]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[6]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == '.' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[6]) {
            if (e.getKeyChar() == '.') {
                if (bookTextField[6].getText().contains(".")) {
                    e.consume();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            bookTextField[0].setEnabled(true);
        } else {
            bookTextField[0].setEnabled(false);
            bookTextField[0].setText(String.valueOf(latestAccessionId));
        }
        //bookTextField[0].setEnabled(e.getStateChange() == ItemEvent.SELECTED);
    }
}