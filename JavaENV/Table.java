import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Book{
    public static String bookName, bookAuthor, bookPublisher, bookEdition;
}

public class Table {
    //components objects
    public Table(){
        JFrame mainFrame = new JFrame("Book Table");
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setBounds(600,300,600,300);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        JMenu mainMenu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Open File");
        mainMenu.setBounds(0,0,600,20);
        mainFrame.add(mainMenu);

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action here
            }
        });


        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(10,10,560,240);

        tableModel.addColumn("Book Name");
        tableModel.addColumn("Book Author");
        tableModel.addColumn("Book Publisher");
        tableModel.addColumn("Book Edition");

        String[] data = {Book.bookName,Book.bookAuthor,Book.bookPublisher,Book.bookEdition};

        //tableModel.addRow(data);

        mainFrame.add(jsp);
        
    }   
    public static void main(String[] args){
        new Table();
    }
}
