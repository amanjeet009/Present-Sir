import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Information extends JFrame {
	JLabel salutation_lbl,date_lbl;
	JPanel salutation_panel,date_panel;
   Information(String s){
	   
	   //adjusting frame display
	   setSize(600,400);
	   setLayout(new GridLayout(2,1));
	   
	   //initializing objects
	   salutation_lbl=new JLabel("Hello, "+s);
	   date_lbl=new JLabel("Tuesday, 18 March 2017");
	   
	   
	   salutation_panel=new JPanel();
	   date_panel=new JPanel();
	   salutation_panel.add(salutation_lbl);
	   date_panel.add(date_lbl);
	   add(salutation_panel);
	   add(date_panel);
	   
	   setVisible(true);
 
   }
}
