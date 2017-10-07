import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class FacultyDetails{
	JPanel panelContainer;
JLabel hello_lbl,date_lbl,schedule_lbl;
JScrollPane sp;	
JList schedule_list;
Statement statement=null;
JButton logout_btn;
ResultSet rs;
JFrame frame;
	FacultyDetails(String fac_name, String fac_id) throws Exception{
		frame=new JFrame();
		 Class.forName("com.mysql.jdbc.Driver");
    	 Connection con=DriverManager.getConnection(
    			 "jdbc:mysql://localhost:3306/attendance","root","");
    	 statement=con.createStatement();
		
    	String time=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
    	frame.setDefaultLookAndFeelDecorated(true);
    	frame.setSize(700,500);
    	frame.setLayout(null);
		//setContentPane(new JLabel(new ImageIcon("soberBg.jpg")));
    	frame.setContentPane(new JLabel(new ImageIcon("shredded.png")));
		//initialization
		 panelContainer=new JPanel();
		panelContainer.setLayout(new BoxLayout(panelContainer,BoxLayout.Y_AXIS));
		//panelContainer.setOpaque(false);
		//panelContainer.setBackground(Color.RED);
		sp=new JScrollPane(panelContainer);
       
		//sp.getViewport().setBackground(Color.RED);;
		//sp.getViewport().setOpaque(false);
		//sp.setOpaque(false);
		schedule_lbl=new JLabel("Today's schedule:",SwingConstants.HORIZONTAL);
		date_lbl=new JLabel(getDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))+", "+time.substring(6, 8)+"th "+getMonth(time.substring(4,6))+" "+time.substring(0, 4),SwingConstants.CENTER);
		hello_lbl=new JLabel("** Hello, "+fac_name+" **",SwingConstants.CENTER);
		logout_btn=new JButton(new ImageIcon("logout.png"));

		
		//execute query and add items to scroll bar
		rs=statement.executeQuery("select * from "+fac_id+" where day like \""+getDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))+"\" order by schedule_time;");
		while(rs.next()){
			
			
			addScheduleItem(panelContainer,rs.getString("sub_code"),rs.getString("class"),rs.getString("schedule_time"));
		
		}
		
		
		//setting styles
		panelContainer.setBorder(new EmptyBorder(5,15,5,15));
		hello_lbl.setFont(hello_lbl.getFont().deriveFont(30.0f));
		date_lbl.setFont(new Font(date_lbl.getName(), Font.ITALIC, 20));
		schedule_lbl.setFont(schedule_lbl.getFont().deriveFont(20.0f));
		logout_btn.setBorderPainted(true);;
		logout_btn.setBackground(Color.WHITE);
		hello_lbl.setForeground(Color .white);
		date_lbl.setForeground(Color .white);
		schedule_lbl.setForeground(Color .white);
		sp.getViewport().setOpaque(false);
		sp.setOpaque(false);
		panelContainer.setOpaque(false);
		
		//setting positions
		hello_lbl.setBounds(0,0,700,100);
		date_lbl.setBounds(0,80,700,100);
		sp.setBounds(10,250,675,200);
		schedule_lbl.setBounds(0,170,700,100);
		logout_btn.setBounds(5,5,50,30);
		logout_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					new LoginForm();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.dispose();
			}
			
		});

		//add to frame
		frame.add(hello_lbl,JLabel.CENTER);
		frame.add(logout_btn);
		frame.add(date_lbl);
		frame.add(sp);
		frame.add(schedule_lbl);
		
		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = frame.getSize().width;
        int h =frame.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        frame.setLocation(x, y);
		
        frame.setVisible(true);
        frame.setResizable(false);
	}
	

	public static void main(String a[]){
		try {
			new FacultyDetails("Nishu Chawla","fac100");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String getDay(int num){
		String day="";
		switch (num){
		case 1:day="Sunday";break;
		case 2:day="Monday";break;
		case 3:day="Tuesday";break;
		case 4:day="Wednesday";break;
		case 5:day="Thursday";break;
		case 6:day="Friday";break;
		case 7:day="Saturday";break;
		}
		return day;
	}
	
	
	
	
	String getMonth(String n){
		String month="";
		int num=Integer.parseInt(n);
		switch(num){
		case 1:month= "January"; break;
		case 2:month= "February"; break;
		case 3:month= "March"; break;
		case 4:month= "April"; break;
		case 5:month= "May"; break;
		case 6:month= "June"; break;
		case 7:month= "July"; break;
		case 8:month= "August"; break;
		case 9:month= "September"; break;
		case 10:month= "October"; break;
		case 11:month= "November"; break;
		case 12:month= "December"; break;
		}
		return month;
	}
	
	void addScheduleItem(JPanel contain,String subject_code,String clas,String time){
		JButton btn =new JButton("Take attendance >>");
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(1,4,0,0));
		char[] subCode=subject_code.toCharArray();
		for(byte i=0;i<3;i++) subCode[i]-=32;
		panel.add(new JLabel(new String(subCode)));
		panel.add(new JLabel(clas));
		panel.add(new JLabel(time));
		panel.add(btn);
		//panel.setOpaque(false);
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try {
					//statement.close();
					
					new TakeAttendance(subject_code,clas,frame);
					frame.setVisible(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EmptyBorder(5,5,5,5));
		panel.setPreferredSize(new Dimension(100,70));
		contain.add(panel);
		contain.add(Box.createRigidArea(new Dimension(0,5)));
		
	}
}
