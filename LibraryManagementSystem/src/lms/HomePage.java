package lms;

import BrahmasmiLiabrary.util;
import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage implements ActionListener, KeyListener, ItemListener, FocusListener, ChangeListener {
    private final Connection con;
    private PreparedStatement preStmt;
    String[] courseString = {
            "Select the course",
            "B.Sc Food Technology",
            "B.Sc Home Science",
            "B.Sc Information Technology",
            "Bachelor of Arts",
            "Bachelor of Arts in Yoga",
            "Bachelor of Commerce"},
            buttonString = {"Submit", "Clear"};
    private final Student student = Student.getInstance();
    private final Book book = Book.getInstance();
    private final Borrow borrow = Borrow.getInstance();
    private final IssuedBook issuedBook = IssuedBook.getInstance();
    private final JTabbedPane mainTabbedPane;
    private final JPanel[] tabbedPane = new JPanel[8];
    private final JDateChooser[] dateChooser = new JDateChooser[5];
    private JLabel[] returnBookLabels;
    private JButton[] newStudentButtons, newBookButtons, issuedBookButton, returnBookButtons;
    private JTextField[] studentInfoTextField, bookTextField, issuedBookTextField, returnBookTextFields;
    private JComboBox<String> studentCourseComboBox, bookCourseComboBox;
    private ButtonGroup genderButtonGroup;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JCheckBox accessionIdCheckBox;

    public HomePage() {
        String[] tabbedPaneString = {
                "Add Student",
                "Add Book",
                "Issue Book",
                "Return Book",
                "Show Student",
                "Show Book",
                "Show Issued Book",
                "Book History"
        };
        con = util.getConnectionWithMySQL("library", "root", "admin@2023");
        JFrame mainFrame = new JFrame("Home Page");
        util.setMainFrame(mainFrame, util.getScreenDimension().width, util.getScreenDimension().height);
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setSize(mainFrame.getWidth(), mainFrame.getHeight());
        for (int i = 0; i < tabbedPane.length; i++) {
            tabbedPane[i] = new JPanel(null);
            mainTabbedPane.add(tabbedPaneString[i], tabbedPane[i]);
        }
        mainTabbedPane.setSelectedIndex(0);
        mainTabbedPane.addChangeListener(this);
        initNewStudentPanel();
        initNewBookPanel();
        initIssuedBookPanel();
        initShowBookTablePanel();
        initReturnBookPanel();
        initShowStudentTablePanel();
        initShowIssuedBookTablePanel();
        initShowLibraryHistoryTablePanel();
        mainFrame.add(mainTabbedPane);
        mainTabbedPane.revalidate();
        mainTabbedPane.repaint();
    }

    public void initNewStudentPanel() {
        tabbedPane[0].removeAll();
        String[] labelNames = {
                "Student Name",
                "Father Name",
                "Course",
                "Date of Birth",
                "Gender",
                "Phone Number",
                "Address"
        };
        JLabel[] newStudentLabel = new JLabel[7];
        newStudentButtons = new JButton[2];
        studentCourseComboBox = new JComboBox<>(courseString);
        studentCourseComboBox.setSelectedIndex(0);
        studentCourseComboBox.setBounds(120, 71, 200, 27);
        genderButtonGroup = new ButtonGroup();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        maleRadioButton.setBounds(120, 130, 100, 30);
        femaleRadioButton.setBounds(220, 130, 100, 30);
        tabbedPane[0].add(maleRadioButton);
        tabbedPane[0].add(femaleRadioButton);
        studentInfoTextField = new JTextField[4];
        dateChooser[0] = new JDateChooser();

        int labelYAxisGap = 10, textFieldYAxisGap = 10;
        for (int i = 0; i < labelNames.length; i++) {
            if (i == 0 || i == 1) {
                newStudentButtons[i] = new JButton(buttonString[i]);
                newStudentButtons[i].addActionListener(this);
                tabbedPane[0].add(newStudentButtons[i]);
            }
            newStudentLabel[i] = new JLabel(labelNames[i]);
            newStudentLabel[i].setBounds(10, labelYAxisGap, 100, 30);
            tabbedPane[0].add(newStudentLabel[i]);
            labelYAxisGap += newStudentLabel[i].getHeight();
            textFieldYAxisGap += i == 2 ? 90 : 0;
            if (i < studentInfoTextField.length) { // overtake the ArrayIndexOutOfBoundsException
                studentInfoTextField[i] = new JTextField(20);
                studentInfoTextField[i].setBounds(120, textFieldYAxisGap, 200, 30);
                tabbedPane[0].add(studentInfoTextField[i]);
                textFieldYAxisGap += 30;
            }
        }
        studentInfoTextField[0].addFocusListener(this);
        studentInfoTextField[0].addKeyListener(this);
        studentInfoTextField[1].addFocusListener(this);
        studentInfoTextField[1].addKeyListener(this);
        studentInfoTextField[studentInfoTextField.length - 2].addKeyListener(this);
        dateChooser[0].setBounds(120, studentInfoTextField[1].getY() + 60, 200, 27);
        newStudentButtons[0].setBounds(studentInfoTextField[studentInfoTextField.length - 1].getX(), studentInfoTextField[studentInfoTextField.length - 1].getY() + 35, 90, 30);
        newStudentButtons[1].setBounds(newStudentButtons[0].getX() + 110, newStudentButtons[0].getY(), newStudentButtons[0].getWidth(), newStudentButtons[0].getHeight());

        JTextField studentDateChooserTextField = (JTextField) dateChooser[0].getDateEditor().getUiComponent();
        studentDateChooserTextField.setDisabledTextColor(Color.BLACK);
        dateChooser[0].getDateEditor().setEnabled(false);

        tabbedPane[0].add(studentCourseComboBox);
        tabbedPane[0].add(dateChooser[0]);

        /* Start implementation of pie chart*/
        // Create a dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 1; i <= 6; i++) {
            if (getEnrolledStudentByCourseId(i) > 0)
                dataset.setValue(courseString[i], getEnrolledStudentByCourseId(i));
        }

        // Create a chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Student Enrollment By Course",
                dataset,
                true,
                true,
                false
        );

        // Create a ChartPanel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setBounds(500, 10, 600, 600);
        tabbedPane[0].add(chartPanel);

        revalidateAndRepaintJPanel(tabbedPane[0]);
    }

    public void initNewBookPanel() {
        String[] labelNamesString = {
                "Accession Id",
                "Title",
                "Author",
                "Publisher",
                "Edition",
                "Course",
                "Date",
                "Quantity",
                "Price"
        };
        bookCourseComboBox = new JComboBox<>(courseString);
        newBookButtons = new JButton[2];
        JLabel[] bookLabel = new JLabel[labelNamesString.length];
        bookCourseComboBox = new JComboBox<>(courseString);
        bookCourseComboBox.setSelectedIndex(0);
        bookTextField = new JTextField[bookLabel.length - 2];
        accessionIdCheckBox = new JCheckBox("Change Accession Number");
        dateChooser[1] = new JDateChooser();
        dateChooser[1].setDate(new Date());
        JTextField bookDateChooserTextField = (JTextField) dateChooser[1].getDateEditor().getUiComponent();
        bookDateChooserTextField.setDisabledTextColor(Color.BLACK);
        dateChooser[1].setEnabled(false);
        int textFieldYAxisGap = 10, yAxisGap = 10;
        for (int i = 0; i < bookLabel.length; i++) {
            if (i == 0 || i == 1) {
                newBookButtons[i] = new JButton(buttonString[i]);
                newBookButtons[i].addActionListener(this);
                tabbedPane[1].add(newBookButtons[i]);
            }
            bookLabel[i] = new JLabel(labelNamesString[i]);
            bookLabel[i].setBounds(10, yAxisGap, 100, 30);
            textFieldYAxisGap += i == 5 ? 60 : 0;
            if (i == 5) {
                bookCourseComboBox.setBounds(120, yAxisGap + 1, 200, 27);
            }
            if (i == 6) {
                dateChooser[1].setBounds(120, yAxisGap + 1, 200, 27);
            }
            if (i < bookTextField.length) {
                bookTextField[i] = new JTextField(50);
                bookTextField[i].setBounds(120, textFieldYAxisGap, 200, 30);
                tabbedPane[1].add(bookTextField[i]);
            }
            tabbedPane[1].add(bookLabel[i]);
            yAxisGap += 30;
            textFieldYAxisGap += 30;
            if (i == 0 || i == 5 || i == 6)
                bookTextField[i].addKeyListener(this);
            if (i >= 1 && i <= 4) {
                bookTextField[i].addFocusListener(this);
                bookTextField[i].addKeyListener(this);
            }
        }
        bookTextField[0].setText(String.valueOf(getLatestAccessionId()));
        bookTextField[0].setEnabled(false);
        bookTextField[0].setDisabledTextColor(Color.BLACK);
        accessionIdCheckBox.setFocusable(false);
        newBookButtons[0].setBounds(120, bookTextField[bookTextField.length - 1].getY() + 5 + 30, 90, 30);
        newBookButtons[1].setBounds(newBookButtons[0].getX() + newBookButtons[0].getWidth() + 20, newBookButtons[0].getY(), newBookButtons[0].getWidth(), newBookButtons[0].getHeight());
        accessionIdCheckBox.setBounds(5, newBookButtons[0].getY() + 30, accessionIdCheckBox.getPreferredSize().width, 30);
        accessionIdCheckBox.addItemListener(this);
        tabbedPane[1].add(dateChooser[1]);
        tabbedPane[1].add(bookCourseComboBox);
        tabbedPane[1].add(accessionIdCheckBox);
    }

    public void initIssuedBookPanel() {
        String[] labelString = {"Student Id", "Accession Id"};
        String[] buttonString = {"Go", "Clear", "Issue", "Re Search"};
        JLabel[] issuedBookLabel = new JLabel[labelString.length];
        dateChooser[2] = new JDateChooser();
        issuedBookTextField = new JTextField[issuedBookLabel.length];
        issuedBookButton = new JButton[buttonString.length];
        int xAxisGap = 10, xAxisGapButton = 410;
        for (int i = 0; i < labelString.length; i++) {
            issuedBookLabel[i] = new JLabel(labelString[i]);
            issuedBookLabel[i].setBounds(xAxisGap, 10, 80, 30);
            tabbedPane[2].add(issuedBookLabel[i]);

            issuedBookTextField[i] = new JTextField(20);
            issuedBookTextField[i].setBounds(xAxisGap + 80, 10, 100, 30);
            issuedBookTextField[i].addKeyListener(this);
            tabbedPane[2].add(issuedBookTextField[i]);

            issuedBookButton[i] = new JButton(buttonString[i]);
            issuedBookButton[i].setBounds(xAxisGapButton, 10, issuedBookButton[i].getPreferredSize().width, 30);
            issuedBookButton[i].addActionListener(this);
            tabbedPane[2].add(issuedBookButton[i]);

            issuedBookButton[i + 2] = new JButton(buttonString[i + 2]);
            issuedBookButton[i + 2].addActionListener(this);
            tabbedPane[2].add(issuedBookButton[i + 2]);
            xAxisGap += 200;
            xAxisGapButton += 70;
        }
    }

    public void initReturnBookPanel() {
        String[] labelString = {
                "Issued Id", "Issued Id", "Student Id", "Student Name",
                "Father Name", "Issued Date", "Accession Id", "Book Title",
                "Book Author", "Return Date", "Fine (If Applicable)"
        };
        String[] buttonString = {"Find", "Clear", "Return", "Re Search"};
        returnBookLabels = new JLabel[11];
        returnBookTextFields = new JTextField[9];
        returnBookButtons = new JButton[4];
        dateChooser[3] = new JDateChooser();
        dateChooser[4] = new JDateChooser();
        int xAxis = 10, yAxis = 10;
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                returnBookLabels[i] = new JLabel(labelString[i]);
                returnBookLabels[i].setBounds(xAxis, yAxis, 100, 30);
                tabbedPane[3].add(returnBookLabels[i]);
                xAxis += 120;

                returnBookTextFields[i] = new JTextField(50);
                returnBookTextFields[i].setBounds(xAxis, yAxis, 200, 30);
                returnBookTextFields[i].setDisabledTextColor(Color.BLACK);
                returnBookTextFields[i].addKeyListener(this);
                tabbedPane[3].add(returnBookTextFields[i]);
                xAxis += 270;

                returnBookButtons[i] = new JButton(buttonString[i]);
                returnBookButtons[i].setBounds(xAxis, yAxis, 90, 30);
                returnBookButtons[i].addActionListener(this);
                tabbedPane[3].add(returnBookButtons[i]);
                xAxis += 110;
                returnBookButtons[i + 1] = new JButton(buttonString[i + 1]);
                returnBookButtons[i + 1].setBounds(xAxis, yAxis, 90, 30);
                returnBookButtons[i + 1].addActionListener(this);
                tabbedPane[3].add(returnBookButtons[i + 1]);
                xAxis = 10;
                yAxis = 100;
            }
            if (i != 0) {
                if (i == 2 || i == 3) {
                    returnBookButtons[i] = new JButton(buttonString[i]);
                    returnBookButtons[i].addActionListener(this);
                    tabbedPane[3].add(returnBookButtons[i]);

                    returnBookLabels[i + 7] = new JLabel(labelString[i + 7]);
                    tabbedPane[3].add(returnBookLabels[i + 7]);
                }
                returnBookLabels[i] = new JLabel(labelString[i]);
                tabbedPane[3].add((returnBookLabels[i]));

                returnBookTextFields[i] = new JTextField(50);
                returnBookTextFields[i].setEnabled(false);
                returnBookTextFields[i].setDisabledTextColor(Color.BLACK);
                tabbedPane[3].add(returnBookTextFields[i]);

                returnBookLabels[i + 4] = new JLabel(labelString[i + 4]);
                tabbedPane[3].add((returnBookLabels[i + 4]));

                returnBookTextFields[i + 3] = new JTextField(50);
                returnBookTextFields[i + 3].setDisabledTextColor(Color.BLACK);
                returnBookTextFields[i + 3].setEnabled(false);
                tabbedPane[3].add(returnBookTextFields[i + 3]);
            }
        }
        returnBookTextFields[8] = new JTextField(50);
        returnBookTextFields[8].setEnabled(true);
        returnBookTextFields[8].addKeyListener(this);
        tabbedPane[3].add(returnBookTextFields[8]);
        dateChooser[4].setDate(new Date());
        dateChooser[4].setEnabled(false);
        JTextField textField = (JTextField) dateChooser[3].getDateEditor().getUiComponent();
        textField.setDisabledTextColor(Color.BLACK);
        textField = (JTextField) dateChooser[4].getDateEditor().getUiComponent();
        textField.setDisabledTextColor(Color.BLACK);
        dateChooser[3].setEnabled(false);
        tabbedPane[3].add(dateChooser[3]);
        tabbedPane[3].add(dateChooser[4]);

        textField = (JTextField) dateChooser[3].getDateEditor().getUiComponent();
        textField.setDisabledTextColor(Color.BLACK);
        dateChooser[3].setEnabled(false);
        tabbedPane[3].add(dateChooser[3]);
    }


    public void initShowStudentTablePanel() {
        tabbedPane[4].removeAll();
        JTable jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        ResultSet resultSet;
        boolean isEmpty = true;
        try {
            String query = "SELECT COUNT(*) AS count FROM student";
            preStmt = con.prepareStatement(query);
            resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count == 0) {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                preStmt = con.prepareStatement("select * from student;");
                resultSet = preStmt.executeQuery();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
                int columnCount = resultSetMetaData.getColumnCount();
                String[] columnName = new String[columnCount];
                for (int i = 0; i < columnCount; i++)
                    columnName[i] = resultSetMetaData.getColumnName(i + 1);
                defaultTableModel.setColumnIdentifiers(columnName);
                while (resultSet.next()) {
                    assert student != null;
                    student.setStudentId(resultSet.getInt(1));
                    student.setStudentName(resultSet.getString(2));
                    student.setFatherName(resultSet.getString(3));
                    student.setCourseId(resultSet.getInt(4));
                    student.setDateOfBirth(resultSet.getString(5));
                    student.setGender(resultSet.getString(6));
                    student.setPhoneNumber(resultSet.getString(7));
                    student.setAddress(resultSet.getString(8));
                    Object[] row = {
                            student.getStudentId(),
                            student.getStudentName(),
                            student.getFatherName(),
                            studentCourseComboBox.getItemAt(student.getCourseId()),
                            student.getDateOfBirth(),
                            student.getGender(),
                            student.getPhoneNumber(),
                            student.getAddress()
                    };
                    defaultTableModel.addRow(row);
                }
                jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //it does not work
                jTable.setEnabled(false);
                jScrollPane.setBounds(0, 0, mainTabbedPane.getWidth() - 15, mainTabbedPane.getHeight() - 65);
            } else {
                JLabel message = new JLabel("Table is Empty");
                message.setBounds(10, 5, 100, 30);
                message.setForeground(Color.RED);
                tabbedPane[4].add(message);
                //JOptionPane.showMessageDialog(null,"Table is Empty","Error",JOptionPane.WARNING_MESSAGE);
            }
            revalidateAndRepaintJPanel(tabbedPane[4]);
            tabbedPane[4].add(jScrollPane);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void initShowBookTablePanel() {
        tabbedPane[5].removeAll();
        JTable jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        ResultSet resultSet;
        boolean isEmpty = true;
        try {
            String query = "SELECT COUNT(*) AS count FROM book";
            preStmt = con.prepareStatement(query);
            resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count == 0) {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                preStmt = con.prepareStatement("select * from book;");
                resultSet = preStmt.executeQuery();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
                int columnCount = resultSetMetaData.getColumnCount();
                String[] columnName = new String[columnCount];
                for (int i = 0; i < columnCount; i++)
                    columnName[i] = resultSetMetaData.getColumnName(i + 1);
                defaultTableModel.setColumnIdentifiers(columnName);
                while (resultSet.next()) {
                    assert book != null;
                    book.setAccessionId(resultSet.getInt(1));
                    book.setTitle(resultSet.getString(2));
                    book.setAuthor(resultSet.getString(3));
                    book.setPublisher(resultSet.getString(4));
                    book.setEdition(resultSet.getString(5));
                    book.setCourse(resultSet.getInt(6));
                    book.setDate(resultSet.getString(7));
                    book.setPrice(resultSet.getDouble(8));
                    book.setAvailability(resultSet.getBoolean(9));
                    Object[] row = {
                            book.getAccessionId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPublisher(),
                            book.getEdition(),
                            bookCourseComboBox.getItemAt(book.getCourse()),
                            book.getDate(),
                            book.getPrice(),
                            book.getAvailability()
                    };
                    defaultTableModel.addRow(row);
                }
                jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //it does not work
                jTable.setEnabled(false);
                jScrollPane.setBounds(0, 0, mainTabbedPane.getWidth() - 15, mainTabbedPane.getHeight() - 65);
            } else {
                JLabel message = new JLabel("Table is Empty");
                message.setBounds(10, 5, 100, 30);
                message.setForeground(Color.RED);
                tabbedPane[5].add(message);
                //JOptionPane.showMessageDialog(null,"Table is Empty","Error",JOptionPane.WARNING_MESSAGE);
            }
            revalidateAndRepaintJPanel(tabbedPane[5]);
            tabbedPane[5].add(jScrollPane);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void initShowIssuedBookTablePanel() {
        tabbedPane[6].removeAll();
        JTable jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        ResultSet resultSet;
        boolean isEmpty = true;
        try {
            String query = "SELECT COUNT(*) AS count FROM issued";
            preStmt = con.prepareStatement(query);
            resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count == 0) {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                preStmt = con.prepareStatement("select * from issued;");
                resultSet = preStmt.executeQuery();
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
                String[] columnName = {
                        "Issue Id",
                        "Student Id",
                        "Student Name",
                        "Accession Id",
                        "Book Title",
                        "Issued Date"
                };
                defaultTableModel.setColumnIdentifiers(columnName);
                while (resultSet.next()) {
                    assert issuedBook != null;
                    issuedBook.setIssuedId(resultSet.getInt(1));
                    issuedBook.setStudentId(resultSet.getInt(2));
                    issuedBook.setAccessionId(resultSet.getInt(3));
                    issuedBook.setIssuedDate(resultSet.getString(4));
                    findStudentByStudentId(issuedBook.getStudentId());
                    findBookByAccessionId(issuedBook.getAccessionId());
                    assert student != null;
                    assert book != null;
                    Object[] row = {
                            issuedBook.getIssuedId(),
                            issuedBook.getStudentId(),
                            student.getStudentName(),
                            issuedBook.getAccessionId(),
                            book.getTitle(),
                            issuedBook.getIssuedDate()
                    };
                    defaultTableModel.addRow(row);
                }
                jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //it does not work
                jTable.setEnabled(false);
                jScrollPane.setBounds(0, 0, mainTabbedPane.getWidth() - 15, mainTabbedPane.getHeight() - 65);
            } else {
                JLabel message = new JLabel("Table is Empty");
                message.setBounds(10, 5, 100, 30);
                message.setForeground(Color.RED);
                tabbedPane[6].add(message);
                //JOptionPane.showMessageDialog(null,"Table is Empty","Error",JOptionPane.WARNING_MESSAGE);
            }
            revalidateAndRepaintJPanel(tabbedPane[6]);
            tabbedPane[6].add(jScrollPane);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void initShowLibraryHistoryTablePanel() {
        tabbedPane[7].removeAll();
        JTable jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        ResultSet resultSet;
        boolean isEmpty = true;
        try {
            String query = "SELECT COUNT(*) AS count FROM borrow";
            preStmt = con.prepareStatement(query);
            resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count == 0) {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                preStmt = con.prepareStatement("select * from borrow;");
                resultSet = preStmt.executeQuery();
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
                String[] columnName = {
                        "Borrow Id",
                        "Student Id",
                        "Student Name",
                        "Accession Id",
                        "Book Title",
                        "Borrowed Date",
                        "Return Date",
                        "Fine Amount"
                };
                defaultTableModel.setColumnIdentifiers(columnName);
                while (resultSet.next()) {
                    assert borrow != null;
                    borrow.setBorrowId(resultSet.getInt(1));
                    borrow.setStudentId(resultSet.getInt(2));
                    borrow.setAccessionId(resultSet.getInt(3));
                    borrow.setBorrowDate(resultSet.getString(4));
                    borrow.setReturnDate(resultSet.getString(5));
                    borrow.setFineAmount(resultSet.getDouble(6));
                    findStudentByStudentId(borrow.getStudentId());
                    findBookByAccessionId(borrow.getAccessionId());
                    assert student != null;
                    assert book != null;
                    Object[] row = {
                            borrow.getBorrowId(),
                            borrow.getStudentId(),
                            student.getStudentName(),
                            borrow.getAccessionId(),
                            book.getTitle(),
                            borrow.getBorrowDate(),
                            borrow.getReturnDate(),
                            borrow.getFineAmount()
                    };
                    defaultTableModel.addRow(row);
                }
                jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //it does not work
                jTable.setEnabled(false);
                jScrollPane.setBounds(0, 0, mainTabbedPane.getWidth() - 15, mainTabbedPane.getHeight() - 65);
            } else {
                JLabel message = new JLabel("Table is Empty");
                message.setBounds(10, 5, 100, 30);
                message.setForeground(Color.RED);
                tabbedPane[7].add(message);
                //JOptionPane.showMessageDialog(null,"Table is Empty","Error",JOptionPane.WARNING_MESSAGE);
            }
            revalidateAndRepaintJPanel(tabbedPane[7]);
            tabbedPane[7].add(jScrollPane);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    public boolean initInnerIssuedBookPanel() {
        boolean validate;
        validate = getBookAvailabilityQuery(Integer.parseInt(issuedBookTextField[1].getText()));
        validate = findStudentByStudentId(Integer.parseInt(issuedBookTextField[0].getText())) && validate;
        validate = findBookByAccessionId(Integer.parseInt(issuedBookTextField[1].getText())) && validate;

        if (validate) {
            String[] labelString = {"Student Id", "Student Name", "Father Name", "Issue Date", "Accession No", "Title", "Author"};
            JLabel[] issuedBookInnerLabel = new JLabel[labelString.length];
            JTextField[] issuedBookInnerTextField = new JTextField[issuedBookInnerLabel.length - 1];
            JDateChooser issuedBookInnerDateChooser = new JDateChooser(new Date());
            int yAxis = 100, xAxis = 170;
            for (int i = 0; i < labelString.length; i++) {
                if (i < 2) {
                    issuedBookTextField[i].setEnabled(false);
                    issuedBookButton[i].setEnabled(false);
                    issuedBookButton[i + 2].setBounds(xAxis, 300, 100, 30);
                    xAxis += 130;
                }
                if (i < 3) {
                    issuedBookInnerLabel[i] = new JLabel(labelString[i]);
                    issuedBookInnerLabel[i].setBounds(10, yAxis, 100, 30);
                    tabbedPane[2].add(issuedBookInnerLabel[i]);
                    issuedBookInnerTextField[i] = new JTextField(50);
                    issuedBookInnerTextField[i].setBounds(120, yAxis, 150, 30);
                    issuedBookInnerTextField[i].setEnabled(false);
                    issuedBookInnerTextField[i].setDisabledTextColor(Color.BLACK);
                    tabbedPane[2].add(issuedBookInnerTextField[i]);

                    issuedBookInnerLabel[i + 4] = new JLabel(labelString[i + 4]);
                    issuedBookInnerLabel[i + 4].setBounds(300, yAxis, 100, 30);
                    tabbedPane[2].add(issuedBookInnerLabel[i + 4]);
                    issuedBookInnerTextField[i + 3] = new JTextField(50);
                    issuedBookInnerTextField[i + 3].setBounds(390, yAxis, 150, 30);
                    issuedBookInnerTextField[i + 3].setEnabled(false);
                    issuedBookInnerTextField[i + 3].setDisabledTextColor(Color.BLACK);
                    tabbedPane[2].add(issuedBookInnerTextField[i + 3]);
                    yAxis += 30;
                }
                if (i == 3) {
                    issuedBookInnerLabel[i] = new JLabel(labelString[i]);
                    issuedBookInnerLabel[i].setBounds(10, yAxis + 30, 100, 30);
                    tabbedPane[2].add(issuedBookInnerLabel[i]);
                    issuedBookInnerDateChooser.setBounds(120, yAxis + 30, 150, 27);
                    issuedBookInnerDateChooser.setEnabled(false);
                    JTextField textField = (JTextField) issuedBookInnerDateChooser.getDateEditor().getUiComponent();
                    textField.setDisabledTextColor(Color.BLACK);
                    tabbedPane[2].add(issuedBookInnerDateChooser);
                    yAxis += 30;
                }
            }
            assert issuedBook != null;
            issuedBook.setIssuedId(getLatestIssuedId());
            assert student != null;
            issuedBook.setStudentId(student.getStudentId());
            assert book != null;
            issuedBook.setAccessionId(book.getAccessionId());
            issuedBook.setIssuedDate(new SimpleDateFormat("yyyy-MM-dd").format(issuedBookInnerDateChooser.getDate()));

            issuedBookInnerTextField[0].setText(String.valueOf(student.getStudentId()));
            issuedBookInnerTextField[1].setText(student.getStudentName());
            issuedBookInnerTextField[2].setText(student.getFatherName());
            issuedBookInnerTextField[3].setText(String.valueOf(book.getAccessionId()));
            issuedBookInnerTextField[4].setText(book.getTitle());
            issuedBookInnerTextField[5].setText(book.getAuthor());
            revalidateAndRepaintJPanel(tabbedPane[2]);
        } else {
            clearIssuedBookForm();
        }
        return validate;
    }

    public void initInnerReturnBookForm() {
        int xAxis = 10, yAxis = 100;
        for (int i = 1; i < 5; i++) {
            if (i == 2 || i == 3) {
                returnBookLabels[i + 7].setBounds(xAxis, 270, 120, 30);
                if (i == 2) {
                    returnBookTextFields[i + 6].setBounds(520, 270, 200, 30);
                }
                xAxis += 390;
            }
            returnBookLabels[i].setBounds(10, yAxis, 120, 30);
            returnBookTextFields[i].setBounds(130, yAxis, 200, 30);
            returnBookLabels[i + 4].setBounds(400, yAxis, 120, 30);
            returnBookTextFields[i + 3].setBounds(520, yAxis, 200, 30);
            yAxis += 33;
        }
        returnBookButtons[2].setBounds(250, 370, 100, 30);
        returnBookButtons[3].setBounds(370, 370, 100, 30);
        returnBookTextFields[8].setBounds(520, 270, 200, 30);
        dateChooser[4].setBounds(130, 270, 200, 30);
        assert issuedBook != null;
        returnBookTextFields[1].setText(String.valueOf(issuedBook.getIssuedId()));
        returnBookTextFields[2].setText(String.valueOf(issuedBook.getStudentId()));
        assert student != null;
        returnBookTextFields[3].setText(student.getStudentName());
        returnBookTextFields[4].setText(student.getFatherName());

        //-----------------------------------
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(issuedBook.getIssuedDate());
            dateChooser[3].setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateChooser[3].setDateFormatString("d-MMM-yyyy");
        dateChooser[3].setBounds(520, 100, 200, 30);
        //-----------------------------------

        returnBookTextFields[5].setText(String.valueOf(issuedBook.getAccessionId()));
        assert book != null;
        returnBookTextFields[6].setText(book.getTitle());
        returnBookTextFields[7].setText(book.getAuthor());
    }

    public int getLatestStudentId() {
        String query = "SELECT MAX(`Student ID`) FROM STUDENT;";
        int latestStudentId = 0;
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                latestStudentId = resultSet.getInt(1);
            }
            latestStudentId += 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestStudentId;
    }

    public int getLatestAccessionId() {
        String query = "SELECT MAX(`Accession ID`) FROM book;";
        int accessionId = 0;
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                accessionId = resultSet.getInt(1);
            }
            accessionId += 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accessionId;
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
            latestIssuedId += 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestIssuedId;
    }

    public int getLatestBorrowId() {
        String query = "SELECT MAX(`Borrow ID`) FROM borrow;";
        int latestBorrowId = 0;
        try {
            preStmt = con.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                latestBorrowId = resultSet.getInt(1);
            }
            latestBorrowId += 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestBorrowId;
    }


    public void initializeStudentObject() {
        assert student != null;
        student.setStudentId(getLatestStudentId());
        student.setStudentName(studentInfoTextField[0].getText());
        student.setFatherName(studentInfoTextField[1].getText());
        student.setCourseId(studentCourseComboBox.getSelectedIndex());
        student.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser[0].getDate()));
        student.setGender(getGenderSelected());
        student.setPhoneNumber(studentInfoTextField[2].getText());
        student.setAddress(studentInfoTextField[3].getText());
        System.out.println(student);
    }

    public void initializeBookObject() {
        assert book != null;
        book.setAccessionId(Integer.parseInt(bookTextField[0].getText()));
        book.setTitle(bookTextField[1].getText());
        book.setAuthor(bookTextField[2].getText());
        book.setPublisher(bookTextField[3].getText());
        book.setEdition(bookTextField[4].getText());
        book.setCourse(bookCourseComboBox.getSelectedIndex());
        book.setDate(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser[1].getDate()));
        book.setQuantity(Integer.parseInt(bookTextField[5].getText()));
        book.setPrice(Double.parseDouble(bookTextField[6].getText()));
        book.setAvailability(true);
        System.out.println(book);
    }

    public void initializeBorrowBookObject() {
        assert borrow != null;
        borrow.setBorrowId(getLatestBorrowId());
        assert issuedBook != null;
        borrow.setStudentId(issuedBook.getStudentId());
        borrow.setAccessionId(issuedBook.getAccessionId());
        borrow.setBorrowDate(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser[3].getDate()));
        borrow.setReturnDate(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser[3].getDate()));
        borrow.setFineAmount(Double.parseDouble(returnBookTextFields[8].getText()));
    }


    public void clearStudentForm() {
        for (JTextField jTextField : studentInfoTextField)
            jTextField.setText("");
        genderButtonGroup.clearSelection();
        studentCourseComboBox.setSelectedIndex(0);
        dateChooser[0].setDate(null);
    }

    public void clearBookForm() {
        for (JTextField jTextField : bookTextField)
            jTextField.setText("");
        if (bookCourseComboBox.getSelectedIndex() != 0)
            bookCourseComboBox.setSelectedIndex(0);
        if (accessionIdCheckBox.isSelected())
            accessionIdCheckBox.setSelected(false);
        bookTextField[0].setText(String.valueOf(getLatestAccessionId()));
    }

    public void clearIssuedBookForm() {
        for (JTextField textField : issuedBookTextField)
            textField.setText("");
    }

    public void clearInnerIssuedBookForm() {
        for (int i = 0; i < 2; i++) {
            tabbedPane[2].removeAll();
            initIssuedBookPanel();
            revalidateAndRepaintJPanel(tabbedPane[2]);
        }
    }


    public boolean studentFormValidation() {
        boolean validate = true;
        for (JTextField jTextField : studentInfoTextField) {
            if (jTextField.getText().isEmpty() || jTextField.getText().length() < 3) {
                validate = false;
                break;
            }
        }
        validate = validate && (maleRadioButton.isSelected() || femaleRadioButton.isSelected());
        if (studentCourseComboBox.getSelectedIndex() == 0)
            validate = false;
        if (dateChooser[0].getDate() == null)
            validate = false;
        System.out.println(validate ? "true" : "false");
        return validate;
    }

    public boolean bookFormValidation() {
        boolean validate = true;
        for (int i = 1; i < bookTextField.length; i++) {
            if (i != 5 && i != 6 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 3)) {
                validate = false;
                break;
            }
            if (i == 5 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 1)) {
                validate = false;
                break;
            }
            if (i == 6 && (bookTextField[i].getText().equals("") || bookTextField[i].getText().length() < 1)) {
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
        if (dateChooser[1].getDate() == null)
            validate = false;
        if (accessionIdCheckBox.isSelected() && bookTextField[0].getText().equals(String.valueOf(getLatestAccessionId())))
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
        System.out.println(validate ? "true" : "false");
        return validate;
    }


    public boolean generateInsertStudentQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO student (`Student Id`, `Student Name`, `Father Name`, Course, `Date Of Birth`, Gender, `Phone Number`, Address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            // Set the values for the placeholders in the statement
            assert student != null;
            preStmt.setInt(1, student.getStudentId());
            preStmt.setString(2, student.getStudentName());
            preStmt.setString(3, student.getFatherName());
            preStmt.setInt(4, student.getCourseId());
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

    public boolean generateInsertBookQuery() {
        assert book != null;
        book.setAccessionId(Integer.parseInt(bookTextField[0].getText()));
        boolean validate = true;
        try {
            String query = "INSERT INTO book (`Accession Id`, Title, Author, Publisher, Edition, Course, Date, Price, Availability) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);

            preStmt.setInt(1, book.getAccessionId());
            preStmt.setString(2, book.getTitle());
            preStmt.setString(3, book.getAuthor());
            preStmt.setString(4, book.getPublisher());
            preStmt.setString(5, book.getEdition());
            preStmt.setInt(6, book.getCourse());
            preStmt.setString(7, book.getDate());
            preStmt.setDouble(8, book.getPrice());
            preStmt.setBoolean(9, book.getAvailability());

            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            e.getStackTrace();
        }
        return validate;
    }

    public boolean generateInsertIssuedBookQuery() {
        boolean validate = true;
        try {
            String query = "INSERT INTO issued (`Issued Id`, `Student Id`, `Accession Id`, `Issued Date`) " +
                    "VALUES (?, ?, ?, ?)";
            preStmt = con.prepareStatement(query);
            assert issuedBook != null;
            preStmt.setInt(1, issuedBook.getIssuedId());
            preStmt.setInt(2, issuedBook.getStudentId());
            preStmt.setInt(3, issuedBook.getAccessionId());
            preStmt.setString(4, issuedBook.getIssuedDate());
            System.out.println(issuedBook);
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
            assert book != null;
            validate = setBookAvailabilityQuery(false, book.getAccessionId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return validate;
    }


    public boolean getBookAvailabilityQuery(int accessionId) {
        boolean validate = true;
        try {
            String query = "SELECT (`Availability`) from book where `Accession Id` = ?";
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, accessionId);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                assert book != null;
                book.setAvailability(resultSet.getBoolean("Availability"));
                validate = book.getAvailability();
                if (!validate)
                    JOptionPane.showMessageDialog(null, "Book Already Allotted");
            }
        } catch (SQLException e) {
            validate = false;
            e.getStackTrace();
        }
        return validate;
    }

    public boolean deleteIssuedBookQuery(int issuedId) {
        boolean validate;
        try {
            preStmt = con.prepareStatement("DELETE FROM issued WHERE `Issued Id` = ?");
            preStmt.setInt(1, issuedId);
            assert borrow != null;
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
            validate = setBookAvailabilityQuery(true, borrow.getAccessionId());
        } catch (SQLException e) {
            validate = false;
            e.getStackTrace();
        }
        return validate;
    }

    public boolean generateInsertBorrowBookQuery() {
        boolean validate = true;
        try {
            preStmt = con.prepareStatement("insert into borrow (`Borrow Id`, `Student Id`, `Accession Id`, `Borrow Date`, `Return Date`, `Fine Amount`) VALUES \n" +
                    "(?, ?, ?, ?, ?, ?);");
            assert borrow != null;
            preStmt.setInt(1, borrow.getBorrowId());
            assert issuedBook != null;
            preStmt.setInt(2, borrow.getStudentId());
            preStmt.setInt(3, borrow.getAccessionId());
            preStmt.setString(4, borrow.getBorrowDate());
            preStmt.setString(5, borrow.getReturnDate());
            preStmt.setDouble(6, borrow.getFineAmount());
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return validate;
    }

    public boolean findStudentByStudentId(int studentId) {
        boolean validate = true;
        String query = "SELECT `Student Name`, `Father Name` FROM student WHERE `Student Id` = ?";
        try {
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, studentId);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                assert student != null;
                student.setStudentId(studentId);
                student.setStudentName(resultSet.getString("Student Name"));
                student.setFatherName(resultSet.getString("Father Name"));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid student Id");
                validate = false;
            }
        } catch (SQLException sqlException) {
            validate = false;
            sqlException.printStackTrace();
        }
        return validate;
    }

    public boolean findBookByAccessionId(int accessionId) {
        boolean validate = true;
        String query = "SELECT `Title`, `Author` FROM book WHERE `Accession Id` = ?";
        try {
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, accessionId);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                assert book != null;
                book.setAccessionId(accessionId);
                book.setTitle(resultSet.getString("Title"));
                book.setAuthor(resultSet.getString("Author"));
            } else {
                validate = false;
                JOptionPane.showMessageDialog(null, "Invalid Accession ID");
            }
        } catch (SQLException sqlException) {
            validate = false;
            sqlException.printStackTrace();
        }
        return validate;
    }

    public boolean findIssuedBookByIssuedId(int issuedId) {
        boolean validate = true;
        String query = "SELECT * FROM issued WHERE `Issued Id` = ?";
        try {
            preStmt = con.prepareStatement(query);
            preStmt.setInt(1, issuedId);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                assert issuedBook != null;
                issuedBook.setIssuedId(resultSet.getInt(1));
                issuedBook.setStudentId(resultSet.getInt(2));
                issuedBook.setAccessionId(resultSet.getInt(3));
                issuedBook.setIssuedDate(resultSet.getString(4));
            } else {
                validate = false;
                JOptionPane.showMessageDialog(null, "Invalid Issued Id");
            }
        } catch (SQLException e) {
            validate = false;
            e.getStackTrace();
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
    }

    public void revalidateAndRepaintJPanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public boolean setBookAvailabilityQuery(boolean availability, int accessionId) {
        boolean validate = true;
        try {
            String query = "UPDATE book SET `Availability` = ? WHERE `Accession Id` = ?;";
            preStmt = con.prepareStatement(query);
            preStmt.setBoolean(1, availability);
            preStmt.setInt(2, accessionId);
            System.out.println(preStmt.executeUpdate() + " row(s) inserted.");
        } catch (SQLException e) {
            validate = false;
            System.out.println(e.getMessage());
        }
        return validate;
    }

    public int getEnrolledStudentByCourseId(int courseId) {
        int enrolledStudent = 0;
        try {
            preStmt = con.prepareStatement("SELECT `Enrolled Students` FROM course where `Course Id` = ?");
            preStmt.setInt(1, courseId);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                enrolledStudent = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return enrolledStudent;
    }

    public boolean updateEnrolledStudentByCourseId(int courseId) {
        boolean validate = true;
        int temp = getEnrolledStudentByCourseId(courseId) + 1;
        try {
            preStmt = con.prepareStatement("UPDATE course SET `Enrolled Students` = ? WHERE `Course Id` = " + courseId);
            preStmt.setInt(1, temp);
            System.out.println(preStmt.executeUpdate() + " row(s) updated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            validate = false;
        }
        return validate;
    }

    public String getGenderSelected() {
        return maleRadioButton.isSelected() ? "Male" : "Female";
    }

    public boolean returnBookFormValidation() {
        return !returnBookTextFields[0].getText().isEmpty();
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (newStudentButtons[0] == e.getSource()) {
            if (studentFormValidation()) {
                initializeStudentObject();
                assert student != null;
                if (generateInsertStudentQuery() && updateEnrolledStudentByCourseId(student.getCourseId())) {
                    tabbedPane[0].removeAll();
                    initNewStudentPanel();
                    JOptionPane.showMessageDialog(null, "Welcome " + student.getStudentName(), "Student Admission Confirmation", JOptionPane.WARNING_MESSAGE);
                    initShowStudentTablePanel();
                } else
                    JOptionPane.showMessageDialog(null, "Some Error Occurred");
            } else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }

        if (newStudentButtons[1] == e.getSource()) {
            clearStudentForm();
        }

        if (newBookButtons[0] == e.getSource()) {
            if (bookFormValidation()) {
                initializeBookObject();
                assert book != null;
                for (int i = book.getQuantity(); i != 0; i--) {
                    if (generateInsertBookQuery())
                        clearBookForm();
                    else
                        System.out.println("false");
                }
                initShowBookTablePanel();
            } else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }

        if (newBookButtons[1] == e.getSource()) {
            clearBookForm();
        }

        if (e.getSource() == issuedBookButton[0]) {
            if (issuedBookFormValidation()) {
                if (initInnerIssuedBookPanel()) {
                    clearIssuedBookForm();
                }
            } else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        if (e.getSource() == issuedBookButton[1])
            clearIssuedBookForm();
        if (e.getSource() == issuedBookButton[2]) {
            int result = JOptionPane.showConfirmDialog(null,
                    "You sure, all above details are correct.",
                    "Confirmation for Book Allotment",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == 0) {
                if (generateInsertIssuedBookQuery())
                    clearInnerIssuedBookForm();
                System.out.println("false");
                initShowBookTablePanel();
                initShowIssuedBookTablePanel();
                assert issuedBook != null;
                JOptionPane.showMessageDialog(null, "Issue Id: " + issuedBook.getIssuedId(), "Book Successfully Issued", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == issuedBookButton[3]) {
            clearInnerIssuedBookForm();
        }
        if (e.getSource() == returnBookButtons[0]) {
            if (returnBookFormValidation()) {
                assert issuedBook != null;
                if (findIssuedBookByIssuedId(issuedBook.getIssuedId())) {
                    if (findStudentByStudentId(issuedBook.getStudentId())) {
                        if (findBookByAccessionId(issuedBook.getAccessionId())) {
                            returnBookTextFields[0].setEnabled(false);
                            returnBookButtons[0].setEnabled(false);
                            returnBookButtons[1].setEnabled(false);
                            initInnerReturnBookForm();
                            revalidateAndRepaintJPanel(tabbedPane[3]);
                        }
                    }
                }
            } else
                JOptionPane.showMessageDialog(null, "Please Completely fill the form", "Alert", JOptionPane.WARNING_MESSAGE);
        }
        if (e.getSource() == returnBookButtons[1]) {
            returnBookTextFields[0].setText("");
        }
        if (e.getSource() == returnBookButtons[2]) {
            assert issuedBook != null;
            int result = JOptionPane.showConfirmDialog(null,
                    "You sure, all above details are correct.",
                    "Confirmation for Book Return",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == 0) {
                returnBookTextFields[8].setText(returnBookTextFields[8].getText().isEmpty() ? "0" : "");
                initializeBorrowBookObject();
                if (generateInsertBorrowBookQuery() && deleteIssuedBookQuery(issuedBook.getIssuedId())) {
                    tabbedPane[3].removeAll();
                    initReturnBookPanel();
                    revalidateAndRepaintJPanel(tabbedPane[3]);
                    initShowBookTablePanel();
                    initShowLibraryHistoryTablePanel();
                    JOptionPane.showMessageDialog(null, "Thank you for return the book", "Book Return Successfully", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Some Error Occurred");
                }
            }
        }
        if (e.getSource() == returnBookButtons[3]) {
            tabbedPane[3].removeAll();
            initReturnBookPanel();
            revalidateAndRepaintJPanel(tabbedPane[3]);
        }
    }

    @Override
    public void keyTyped(@NotNull KeyEvent e) {
        if (e.getSource() == studentInfoTextField[studentInfoTextField.length - 2]) { //textField validation for phone number
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
            if (studentInfoTextField[studentInfoTextField.length - 2].getText().length() >= 10)
                e.consume();
        }
        if (e.getSource() == studentInfoTextField[0] || e.getSource() == studentInfoTextField[1] || e.getSource() == bookTextField[1] || e.getSource() == bookTextField[2] || e.getSource() == bookTextField[3] || e.getSource() == bookTextField[4]) {
            char ch = e.getKeyChar();
            if (!(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_SPACE))
                e.consume();
        }
        if (e.getSource() == bookTextField[0] || e.getSource() == bookTextField[5] || e.getSource() == issuedBookTextField[0] || e.getSource() == issuedBookTextField[1] || e.getSource() == issuedBookTextField[0] || e.getSource() == issuedBookTextField[1] || e.getSource() == returnBookTextFields[0]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
        }
        if (e.getSource() == bookTextField[6] || e.getSource() == returnBookTextFields[8]) {
            char ch = e.getKeyChar();
            if (!(Character.isDigit(ch) || ch == '.' || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE))
                e.consume();
            if (e.getKeyChar() == '.') {
                if (bookTextField[6].getText().contains(".")) {
                    e.consume();
                }
                if (returnBookTextFields[8].getText().contains(".")) {
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
    public void itemStateChanged(@NotNull ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            bookTextField[0].setEnabled(true);
        } else {
            bookTextField[0].setEnabled(false);
            bookTextField[0].setText(String.valueOf(getLatestAccessionId()));
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(@NotNull FocusEvent e) {
        if (e.getSource() == studentInfoTextField[0]) {
            if (!studentInfoTextField[0].getText().isEmpty())
                toStringValidate(studentInfoTextField[0]);
        }
        if (e.getSource() == studentInfoTextField[1]) {
            if (!studentInfoTextField[1].getText().isEmpty())
                toStringValidate(studentInfoTextField[1]);
        }
        if (e.getSource() == bookTextField[1]) {
            if (!bookTextField[1].getText().isEmpty())
                toStringValidate(bookTextField[1]);
        }
        if (e.getSource() == bookTextField[2]) {
            if (!bookTextField[2].getText().isEmpty())
                toStringValidate(bookTextField[2]);
        }
        if (e.getSource() == bookTextField[3]) {
            if (!bookTextField[3].getText().isEmpty())
                toStringValidate(bookTextField[3]);
        }
        if (e.getSource() == bookTextField[4]) {
            if (!bookTextField[4].getText().isEmpty())
                toStringValidate(bookTextField[4]);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == mainTabbedPane) {
            if (mainTabbedPane.getSelectedIndex() == 0)
                initNewStudentPanel();
            if (mainTabbedPane.getSelectedIndex() == 1)
                initNewBookPanel();
            if (mainTabbedPane.getSelectedIndex() == 2)
                initIssuedBookPanel();
            if (mainTabbedPane.getSelectedIndex() == 3)
                initReturnBookPanel();
            if (mainTabbedPane.getSelectedIndex() == 4)
                initShowStudentTablePanel();
            if (mainTabbedPane.getSelectedIndex() == 5)
                initShowBookTablePanel();
            if (mainTabbedPane.getSelectedIndex() == 6)
                initShowIssuedBookTablePanel();
            if (mainTabbedPane.getSelectedIndex() == 7)
                initShowLibraryHistoryTablePanel();
        }
    }
}