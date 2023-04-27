package JavaLearning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarProgram extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JLabel monthLabel;
    private JButton[] dayButton;
    private int year, month;
    private GregorianCalendar cal;

    public CalendarProgram() throws Exception{
        super("Calendar");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create calendar
        cal = new GregorianCalendar();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        monthLabel = new JLabel();
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        updateMonthLabel();
        headerPanel.add(monthLabel, BorderLayout.CENTER);

        // Create calendar panel
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7));
        String[] daysOfWeek = new DateFormatSymbols().getShortWeekdays();
        for (int i = 0; i < daysOfWeek.length; i++) {
            calendarPanel.add(new JLabel(daysOfWeek[i], JLabel.CENTER));
        }
        dayButton = new JButton[42];
        for (int i = 0; i < dayButton.length; i++) {
            dayButton[i] = new JButton();
            dayButton[i].addActionListener(this);
            dayButton[i].setBackground(Color.WHITE);
            calendarPanel.add(dayButton[i]);
        }

        // Add header and calendar panels to the frame
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(calendarPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < dayButton.length; i++) {
            if (e.getSource() == dayButton[i]) {
                int day = Integer.parseInt(dayButton[i].getText());
                cal.set(year, month, day);
                updateMonthLabel();
                return;
            }
        }
    }

    private void updateMonthLabel() {
        monthLabel.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, null) + " " + cal.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int numDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < dayButton.length; i++) {
            if (i < dayOfWeek - 1 || i >= dayOfWeek + numDaysInMonth - 1) {
                dayButton[i].setText("");
                dayButton[i].setEnabled(false);
            } else {
                dayButton[i].setText(String.valueOf(i - dayOfWeek + 2));
                dayButton[i].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new CalendarProgram().setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
