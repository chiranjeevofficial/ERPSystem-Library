package BrahmasmiLiabrary;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class util {
    public static final int
            labelHeaderX = 50, labelHeaderY = 130, labelHeaderWidth = 200, labelHeaderHeight = 50,
            textFieldX = 270, textFieldY = 0, textFieldWidth = 200, textFieldHeight = 35,
            companyNameWidth = 265,
            passwordFieldY = 0, passwordFieldWidth = 200;
    public static Connection getConnectionWithMySQL(String databaseName, String username, String password) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, username, password);
            if(con!=null)
                System.out.println("Connection Established");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    public static JLabel getImageOnLabel(String path, int imageWidth) {
        ImageIcon scaledIcon = null;
        try{
            ImageIcon originalImage = new ImageIcon(ImageIO.read(new File(path)));
            int originalWidth = originalImage.getIconWidth();
            int originalHeight = originalImage.getIconHeight();
            int newHeight = (int) (( (double) imageWidth / originalWidth) * originalHeight);
            scaledIcon = new ImageIcon(originalImage.getImage().getScaledInstance(imageWidth, newHeight, java.awt.Image.SCALE_SMOOTH));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new JLabel(scaledIcon);
    }

    public static void setMainFrame(JFrame frame, int width, int height) {
    frame.setLayout(null);
    frame.setSize(width,height);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null); // open application to center on the screen
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static final Font titleFont = new Font("Century Gothic", Font.BOLD,25),
            labelHeaderFont = new Font("Century Gothic",Font.BOLD,40),
            textFieldFont = new Font("MS Reference Sans Serif",Font.PLAIN,20);

    public static final Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
            allSideBorder = BorderFactory.createLineBorder(Color.BLACK,1);

    public static final Color blueColor = new Color(0, 55, 255),
            purpleColor = new Color(183,0,255),
            pinkColor = new Color(255, 0, 183),
            whiteColor = new Color(255,255,255),
            orangeColor = new Color(255,152,0);

    public static void loginButtonDecoration(JButton button) {
        button.setFocusable(false);
        button.setFont(util.textFieldFont);
        button.setOpaque(true);
        button.setBackground(purpleColor);
        button.setForeground(Color.WHITE);
        button.setBorder(util.allSideBorder);
    }

    public static void labelTextDecoration(JLabel label) {
        label.setForeground(Color.BLACK);
        label.setFont(util.labelHeaderFont);
        label.setOpaque(true);
    }

    public static void labelTextDecoration(JLabel label, boolean opaque) {
        label.setForeground(Color.BLACK);
        label.setFont(util.labelHeaderFont);
        label.setOpaque(opaque);
    }

    public static void headerTextDecoration(JLabel label) {
        label.setForeground(Color.BLACK);
        label.setFont(util.titleFont);
        label.setOpaque(true);
    }

    public static void verificationLabelDecoration(JLabel label) {
        label.setFont(util.textFieldFont);
        util.setLabelOnCenter(label,0,350,600,40);
    }

    
    public static void textFieldDecoration(JTextField textField) {
        textField.setForeground(Color.black);
        textField.setOpaque(false);
        textField.setBorder(util.underline);
        textField.setFont(util.textFieldFont);
    }

    public static void setLabelOnCenter(JLabel label, int width, int height) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setSize(width,height);
    }

    public static void setLabelOnCenter(JButton label, int x, int y, int width, int height) {
        label.setSize(width,height);
        label.setLocation(x,y);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
    }

    public static void setLabelHighlighted(JLabel label) {
        label.setBackground(Color.YELLOW);
        label.setOpaque(true);
    }

    public static void setLabelHighlighted(JLabel label, Color color) {
        label.setBackground(color);
        label.setOpaque(true);
    }

    public static void setLabelHighlighted(JButton label, Color color) {
        label.setBackground(color);
        label.setOpaque(true);
    }

    public static void setLabelOnCenter(JLabel label, int x, int y, int width, int height) {
        label.setLocation(x,y);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setSize(width,height);
    }



    /*public static void setYellowBackgroundAndBottomBorder(JLabel label) {
        label.setBorder(MyFont.underline);
        label.setBackground(Color.YELLOW);
        label.setOpaque(true);
    }**/

    /*public static void setMyFont(JTextField textField) {
        textField.setFont(MyFont.textField);
    }**/

    /*public static Color generateGradientColor(Color color1, Color color2, int width, int height) {
        GradientPaint gradient = new GradientPaint(10, 50, color1, width, height, color2);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        //g2d.dispose();
        return new Color(image.getRGB(0, 0));
    }**/

}