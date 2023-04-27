package lms;

import BrahmasmiLiabrary.util;
import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class HomePage implements ActionListener, KeyListener {
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
    private JDateChooser dobDateChooser;
    private final JButton addStudentButton = new JButton("Submit");
    private final JButton clearStudentButton = new JButton("Clear");
    private final JButton addBookButton = new JButton("Submit");
    private final JButton clearBookButton = new JButton("Clear");
    private JTextField[] bookTextField;

    public HomePage() { // Non-Parameterized Constructor
        con = util.getConnectionWithMySQL("library","root","admin@2023");
        JFrame mainFrame = new JFrame("Home Page");
        JTabbedPane mainTabbedPanel = new JTabbedPane();
        mainTabbedPanel.setSize(700,400);
        newStudentPanel = new JPanel(null);
        newBookPanel = new JPanel(null);
        issuedBookPanel = new JPanel(null);
        mainTabbedPanel.add("Add Student", newStudentPanel);
        mainTabbedPanel.add("Add Book", newBookPanel);
        mainTabbedPanel.add("Issue Book",issuedBookPanel);
        mainTabbedPanel.setSelectedIndex(2);
        initializeNewStudentFormPanel();
        initializeNewBookFormPanel();
        initializeIssuedBookFormPanel();
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
        dobDateChooser = new JDateChooser();
        dobDateChooser.getDateEditor().setEnabled(false);

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
        dobDateChooser.setBounds(120,studentInfoTextField[1].getY()+60,200,27);
        addStudentButton.setBounds(studentInfoTextField[studentInfoTextField.length-1].getX(),studentInfoTextField[studentInfoTextField.length-1].getY()+35,90,30);
        clearStudentButton.setBounds(addStudentButton.getX()+110, addStudentButton.getY(),addStudentButton.getWidth(),addStudentButton.getHeight());
        addStudentButton.addActionListener(this);
        clearStudentButton.addActionListener(this);
        newStudentPanel.add(courseComboBox);
        newStudentPanel.add(addStudentButton);
        newStudentPanel.add(clearStudentButton);
        newStudentPanel.add(dobDateChooser);
    }

    void initializeNewBookFormPanel() {
        String[] labelNames = {"Title","Author","Publisher","Edition","Course","Quantity","Price"};
        JLabel[] bookLabel = new JLabel[labelNames.length];
        bookCourseComboBox = new JComboBox<>(course);
        bookTextField = new JTextField[6];
        int textFieldYAxisGap = 10, yAxisGap = 10;
        for (int i = 0 ; i < bookLabel.length ; i++) {
            bookLabel[i] = new JLabel(labelNames[i]);
            bookLabel[i].setBounds(10,yAxisGap,100,30);
            if (i == 4) {
                bookCourseComboBox.setBounds(120, yAxisGap+1, 200, 27);
                textFieldYAxisGap+=30;
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
        }
        addBookButton.setBounds(120,bookTextField[bookTextField.length-1].getY()+5+30,90,30);
        clearBookButton.setBounds(addBookButton.getX()+addBookButton.getWidth()+20,addBookButton.getY(),addBookButton.getWidth(),addBookButton.getHeight());
        addBookButton.addActionListener(this);
        clearBookButton.addActionListener(this);
        bookTextField[4].addKeyListener(this);
        bookTextField[5].addKeyListener(this);
        newBookPanel.add(addBookButton);
        newBookPanel.add(clearBookButton);
    }

    public void initializeIssuedBookFormPanel() {
        JTextField[] issuedBookTextField = new JTextField[2];
        String[] labelString = {"Student Id", "Book Id", "Issue Date", "Due Date"};
        String[] buttonLabel = {"Submit","Clear"};
        JLabel[] issuedBookLabel = new JLabel[labelString.length];
        JDateChooser[] issuedBookDateChooser = new JDateChooser[2];
        JButton[] issuedBookButton = new JButton[2];
        int yAxisGap = 10, yAxisGapDC = 70, xAxisGapButton = 120;
        for(int i = 0 ; i < labelString.length ; i++) {
            issuedBookLabel[i] = new JLabel(labelString[i]);
            issuedBookLabel[i].setBounds(10,yAxisGap,100,30);
            issuedBookPanel.add(issuedBookLabel[i]);
            if (i < issuedBookTextField.length) {
                issuedBookTextField[i] = new JTextField(20);
                issuedBookTextField[i].setBounds(120,yAxisGap,200,30);
                issuedBookPanel.add(issuedBookTextField[i]);
            }
            if (i < issuedBookDateChooser.length) {
                issuedBookDateChooser[i] = new JDateChooser();
                issuedBookDateChooser[i].setBounds(120,yAxisGapDC,200,30);
                issuedBookPanel.add(issuedBookDateChooser[i]);
                yAxisGapDC += 30;
            }
            if (i < issuedBookButton.length) {
                issuedBookButton[i] = new JButton(buttonLabel[i]);
                issuedBookButton[i].setBounds(xAxisGapButton,135,95,30);
                issuedBookPanel.add(issuedBookButton[i]);
                xAxisGapButton += 105;
            }
            yAxisGap += 30;
        }
    }

    public void initializeStudentObject() {
        std = new Student();
        std.setStudentId(0);
        std.setStudentName(studentInfoTextField[0].getText());
        std.setFatherName(studentInfoTextField[1].getText());
        std.setCourse(courseComboBox.getSelectedIndex());
        std.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(dobDateChooser.getDate()));
        std.setGender(getGenderSelected());
        std.setPhoneNumber(studentInfoTextField[2].getText());
        std.setAddress(studentInfoTextField[3].getText());
        System.out.println(std);
    }

    public void initializeBookObject() {
        book = new Book();
        book.setBook_id(0);
        book.setTitle(bookTextField[0].getText());
        book.setAuthor(bookTextField[1].getText());
        book.setPublisher(bookTextField[2].getText());
        book.setEdition(bookTextField[3].getText());
        book.setCourse(bookCourseComboBox.getSelectedIndex());
        book.setQuantity(Integer.parseInt(bookTextField[4].getText()));
        book.setPrice(Double.parseDouble(bookTextField[5].getText()));
        System.out.println(book);
    }

    public void clearStudentForm() {
        for (JTextField jTextField : studentInfoTextField) jTextField.setText("");
        genderButtonGroup.clearSelection();
        courseComboBox.setSelectedIndex(0);
        dobDateChooser.setDate(null);
    }

    public void clearBookForm() {
        for (JTextField jTextField : bookTextField)
            jTextField.setText("");
        if (bookCourseComboBox.getSelectedIndex() != 0)
            bookCourseComboBox.setSelectedIndex(0);
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
        if (dobDateChooser.getDate() == null)
            validate = false;
        System.out.println(validate ? "true" : "false");
        return validate;
    }

    public boolean bookFormValidation() {
        boolean validate = true;
        for (int i = 0 ; i < bookTextField.length ; i++) {
            if (i != 4 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 3)) {
                validate = false;
                break;
            }
            if (i == 4 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 1)) {
                validate = false;
                break;
            }
        }
        if (bookCourseComboBox.getSelectedIndex() == 0)
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
            String query = "INSERT INTO book (title, author, publisher, edition, course, quantity, price) "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            preStmt.setString(1,book.getTitle());
            preStmt.setString(2,book.getAuthor());
            preStmt.setString(3,book.getPublisher());
            preStmt.setString(4,book.getEdition());
            preStmt.setInt(5,book.getCourse());
            preStmt.setInt(6,book.getQuantity());
            preStmt.setDouble(7,book.getPrice());

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
        if (e.getSource() == bookTextField[4]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[5]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == '.' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {

    }
}