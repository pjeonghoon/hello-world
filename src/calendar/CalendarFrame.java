package calendar;

import java.awt.*;

import java.awt.event.*;

import java.util.*;



class MyWindowAdapter extends WindowAdapter { 

        // implement WindowListener methods 

public void windowClosing(WindowEvent we) { 

System.exit(0); 

}

} 



public class CalendarFrame extends Frame implements ActionListener{

FlowLayout f = new FlowLayout(FlowLayout.LEFT,3,5);

GridLayout gl =  new GridLayout(1,7);

Calendar nowcal = Calendar.getInstance();

//��ġ������ �ߺ������ ���� Panel

Panel selectPanel = new Panel();

Panel labelPanel = new Panel();

Panel dayPanel[] = new Panel[6];

//������ù� Ȯ�� ������Ʈ

Choice cyear = new Choice();

Choice cmonth = new Choice();

Label lyear = new Label("��");

Label lmonth = new Label("��");

Button bt = new Button("Ȯ��");

//���� ������Ʈ

Label l[] = new Label[7];

//���� ������Ʈ

Label lday[] = new Label[32];

//������

public CalendarFrame() {

this("�޷�"); 

}

public CalendarFrame(String s){

super(s);



//�� ���̽� ������Ʈ

for (int i = 2005; i < 2015; i++) {

String year = " " + i;

cyear.add(year);

}

//�� ���̽� ������Ʈ

for (int i = 1; i < 13; i++) {

String month = " " + i;

cmonth.add(month);

}

//��ư �׼Ǹ����� ����

bt.addActionListener(this);

//���� ������Ʈ

        String lstr[] = {"  ��","��","ȭ","��","��","��","��"};

for (int i = 0; i < 7; i++) {

l[i] = new Label(lstr[i]);

}

//��¥ ������Ʈ

lday[0] = new Label(" ");

for (int i = 1; i < lday.length; i++) {

String sday = " ";

if(i < 10)

sday = "   "+ i;

else

sday = " "+ i;

lday[i] = new Label(sday);

lday[i].setSize(5,5);

} 

//���� ��¥ �ޱ� 

int year = nowcal.get(Calendar.YEAR);

int month = nowcal.get(Calendar.MONTH);

//cyear.select(year);

cmonth.select(month); 


//����Ϲ�Ȯ�� ������Ʈ ��ġ

selectPanel.setLayout(f);

selectPanel.add(cyear);

selectPanel.add(lyear);

selectPanel.add(cmonth);

selectPanel.add(lmonth);

selectPanel.add(bt);

add(selectPanel);

//����������Ʈ��ġ

    labelPanel.setLayout(gl);

for (int i = 0; i < l.length; i++) {

     labelPanel.add(l[i]);

    } 

add(labelPanel);

    //��¥ ������Ʈ ��ġ �޼ҵ� ȣ��

daytext(year,month);

//�����Ӽ��� 

setSize(235,250);

setVisible(true);

setLayout(f);

addWindowListener(new MyWindowAdapter());

}

/**

 * ��¥ ������Ʈ ��ġ �޼ҵ�

 * @param year 

 * @param month

 */

public void daytext(int year, int month ){

Calendar setcal = Calendar.getInstance();

setcal.set(year,month,1);

//�״��� ù��°���� ���� �ޱ�

int firstday = setcal.get(Calendar.DAY_OF_WEEK) ;

//�״��� �������� �ޱ�

int lastday = setcal.getActualMaximum(Calendar.DAY_OF_MONTH);

//��¥ �ε���

int dayindex = 1;

int sunday = 1;

// int saturday;

//��ġ

dayPanel[0] = new Panel();

dayPanel[0].setLayout(new GridLayout(6,7));

//��Ʈ

//Font redfont = new Font("",Color.red);

//1���� �Ǳ��� ���� ä���

    for (int i = 1; i < firstday; i++) {

dayPanel[0].add(new Label("  "));

    sunday++;

    }

//1�� ���� ��¥ ä���

for (int j = 0; j < lastday; j++) {

    if(sunday%7 == 1)lday[dayindex].setForeground(Color.red);

    else if(sunday%7 == 0)lday[dayindex].setForeground(Color.blue);

    else lday[dayindex].setForeground(Color.black);

    sunday++;

    dayPanel[0].add(lday[dayindex++]);

}

//������ ����ä���

while (dayPanel[0].getComponentCount() < 42) {

dayPanel[0].add(new Label("  "));

}

this.add(dayPanel[0]);

}

/**

 * ��ư �׼Ǹ����� 

 * ��ưŬ�� �� �޷� ���

 */

@Override

public void actionPerformed(ActionEvent e) {

// TODO Auto-generated method stub

int year = cyear.getSelectedIndex() + 2005;

int month = cmonth.getSelectedIndex();

System.out.println(year+" "+month);

//��¥ ������Ʈ �����

dayPanel[0].setVisible(false);


//��¥��ġ ������Ʈ ȣ��

daytext(year,month);

    //�ٽ� �׸���

setVisible(true);

}


public static void main(String[] args) {

CalendarFrame cf = new CalendarFrame();

}

}
