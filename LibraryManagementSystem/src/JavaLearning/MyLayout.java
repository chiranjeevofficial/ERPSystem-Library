package JavaLearning;

import javax.swing.*;
import java.awt.*;

class MyBorderLayout {
    JFrame frame;
    public MyBorderLayout() {
        frame = new JFrame("Border Layout");
        //frame.setLayout(new BorderLayout(20,15));
        frame.add(new JButton("North"), BorderLayout.NORTH);
        frame.add(new JButton("South"), BorderLayout.SOUTH);
        frame.add(new JButton("East"), BorderLayout.EAST);
        frame.add(new JButton("West"), BorderLayout.WEST);
        frame.add(new JButton("Center"), BorderLayout.CENTER);
        frame.add(new JButton("North"), BorderLayout.NORTH);
        frame.add(new JButton("South"), BorderLayout.SOUTH);
        frame.add(new JButton("East"), BorderLayout.EAST);
        frame.add(new JButton("West"), BorderLayout.WEST);
        frame.add(new JButton("Center"), BorderLayout.CENTER);
        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void MyBorderThreeWithoutAnyPosition(int hGap, int vGap) {
        frame.setLayout(new BorderLayout(hGap,vGap));
        frame.add(new JButton("North"));
        frame.add(new JButton("East"));
        frame.add(new JButton("West"));
        frame.add(new JButton("South"));
        frame.add(new JButton("Center"));
    }
}

class MyGridLayout {
    JFrame frame;
    public MyGridLayout() {
        frame = new JFrame("Grid Layout");
        /*frame.setLayout(new GridLayout());
        frame.setLayout(new GridLayout(3,3));**/
        frame.setLayout(new GridLayout(3,3, 20, 15));
        frame.add(new JButton("1"));
        frame.add(new JButton("2"));
        frame.add(new JButton("3"));
        frame.add(new JButton("4"));
        frame.add(new JButton("5"));
        frame.add(new JButton("6"));
        frame.add(new JButton("7"));
        frame.add(new JButton("8"));
        frame.add(new JButton("9"));

        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}

class MyFlowLayout {
    JFrame frame;
    public MyFlowLayout() {
        frame = new JFrame("Flow Layout");
        //frame.setLayout(new FlowLayout());
        //frame.setLayout(new FlowLayout(FlowLayout.RIGHT));
        frame.setLayout(new FlowLayout(FlowLayout.LEFT,20,25));

        frame.add(new JButton("1"));
        frame.add(new JButton("2"));
        frame.add(new JButton("3"));
        frame.add(new JButton("4"));
        frame.add(new JButton("5"));

        frame.setSize(500,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}


class BoxLayout {
    public BoxLayout() {
        JFrame frame = new JFrame("Box Layout");
        frame.setLayout(new javax.swing.BoxLayout(frame.getContentPane(), javax.swing.BoxLayout.X_AXIS));
        frame.add(new JButton("1"));
        frame.add(new JButton("2"));
        frame.add(new JButton("3"));
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}

public class MyLayout {
    public static void main(String[] args) {
        new BoxLayout();
    }
}
