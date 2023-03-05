import javax.swing.*;

public class MyMenu extends JFrame {
    public MyMenu() {
        JMenuBar menuBar = new JMenuBar(); // Create a menu bar

        JMenu fileMenu = new JMenu("File"); // Create a top-level menu
        JMenuItem openItem = new JMenuItem("Open"); // Create a menu item
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openItem); // Add menu items to the menu
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // Add a separator
        fileMenu.add(exitItem);

        menuBar.add(fileMenu); // Add the menu to the menu bar

        setJMenuBar(menuBar); // Set the menu bar in the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyMenu frame = new MyMenu();
    }
}
