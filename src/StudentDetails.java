import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class StudentDetails extends JFrame {

	JLabel stud_name_lbl,stud_father_lbl,stud_roll_lbl;
	JPanel container_pnl,side_pnl;
	JButton logout_btn;
	JLabel photo;
	java.sql.Connection con;
	Statement statement,statement2;
	StudentDetails(String stud_name,String stud_father_name,String stud_univ_roll) throws SQLException, ClassNotFoundException{
		
		
		Class.forName("com.mysql.jdbc.Driver");
   	  con= DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","");
   	 statement=con.createStatement();
		
		
		setContentPane(new JLabel(new ImageIcon("wood.png")));
		setSize(700,500);
		setResizable(false);
		setLayout(null);
		
		//initialize labels and text fields
		stud_name_lbl=new JLabel(stud_name);
		stud_name_lbl.setFont(new Font(stud_name_lbl.getName(), Font.ITALIC, 22));
		stud_father_lbl=new JLabel(stud_father_name);
		stud_father_lbl.setFont(new Font(stud_father_lbl.getName(), Font.ITALIC, 18));
		stud_roll_lbl=new JLabel(stud_univ_roll);
		stud_roll_lbl.setFont(new Font(stud_roll_lbl.getName(), Font.ITALIC, 18));
		photo=new JLabel(new ImageIcon("person.jpg"));
		logout_btn=new JButton(new ImageIcon("logout.png"));
		
		
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
				dispose();
			}
			
		});
		
		
		//create panels
		 side_pnl=new JPanel(new FlowLayout(FlowLayout.CENTER,50,20));
		 container_pnl=new JPanel(new FlowLayout(FlowLayout.LEFT,25,20));
		
		//setting styles
		side_pnl.setBackground(new Color(00, 00, 00, 150));
		logout_btn.setBackground(Color.WHITE);
		stud_name_lbl.setForeground(Color .WHITE);
		stud_father_lbl.setForeground(Color .WHITE);
		stud_roll_lbl.setForeground(Color .WHITE);
		
		
		//side_pnl.setOpaque(false);
		//container_pnl.setOpaque(false);
		container_pnl.setBackground(new Color(255, 255, 255,20));
		

		//add items to panels
		 side_pnl.add(photo);
		 side_pnl.add(stud_name_lbl);
		 side_pnl.add(stud_father_lbl);
		 side_pnl.add(stud_roll_lbl);

		
		//locate panels
        side_pnl.setBounds(10,50,200,400);
        container_pnl.setBounds(220,50,460,400);
        logout_btn.setBounds(5, 5, 50, 30);
        
        ResultSet rs=(ResultSet) statement.executeQuery("SELECT * from subject ;");
        statement2=con.createStatement();
        while(rs.next()){
 
        	ResultSet rs2= (ResultSet) statement2.executeQuery("SELECT * FROM "+rs.getString("sub_code")
        	+" WHERE stud_univ_roll = \""+stud_univ_roll+"\"");
        	if(rs2.next()){
        		System.out.println("Hello");
        		addToContainer(container_pnl,rs.getString("sub_name"),rs2.getInt("attended_lect"),rs2.getInt("total_lect"));
        		
        	}
        }

		
		//add Panels
         add(side_pnl);
         add(container_pnl);
         add(logout_btn);
         
 		setVisible(true);

         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

         // Determine the new location of the window
         int w = this.getSize().width;
         int h = this.getSize().height;
         int x = (dim.width-w)/2;
         int y = (dim.height-h)/2;

         // Move the window
         this.setLocation(x, y);
         
         
         
		
	}
	public static void main(String a[]){
		try {
			new StudentDetails("u","7","1011493");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void addToContainer(JPanel contain,String subject,int attended,int total){
		
		JProgressBar progressBar=new JProgressBar(0,100);
		float perc=(float) ((((float)attended)/total)*100);
		System.out.println(perc);
		JPanel panel=new JPanel();
		JLabel subject_lbl=new JLabel(subject);
		subject_lbl.setPreferredSize(new Dimension(120,30));
		JLabel attended_lbl=new JLabel(attended+" / "+total);
		attended_lbl.setPreferredSize(new Dimension(120,30));
		subject_lbl.setHorizontalAlignment(JLabel.CENTER);
		attended_lbl.setHorizontalAlignment(JLabel.CENTER);
		progressBar.setValue((int)perc);
		progressBar.setPreferredSize(new Dimension(100,20));
		progressBar.setString(Math.round(perc*100)/100.0+"%");
		progressBar.setStringPainted(true);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,5));
		panel.setPreferredSize(new Dimension(120,100));
		//panel.setBorder(new EmptyBorder(10,10,10,10));

       if(perc<70.00)
    	   panel.setBackground(Color.RED);
       else
    	   panel.setBackground(new Color(49, 215, 49, 200));
			
        panel.add(subject_lbl);
        panel.add(attended_lbl);
		panel.add(progressBar);
		contain.add(panel);
	}

}
