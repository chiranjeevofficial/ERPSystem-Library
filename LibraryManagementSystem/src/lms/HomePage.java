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

public class HomePage implements ActionListener, KeyListener, ItemListener, FocusListener {
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
    private Student student = new Student();
    private Book book = new Book();
    private final IssuedBook issuedBook = new IssuedBook();
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
    private JButton[] issuedBookButton;
    private JTextField[] bookTextField, issuedBookTextField;
    JCheckBox accessionIdCheckBox;
    int getLatestAccessionId;

    public HomePage() { // Non-Parameterized Constructor
        con = util.getConnectionWithMySQL("library","root","admin@2023");
        JFrame mainFrame = new JFrame("Home Page");
        JTabbedPane mainTabbedPanel = new JTabbedPane();
        mainTabbedPanel.setSize(600,450);
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
        BrahmasmiLiabrary.util.setMainFrame(mainFrame, mainTabbedPanel.getWidth()+20, mainTabbedPanel.getHeight()+40);
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
        studentInfoTextField[0].addFocusListener(this);
        studentInfoTextField[0].addKeyListener(this);
        studentInfoTextField[1].addFocusListener(this);
        studentInfoTextField[1].addKeyListener(this);
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
        setLatestAccessionID();
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
            if (i >=1 && i <= 4) {
                bookTextField[i].addFocusListener(this);
                bookTextField[i].addKeyListener(this);
            }
        }
        bookTextField[0].setText(String.valueOf(getLatestAccessionId));
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
        String[] labelString = {"Student Id", "Accession Id"};
        String[] buttonString = {"Go", "Clear", "Issue", "Re Search"};
        JLabel[] issuedBookLabel = new JLabel[labelString.length];
        issuedBookTextField = new JTextField[issuedBookLabel.length];
        issuedBookButton = new JButton[buttonString.length];

        int xAxisGap = 10, xAxisGapButton = 410;
        for(int i = 0 ; i < labelString.length ; i++) {
            issuedBookLabel[i] = new JLabel(labelString[i]);
            issuedBookLabel[i].setBounds(xAxisGap,10,80,30);
            issuedBookPanel.add(issuedBookLabel[i]);
            
            issuedBookTextField[i] = new JTextField(20);
            issuedBookTextField[i].setBounds(xAxisGap+80,10,100,30);
            issuedBookPanel.add(issuedBookTextField[i]);

            issuedBookButton[i] = new JButton(buttonString[i]);
            issuedBookButton[i].setBounds(xAxisGapButton,10,issuedBookButton[i].getPreferredSize().width,30);
            issuedBookButton[i].addActionListener(this);
            issuedBookPanel.add(issuedBookButton[i]);

            issuedBookButton[i+2] = new JButton(buttonString[i+2]);
            issuedBookButton[i+2].addActionListener(this);
            issuedBookPanel.add(issuedBookButton[i+2]);
            xAxisGap += 200;
            xAxisGapButton += 70;
        }
    }

    public void setLatestAccessionID() {
        String query = "SELECT MAX(`Accession ID`) FROM book;";
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                getLatestAccessionId = resultSet.getInt(1);
            }
            getLatestAccessionId +=1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLatestStudentID() {
        String query = "SELECT MAX(`Student ID`) FROM STUDENT;";
        int latestStudentId = 0;
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                latestStudentId = resultSet.getInt(1);
            }
            latestStudentId+=1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestStudentId;
    }

    public int getLatestIssuedId() {
        String query = "SELECT MAX(`Issued ID`) FROM issued;";
        int latestIssuedId = 0;
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                latestIssuedId = resultSet.getInt(1);
            }
            latestIssuedId+=1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestIssuedId;
    }

    public void initializeStudentObject() {
        student = new Student();
        student.setStudentId(getLatestStudentID());
        student.setStudentName(studentInfoTextField[0].getText());
        student.setFatherName(studentInfoTextField[1].getText());
        student.setCourse(courseComboBox.getSelectedIndex());
        student.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(studentDateChooser.getDate()));
        student.setGender(getGenderSelected());
        student.setPhoneNumber(studentInfoTextField[2].getText());
        student.setAddress(studentInfoTextField[3].getText());
        System.out.println(student);
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

    

    public Object clearStudentForm() {
        for (JTextField jTextField : studentInfoTextField) jTextField.setText("");
        genderButtonGroup.clearSelection();
        courseComboBox.setSelectedIndex(0);
        studentDateChooser.setDate(null);
        return null;
    }

    public Object clearBookForm() {
        for (JTextField jTextField : bookTextField)
            jTextField.setText("");
        if (bookCourseComboBox.getSelectedIndex() != 0)
            bookCourseComboBox.setSelectedIndex(0);
        if (accessionIdCheckBox.isSelected())
            accessionIdCheckBox.setSelected(false);
        setLatestAccessionID();
        bookTextField[0].setText(String.valueOf(getLatestAccessionId));
        return null;
    }

    public void clearIssuedBookForm() {
        for (JTextField textField : issuedBookTextField)
            textField.setText("");
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
            if (bookTextField[0].getText().equals("") || bookTextField[0].getText().length() < 1) {
                validate = false;
            }
        }
        if (bookCourseComboBox.getSelectedIndex() == 0)
            validate = false;
        if (bookDateChooser.getDate() == null)
            validate = false;
        if (accessionIdCheckBox.isSelected() && bookTextField[0].getText().equals(String.valueOf(getLatestAccessionId)))
            validate = false;
        System.out.println(validate ? "true" : "false");
        return validate;
    }

    public boolean issuedBookFormValidation() {
        boolean validate = true;
        for (JTextField jTextField : issuedBookTextField) {
            if (jTextField.getText().equals("")) {
                validate = false;
                break;
            }
        }
        System.out.println(validate?"true":"false");
        return validate;
    }

    public boolean generateStudentObjectQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO student (`Student Id`, `Student Name`, `Father Name`, Course, `Date Of Birth`, Gender, `Phone Number`, Address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            // Set the values for the placeholders in the statement
            preStmt.setInt(1, student.getStudentId());
            preStmt.setString(2, student.getStudentName());
            preStmt.setString(3, student.getFatherName());
            preStmt.setInt(4, student.getCourse());
            preStmt.setString(5, student.getDateOfBirth());
            preStmt.setString(6, student.getGender());
            preStmt.setString(7, student.getPhoneNumber());
            preStmt.setString(8, student.getAddress());

            // Execute the statement to insert the data
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            System.out.println(e.getMessage());
        }
        return validate;
    }

    public boolean generateBookObjectQuery() {
        book.setAccessionId(Integer.parseInt(bookTextField[0].getText()));
        boolean validate = true;
        try {
            String query = "INSERT INTO book (`Accession Id`, Title, Author, Publisher, Edition, Course, Date, Price) "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            preStmt.setInt(1,book.getAccessionId());
            preStmt.setString(2,book.getTitle());
            preStmt.setString(3,book.getAuthor());
            preStmt.setString(4,book.getPublisher());
            preStmt.setString(5,book.getEdition());
            preStmt.setInt(6,book.getCourse());
            preStmt.setString(7,book.getDate());
            preStmt.setDouble(8,book.getPrice());

            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            System.out.println(e.getMessage());
        }
        return validate;
    }

    public boolean generateIssuedBookObjectQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO issued (`Issued Id`, `Student Id`, `Accession Id`, `Issued Date`) " +
                    "VALUES (?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1,issuedBook.getIssuedId());
            preStmt.setInt(2,issuedBook.getStudentId());
            preStmt.setInt(3,issuedBook.getAccessionId());
            preStmt.setString(4,issuedBook.getIssuedDate());
            System.out.println(issuedBook);

            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return validate;
    }

    public boolean findStudentThroughStudentId() {
        boolean validate = true;
        String query = "SELECT `Student Name`, `Father Name` FROM student WHERE `Student Id` = ?";
        try {
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, Integer.parseInt(issuedBookTextField[0].getText()));
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                student.setStudentId(Integer.parseInt(issuedBookTextField[0].getText()));
                student.setStudentName(resultSet.getString("Student Name"));
                student.setFatherName(resultSet.getString("Father Name"));
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid student ID");
                validate = false;
            }
        } catch (SQLException sqlException) {
            validate = false;
            sqlException.printStackTrace();
        }
        return validate;
    }

    public boolean findBookThroughAccessionId() {
        boolean validate = true;
        String query = "SELECT `Title`, `Author` FROM book WHERE `Accession Id` = ?";
        try {
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, Integer.parseInt(issuedBookTextField[1].getText()));
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                book.setAccessionId(Integer.parseInt(issuedBookTextField[1].getText()));
                book.setTitle(resultSet.getString("Title"));
                book.setAuthor(resultSet.getString("Author"));
            }
            else {
                validate = false;
                JOptionPane.showMessageDialog(null, "Invalid Accession ID");
            }
        } catch (SQLException sqlException) {
            validate = false;
            sqlException.printStackTrace();
        }
        return validate;
    }

    public boolean generateIssuedBookInnerPanel() {
        boolean validate;
        validate = findStudentThroughStudentId();
        validate = findBookThroughAccessionId() && validate;
        if (validate) {
            String[] labelString = {"Student Id", "Student Name", "Father Name", "Issue Date", "Accession No", "Title", "Author"};
            JLabel[] issuedBookInnerLabel = new JLabel[labelString.length];
            JTextField[] issuedBookInnerTextField = new JTextField[issuedBookInnerLabel.length-1];
            JDateChooser issuedBookInnerDateChooser = new JDateChooser(new Date());
            int yAxis = 100, xAxis = 170;
            for (int i = 0 ; i < labelString.length ; i++) {
                if (i < 2) {
                    issuedBookTextField[i].setEnabled(false);
                    issuedBookButton[i].setEnabled(false);
                    issuedBookButton[i+2].setBounds(xAxis, 300, 100, 30);
                    xAxis += 130;
                }
                if (i < 3) {
                    issuedBookInnerLabel[i] = new JLabel(labelString[i]);
                    issuedBookInnerLabel[i].setBounds(10,yAxis,100,30);
                    issuedBookPanel.add(issuedBookInnerLabel[i]);
                    issuedBookInnerTextField[i] = new JTextField(50);
                    issuedBookInnerTextField[i].setBounds(120,yAxis,150,30);
                    issuedBookInnerTextField[i].setEnabled(false);
                    issuedBookInnerTextField[i].setDisabledTextColor(Color.BLACK);
                    issuedBookPanel.add(issuedBookInnerTextField[i]);

                    issuedBookInnerLabel[i+4] = new JLabel(labelString[i+4]);
                    issuedBookInnerLabel[i+4].setBounds(300,yAxis,100,30);
                    issuedBookPanel.add(issuedBookInnerLabel[i+4]);
                    issuedBookInnerTextField[i+3] = new JTextField(50);
                    issuedBookInnerTextField[i+3].setBounds(390,yAxis,150,30);
                    issuedBookInnerTextField[i+3].setEnabled(false);
                    issuedBookInnerTextField[i+3].setDisabledTextColor(Color.BLACK);
                    issuedBookPanel.add(issuedBookInnerTextField[i+3]);
                    yAxis += 30;
                }
                if (i == 3) {
                    issuedBookInnerLabel[i] = new JLabel(labelString[i]);
                    issuedBookInnerLabel[i].setBounds(10,yAxis+30,100,30);
                    issuedBookPanel.add(issuedBookInnerLabel[i]);
                    issuedBookInnerDateChooser.setBounds(120,yAxis+30,150,27);
                    issuedBookInnerDateChooser.setEnabled(false);
                    JTextField textField = (JTextField) issuedBookInnerDateChooser.getDateEditor().getUiComponent();
                    textField.setDisabledTextColor(Color.BLACK);
                    issuedBookPanel.add(issuedBookInnerDateChooser);
                    yAxis += 30;
                }
            }
            issuedBook.setIssuedId(getLatestIssuedId());
            issuedBook.setStudentId(student.getStudentId());
            issuedBook.setAccessionId(book.getAccessionId());
            issuedBook.setIssuedDate(new SimpleDateFormat("yyyy-MM-dd").format(issuedBookInnerDateChooser.getDate()));

            issuedBookInnerTextField[0].setText(String.valueOf(student.getStudentId()));
            issuedBookInnerTextField[1].setText(student.getStudentName());
            issuedBookInnerTextField[2].setText(student.getFatherName());
            issuedBookInnerTextField[3].setText(String.valueOf(book.getAccessionId()));
            issuedBookInnerTextField[4].setText(book.getTitle());
            issuedBookInnerTextField[5].setText(book.getAuthor());
            issuedBookPanel.revalidate();
            issuedBookPanel.repaint();
        } else {
            clearIssuedBookForm();
        }
        return validate;
    }

    public void toStringValidate(@NotNull JTextField textField) {
        textField.setText(textField.getText().trim());
        String str = textField.getText();
        String[] words = str.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            words[i] = capitalizedWord;
        }
        str = String.join(" ", words);
        textField.setText(str);
        System.out.println(str);
    }

    public Object clearIssuedBookInnerForm() {
        for (int i = 0 ; i < 2 ; i++) {
            issuedBookPanel.removeAll();
            issuedBookPanel.revalidate();
            issuedBookPanel.repaint();
            initializeIssuedBookFormPanel();
        }
        return null;
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (addStudentButton == e.getSource()) {
            if (studentFormValidation()) {
                initializeStudentObject();
                System.out.println(generateStudentObjectQuery()?clearStudentForm():"false");
            }
            else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }

        if (clearStudentButton == e.getSource()) {
            clearStudentForm();
        }

        if (addBookButton == e.getSource()) {
            if (bookFormValidation()) {
                initializeBookObject();
                for (int i = book.getQuantity() ; i != 0 ; i--)
                    System.out.println(generateBookObjectQuery()?clearBookForm():"false");
            }
            else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        
        if (clearBookButton == e.getSource()) {
            clearBookForm();
        }
        
        if (e.getSource() == issuedBookButton[0]) {
            if (issuedBookFormValidation()) {
                if (generateIssuedBookInnerPanel()) {
                    clearIssuedBookForm();
                }
            } else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        if (e.getSource() == issuedBookButton[1])
            clearIssuedBookForm();
        if (e.getSource() == issuedBookButton[2]) {
            System.out.println(generateIssuedBookObjectQuery()?clearIssuedBookInnerForm():"false");
        }
        if (e.getSource() == issuedBookButton[3]) {
            clearIssuedBookInnerForm();
        }
    }

    @Override
    public void keyTyped(@NotNull KeyEvent e) {
        if (e.getSource() == studentInfoTextField[studentInfoTextField.length-2]) { //textField validation for phone number
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
            if (studentInfoTextField[studentInfoTextField.length-2].getText().length() >= 10)
                e.consume();
        }
        if (e.getSource() == studentInfoTextField[0] || e.getSource() == studentInfoTextField[1] || e.getSource() == bookTextField[1] || e.getSource() == bookTextField[2] || e.getSource() == bookTextField[3] || e.getSource() == bookTextField[4]) {
            char ch = e.getKeyChar();                       
            if (!(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_SPACE))
                e.consume();
        }
        if (e.getSource() == bookTextField[0] || e.getSource() == bookTextField[5]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[6]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == '.' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
            if (e.getKeyChar() == '.') {
                if (bookTextField[6].getText().contains(".")) {
                    e.consume();
                }
            }
        }
        if (e.getSource() == issuedBookTextField[0] || e.getSource() == issuedBookTextField[1]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void itemStateChanged(@NotNull ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            bookTextField[0].setEnabled(true);
        } else {
            bookTextField[0].setEnabled(false);
            bookTextField[0].setText(String.valueOf(getLatestAccessionId));
        }
        //bookTextField[0].setEnabled(e.getStateChange() == ItemEvent.SELECTED);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == studentInfoTextField[0]) {
            studentInfoTextField[0].setText("");
        }
        if (e.getSource() == studentInfoTextField[1]) {
            studentInfoTextField[1].setText("");
        }
    }

    @Override
    public void focusLost(@NotNull FocusEvent e) {
        if (e.getSource() == studentInfoTextField[0]) {
            if(!studentInfoTextField[0].getText().isEmpty())
                toStringValidate(studentInfoTextField[0]);
        }
        if (e.getSource() == studentInfoTextField[1]) {
            if(!studentInfoTextField[1].getText().isEmpty())
                toStringValidate(studentInfoTextField[1]);
        }
        if (e.getSource() == bookTextField[1]) {
            if(!bookTextField[1].getText().isEmpty())
                toStringValidate(bookTextField[1]);
        }
        if (e.getSource() == bookTextField[2]) {
            if(!bookTextField[2].getText().isEmpty())
                toStringValidate(bookTextField[2]);
        }
        if (e.getSource() == bookTextField[3]) {
            if(!bookTextField[3].getText().isEmpty())
                toStringValidate(bookTextField[3]);
        }
        if (e.getSource() == bookTextField[4]) {
            if(!bookTextField[4].getText().isEmpty())
                toStringValidate(bookTextField[4]);
        }
    }
}