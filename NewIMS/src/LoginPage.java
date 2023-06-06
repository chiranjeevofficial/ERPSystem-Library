import javax.swing.*;
import java.awt.*;

public class LoginPage {
    int width = 700, height = 500;
    JFrame mainJFrame;
    JPanel mainJPanel, headJPanel, inputJPanel, navigateJPanel;

    public LoginPage() {
        initMainFrame();
        initMainPanel();
    }

    public void initMainFrame() {
        mainJFrame = new JFrame("Inventory Management System");
        mainJFrame.setLayout(null);
        mainJFrame.setSize(width,height);
        mainJFrame.setVisible(true);
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initMainPanel() {
        mainJPanel = new JPanel();
        mainJPanel.setBounds(0,0,width - 16,height - 39);
        mainJPanel.setBackground(Color.WHITE);
        mainJFrame.add(mainJPanel);
        initHeadPanel();
        initInputPanel();
        initNavigatePanel();
        mainJFrame.repaint();
        mainJFrame.revalidate();
    }

    public void initHeadPanel() {
        headJPanel = new JPanel();
        headJPanel.setLayout(new BoxLayout(headJPanel, BoxLayout.X_AXIS));
        JPanel[] innerHeadJPanel = new JPanel[2];

        innerHeadJPanel[0] = new JPanel(new GridLayout(1, 1));
        innerHeadJPanel[0].setBackground(Color.YELLOW);
        innerHeadJPanel[0].setPreferredSize(new Dimension(150, 150));
        JLabel iconLabel = new JLabel("Image Icon");
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        innerHeadJPanel[0].add(iconLabel);
        headJPanel.add(innerHeadJPanel[0]);

        innerHeadJPanel[1] = new JPanel(new GridLayout(1, 1));
        innerHeadJPanel[1].setBackground(Color.CYAN);
        innerHeadJPanel[1].setPreferredSize(new Dimension(534, 150));
        headJPanel.add(innerHeadJPanel[1]);

        headJPanel.setPreferredSize(new Dimension(width - 16, 150));
        //headJPanel.setBounds(0, 0, width - 16, 100);
        headJPanel.setBackground(Color.GREEN);
        mainJPanel.add(headJPanel);
    }

    public void initInputPanel() {
        inputJPanel = new JPanel();
        inputJPanel.setLayout(new BoxLayout(inputJPanel, BoxLayout.Y_AXIS));

        JPanel[] innerInputJPanel = new JPanel[2];
        innerInputJPanel[0] = new JPanel(new GridLayout(1,1));
        innerInputJPanel[0].setBackground(Color.PINK);
        innerInputJPanel[0].setPreferredSize(new Dimension(width - 16, 100));
        inputJPanel.add(innerInputJPanel[0]);

        innerInputJPanel[1] = new JPanel(new GridLayout(1,1));
        innerInputJPanel[1].setBackground(Color.LIGHT_GRAY);
        innerInputJPanel[1].setPreferredSize(new Dimension(width - 16, 100));
        inputJPanel.add(innerInputJPanel[1]);

        inputJPanel.setBounds(0,150,width - 16, 200);
        //inputJPanel.setPreferredSize(new Dimension(width - 16, 200));
        inputJPanel.setBackground(Color.YELLOW);
        mainJPanel.add(inputJPanel);
    }

    public void initNavigatePanel() {
        navigateJPanel = new JPanel();
        navigateJPanel.setLayout(null);
        navigateJPanel.setPreferredSize(new Dimension(width - 16, 150));
        navigateJPanel.setBackground(Color.RED);
        mainJPanel.add(navigateJPanel);
    }
}
