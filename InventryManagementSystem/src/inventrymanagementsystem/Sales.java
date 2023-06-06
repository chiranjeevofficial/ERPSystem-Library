

package inventrymanagementsystem;
import java.awt.*;
import javax.swing.*;
public class Sales {
     JFrame mainFrame;
     JPanel mainPanel;
     public Sales(){
     mainFrame = new JFrame("Homepage");
        mainFrame.setSize(1500, 800);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1500,800);
        mainPanel.setBackground(Color.GRAY);
        mainFrame.add(mainPanel);
        mainPanel.setVisible(true);

}
     
}
