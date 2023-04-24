package fms;
import javax.swing.*;
public class ProgressBarExample extends JFrame{
    JProgressBar jb;
    ProgressBarExample(){
        jb=new JProgressBar(0,2000);
        jb.setBounds(40,40,160,30);
        jb.setValue(0);
        jb.setStringPainted(true);
        add(jb);
        setSize(250,150);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iterate();
    }
    public void iterate(){
        int i = 0;
        while(i<=2000){
            jb.setValue(i);
            i+=20;
            try {
                Thread.sleep(150);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new ProgressBarExample();
    }
}