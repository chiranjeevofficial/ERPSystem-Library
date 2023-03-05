// import javax.swing.*;  
// class MenuExample {  
//     MenuExample(){  
//         JFrame f= new JFrame("Menu and MenuItem Example");  
//         f.setSize(400,400);  
//         f.setLayout(null);  
//         f.setVisible(true);  
//         f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

//         JMenu menu = new JMenu("Menu");
//         JMenu submenu = new JMenu("Sub Menu");
//         JMenuItem i1, i2, i3, i4, i5;  

//         JMenuBar mb=new JMenuBar();  
//         f.setJMenuBar(mb);  
//         i1 = new JMenuItem("Item 1");  
//         i2 = new JMenuItem("Item 2");  
//         i3 = new JMenuItem("Item 3");  
//         i4 = new JMenuItem("Item 4");  
//         i5 = new JMenuItem("Item 5");  
//         menu.add(i1);
//         menu.add(i2);
//         menu.add(i3);  
//         submenu.add(i4);
//         submenu.add(i5);  
//         menu.add(submenu);  
//         mb.add(menu);  
//     }  
//     public static void main(String args[]) {  
//         new MenuExample();  
//     }
// }  
import javax.swing.*;

public class MenuExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Demo");
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
