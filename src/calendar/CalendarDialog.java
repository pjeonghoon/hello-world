package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalendarDialog extends JPanel {
    
    protected int yy;
    

    protected int mm, dd;

   
    protected JButton labs[][];

   
    protected int leadGap = 0;

  
    Calendar calendar = new GregorianCalendar();

  
    protected final int thisYear = calendar.get(Calendar.YEAR);

   
    protected final int thisMonth = calendar.get(Calendar.MONTH);

   
    private JButton b0;


    private JComboBox monthChoice;

  
    private JComboBox yearChoice;
    
    private String dateText = null;
  
    public CalendarDialog() {
        super();
        setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        buildGUI();
        recompute();
    }

   
    public CalendarDialog(int year, int month, int today) {
        super();
        setYYMMDD(year, month, today);
        buildGUI();
        recompute();
    }

    private void setYYMMDD(int year, int month, int today)
    {
        yy = year;
        mm = month;
        dd = today;
    }

    String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

    /** Build the GUI. Assumes that setYYMMDD has been called. */
    private void buildGUI()
    {
        getAccessibleContext().setAccessibleDescription("Calendar not accessible yet. Sorry!");
        setBorder(BorderFactory.createEtchedBorder());

        setLayout(new BorderLayout());

        JPanel tp = new JPanel();
        monthChoice = new JComboBox();
        for (int i = 0; i < months.length; i++)
            monthChoice.addItem(months[i]);
        monthChoice.setSelectedItem(months[mm]);
        monthChoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                int i = monthChoice.getSelectedIndex();
                if (i >= 0)
                {
                    mm = i;
                    // System.out.println("Month=" + mm);
                    recompute();
                }
            }
        });
        monthChoice.getAccessibleContext().setAccessibleName("Months");
        monthChoice.getAccessibleContext().setAccessibleDescription("Choose a month of the year");

        tp.add(yearChoice = new JComboBox());
        yearChoice.setEditable(true);
        for (int i = yy - 5; i < yy + 5; i++)
            yearChoice.addItem(Integer.toString(i));
        yearChoice.setSelectedItem(Integer.toString(yy));
        yearChoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                int i = yearChoice.getSelectedIndex();
                if (i >= 0)
                {
                    yy = Integer.parseInt(yearChoice.getSelectedItem().toString());
                    // System.out.println("Year=" + yy);
                    recompute();
                }
            }
        });
        
        tp.add(monthChoice);
        add(BorderLayout.CENTER, tp);

        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout(7, 7));
        labs = new JButton[6][7];

        
        bp.add(b0 = new JButton("일"));
        bp.add(new JButton("월"));
        bp.add(new JButton("화"));
        bp.add(new JButton("수"));
        bp.add(new JButton("목"));
        bp.add(new JButton("금"));
        bp.add(new JButton("토"));
       

        ActionListener dateSetter = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String num = e.getActionCommand();
                if (!num.equals(""))
                {
                
                    setDayActive(Integer.parseInt(num));
                    
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(yy).append("-");
                    
                    String temp = ""+(mm+1);
                    if(temp.length() <= 1) {
                     buffer.append("0").append(mm+1);
                    }
                    else {
                     buffer.append(mm+1);
                    }
                    buffer.append("-");
                    
                    temp = ""+dd;
                    if(temp.length() <= 1) {
                     buffer.append("0").append(dd);
                    }
                    else {
                     buffer.append(dd);
                    }
                    
                    dateText = buffer.toString().trim();
                    
                    closeDispose();
                    
                     }
            }

        };

       
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
            {
                bp.add(labs[i][j] = new JButton(""));
                labs[i][j].addActionListener(dateSetter);
            }

        add(BorderLayout.SOUTH, bp);
    }

    public final static int dom[] = { 31, 28, 31, 30, /* jan feb mar apr */  31, 30, 31, 31, /* may jun jul aug */  30, 31, 30, 31 /* sep oct nov dec */ };

    
    private void closeDispose() {
     
     Component compo = getParent();
     
     while(compo != null) {
      if(compo instanceof JDialog) {
          ((JDialog) compo).dispose();
          break;
         }
      else {
       compo = compo.getParent().getParent().getParent();
      }
     }
  
 }
    
  
    protected void recompute()
    {
        // System.out.println("Cal::recompute: " + yy + ":" + mm + ":" + dd);
        if (mm < 0 || mm > 11)
            throw new IllegalArgumentException("Month " + mm + " bad, must be 0-11");
        clearDayActive();
        calendar = new GregorianCalendar(yy, mm, dd);

        // Compute how much to leave before the first.
        // getDay() returns 0 for Sunday, which is just right.
        leadGap = new GregorianCalendar(yy, mm, 1).get(Calendar.DAY_OF_WEEK) - 1;
        // System.out.println("leadGap = " + leadGap);

        int daysInMonth = dom[mm];
        if (isLeap(calendar.get(Calendar.YEAR)) && mm == 1)
            ++daysInMonth;
        
        
        //추가 
        for(int k=0 ; k<labs.length ; k++) {
         for(int r=0 ; r<labs[k].length ; r++) {
          labs[k][r].setText("");
         }
        }
        
        // Blank out the labels before 1st day of month
        for (int i = 0; i < leadGap; i++)
        {
            labs[0][i].setText("");
        }

        // Fill in numbers for the day of month.
        for (int i = 1; i <= daysInMonth; i++)
        {
            JButton b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
            b.setText(Integer.toString(i));
        }

        // 7 days/week * up to 6 rows
        for (int i = leadGap + 1 + daysInMonth; i < 6 * 7; i++)
        {
            labs[(i) / 7][(i) % 7].setText("");
        }

        // Shade current day, only if current month
        if (thisYear == yy && mm == thisMonth)
            setDayActive(dd); // shade the box for today

        // Say we need to be drawn on the screen
        repaint();
    }

    
    public boolean isLeap(int year)
    {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        return false;
    }

    /** Set the year, month, and day */
    public void setDate(int yy, int mm, int dd)
    {
        // System.out.println("Cal::setDate");
        this.yy = yy;
        this.mm = mm; // starts at 0, like Date
        this.dd = dd;
        recompute();
    }

    /** Unset any previously highlighted day */
    private void clearDayActive()
    {
        JButton b;

        // First un-shade the previously-selected square, if any
        if (activeDay > 0)
        {
            b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
            b.setBackground(b0.getBackground());
            b.repaint();
            activeDay = -1;
        }
    }

    private int activeDay = -1;

    /** Set just the day, on the current month */
    public void setDayActive(int newDay)
    {

        clearDayActive();

        // Set the new one
        if (newDay <= 0)
            dd = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
        else
            dd = newDay;
        // Now shade the correct square
        Component square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
        square.setBackground(Color.red);
        square.repaint();
        activeDay = newDay;
    }
    
    public String getDateText () {
     return dateText;
    }

    /** For testing, a main program */
    public static void main(String[] av)
    {
        JFrame f = new JFrame("캘린더");
        Container c = f.getContentPane();
        c.setLayout(new FlowLayout());

        // for this test driver, hardcode 1995/02/10.
        //c.add(new Cal(1995, 2 - 1, 10));

        // and beside it, the current month.
        CalendarDialog dal = new  CalendarDialog();
        c.add(dal);
        
        f.pack();
        f.setVisible(true);
    }
}