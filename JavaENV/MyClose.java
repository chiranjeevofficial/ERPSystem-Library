import javax.swing.*;
import java.awt.event.*;

public class MyClose extends JFrame {
    public MyClose() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Disable the default close operation
        addWindowListener(new ConfirmCloseListener()); // Add a custom close operation
        addWindowListener(new SaveAndCloseListener()); // Add another custom close operation

        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyClose frame = new MyClose();
    }

    private class ConfirmCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int result = JOptionPane.showConfirmDialog(MyClose.this,"Are you sure you want to close this window?", "Confirm Close",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                dispose(); // Close the window
            }
        }
    }

    private class SaveAndCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int result = JOptionPane.showConfirmDialog(MyClose.this,"Do you want to save changes before closing?", "Save Changes",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                // Save changes and close the window
                dispose();
            }
            else if (result == JOptionPane.NO_OPTION) {
                // Discard changes and close the window
                dispose();
            }
            // Do nothing if the user cancels the close operation
        }
    }
}
