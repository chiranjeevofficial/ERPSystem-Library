import javax.swing.*;

public class ApplicationHead {
    public void ApplicationHead(int panelWidth, int panelHeight){
        JPanel myPanel = new JPanel(null);
        myPanel.setBounds(0,0,panelWidth,panelHeight);
        JLabel collegeLogo = new JLabel(new ImageIcon("C:\\images\\collegeLogo.jpg"));
        JLabel collegeName = new JLabel("Swami Purnanand Degree College of Technical Education");
        collegeLogo.setBounds(180,5,70,70);
        collegeName.setBounds(10,80,440,FixConstant.labelHeight);
        myPanel.add(collegeName);
        myPanel.add(collegeLogo);
        myPanel.setVisible(true);
    }
}
