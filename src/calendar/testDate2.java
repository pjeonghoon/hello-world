package calendar;

import java.awt.Container;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JFrame;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

class date2 {

    void GUI() {
        JFrame f1 = new JFrame();
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(300, 300);
        f1.setVisible(true);

        Container conn = f1.getContentPane();
        conn.setLayout(null);

      
    }

}

public class testDate2 {

	public void main(String[] args) {

        date2 d1 = new date2();
        d1.GUI();

    }
	
}
