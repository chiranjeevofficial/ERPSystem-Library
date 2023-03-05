import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    
    private JButton myButton;
    
    public MyFrame() {
        setTitle("My Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
        
        myButton = new JButton("Click me!");
		myButton.setBounds(10,10,200,40);
        myButton.addActionListener(this); // add the button listener
        add(myButton);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myButton) {
            System.out.println("Button clicked!");
			//dispose();
			new Login();
        }
    }
    
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
    }
}

/*import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListner;
public class MyFrame extends JFrame implements ActionListner {   
    public MyFrame() {
        setTitle("My Frame");
	    setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//dispose();
		JButton myButton = new JButton("Click Me");
		myButton.addActionListner(this);
		add(myButton);

		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == myButton)
				System.out.println("Button Clicked");
		}
	}
	public static void main(String[] args) {
		new MyFrame();
	}
}**/