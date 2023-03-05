import javax.swing.*;
public class Login{
    public Login(){
        JFrame frame = new JFrame("Login Window");
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        new Login();
    }
}