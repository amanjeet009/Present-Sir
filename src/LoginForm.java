import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class LoginForm extends JFrame implements ActionListener,MouseListener{
	JLabel title_lbl,student_lbl,faculty_lbl;
	JTextField fac_username_tf,fac_password_tf,stud_username_tf,stud_password_tf;
	JButton login_student,login_faculty;
	Statement statement,stmt;
	Connection con;
	boolean loggedIn=false;
     LoginForm() throws ClassNotFoundException, SQLException{
    	 
    	 Class.forName("com.mysql.jdbc.Driver");
    	 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","");
    	 statement=con.createStatement();
    	 
    	 //adjusting window display
    	 setTitle("ATTENDANCE MANAGER");
    	 setLayout(null);
    	 setSize(600,400);
         setResizable(false);
    	 setBackground(Color.GREEN);
    	 //initialize labels
         setContentPane(new JLabel(new ImageIcon("shredded.png")));

    	 
    	 title_lbl=new JLabel("WELCOME TO ATTENDANCE MANAGER");
    	 title_lbl.setFont(title_lbl.getFont().deriveFont(20.0f));
    	 student_lbl=new JLabel("Login as Student ");
    	 student_lbl.setFont(new Font(student_lbl.getName(), Font.ITALIC, 18));
    	 faculty_lbl=new JLabel("Login as Faculty ");
    	 faculty_lbl.setFont(new Font(faculty_lbl.getName(), Font.ITALIC, 18));
    	 fac_username_tf=new JTextField("  Enter username",15);
    	 fac_password_tf=new JTextField("  Enter Password",15);
    	 stud_username_tf=new JTextField("  Enter enrollment",15);
    	 stud_password_tf=new JTextField("  Enter password",15);
    	
    	 //image
    	 JLabel img =new JLabel(new ImageIcon("line2.png"));
    	 
    	 //create panels
    	 JPanel title_pnl=new JPanel();
    	 title_pnl.setBackground(new Color(00, 00, 45, 100));
    	 JPanel student_pnl=new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
    	 student_pnl.setBackground(new Color(45, 194, 45,213));
    	 JPanel faculty_pnl=new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
    	 faculty_pnl.setBackground(new Color(45, 194, 45,213));
    	 title_pnl.setOpaque(true);
    	 student_pnl.setOpaque(false);
    	 faculty_pnl.setOpaque(false);
    	 
    	 //setting style
    	 title_lbl.setForeground(Color .white);
    	 student_lbl.setForeground(Color .white);
    	 faculty_lbl.setForeground(Color .white);
    	 
    	 //sizing textfields
    	 stud_username_tf.setPreferredSize(new Dimension(100,27));
    	 stud_password_tf.setPreferredSize(new Dimension(100,27));
    	 fac_username_tf.setPreferredSize(new Dimension(100,27));
    	 fac_password_tf.setPreferredSize(new Dimension(100,27));
    	 
    	 
         //create menu bar
    	 JMenuBar mb = new JMenuBar();
    	 JMenu m1 = new JMenu("FILE");
    	 JMenu m2 = new JMenu("Help");
    	 mb.add(m1);
    	 mb.add(m2);
    	 JMenuItem m11 = new JMenuItem("Open");
    	 JMenuItem m22 =new JMenuItem("Save as");
    	 m1.add(m11);
    	 m1.add(m22);
    	 
    	 //create buttons
    	  login_faculty=new JButton(new ImageIcon("login_btn.png"));
    	  login_faculty.setOpaque(false);
    	  login_faculty.setContentAreaFilled(false);
    	  login_faculty.setBorderPainted(false);
    	  login_student=new JButton(new ImageIcon("login_btn.png"));
    	  login_student.setOpaque(false);
    	  login_student.setContentAreaFilled(false);
    	  login_student.setBorderPainted(false);
    	  //login_student.setEnabled(true);
    	  login_student.setPreferredSize(new Dimension(80,40));
    	  login_faculty.setPreferredSize(new Dimension(80,40));
    	 
    	 //add items to panel 1
    	 title_pnl.add(title_lbl);
    	 
    	 //add items to panel 2
    	 student_pnl.add(student_lbl);
    	 student_pnl.add(stud_username_tf);
    	 student_pnl.add(stud_password_tf);
    	 student_pnl.add(login_student);
    	 


    	 //add items to panel 3
    	 faculty_pnl.add(faculty_lbl);
    	 faculty_pnl.add(fac_username_tf);
    	 faculty_pnl.add(fac_password_tf);
    	 faculty_pnl.add(login_faculty);
    	 
    	 
    	 //sizing and positioning panels
    	 mb.setBounds(0,0,this.getWidth(),25);
    	 title_pnl.setBounds(0,50,this.getWidth(),35);
    	 student_pnl.setBounds(20,100,260,250);
         student_pnl.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
         faculty_pnl.setBounds(320,100,250,250);         
         faculty_pnl.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
         img.setBounds(299, 100, 2, 250);
         
         
         //add listeners
         login_student.addActionListener(this);
         login_faculty.addActionListener(this);
         fac_username_tf.addMouseListener(this);
         fac_password_tf.addMouseListener(this);
         stud_username_tf.addMouseListener(this);
         stud_password_tf.addMouseListener(this);
         
         
         //add components to frame
         add(mb);
         add(title_pnl);
         add(img);
         add(student_pnl);
         add(faculty_pnl);
       
        
         
         //set windows visible
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
   
	     

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		try {
			 stmt=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean found=false;
		String name = "ok";
		
		
		
		if(arg0.getSource()==login_student)
		{	
			
			try{
                  if(stud_username_tf.getText().toString().equals("") ||
                		  stud_password_tf.getText().toString().equals("")  )
      				JOptionPane.showMessageDialog (null, "Username or Password cannot be empty!","LOGIN FAILED",JOptionPane.ERROR_MESSAGE);

                  else{
				rs=statement.executeQuery("select * from course ");
				ResultSet rs2=null;
				while(rs.next()){
					
				System.out.println(rs.getString("course_id"));
				rs2=stmt.executeQuery("select * from "+rs.getString("course_id")+" where stud_univ_roll = \""+stud_username_tf.getText()+
						"\" and stud_password = \""+stud_password_tf.getText()+"\" ;");
				if(rs2.next()){
				    loggedIn=true;
					new StudentDetails(rs2.getString("stud_name"),rs2.getString("Stud_father_name"),rs2.getString("stud_univ_roll"));
					break;
					
				}
				
				
				}
				if(loggedIn==false)
				 JOptionPane.showMessageDialog (null, "Username and Password do not match!!","LOGIN FAILED",JOptionPane.ERROR_MESSAGE);
				else
					dispose();
                  }
				
				
				}catch(Exception e){
					System.out.println(e);
				}

			
			
		}
		
		 if(arg0.getSource()==login_faculty)
		{	
			try{ if(fac_username_tf.getText().toString().equals("") ||
          		  fac_password_tf.getText().toString().equals("")  )
				JOptionPane.showMessageDialog (null, "Username or Password cannot be empty!","LOGIN FAILED",JOptionPane.ERROR_MESSAGE);

			else{
			
				rs=statement.executeQuery("select * from faculty where fac_name like \""+fac_username_tf.getText()+
						"\" and fac_password like \""+fac_password_tf.getText()+"\" ;");
			
				if(rs.next())
				{
					new FacultyDetails(fac_username_tf.getText().toString(),rs.getString("fac_id"));
					dispose();
				}
				else JOptionPane.showMessageDialog (null, "Username and Password do not match!!","LOGIN FAILED",JOptionPane.ERROR_MESSAGE);
					
			}}
			catch(Exception e){}
			
		}
		
		
	}
	
	
	public static void main(String a[]){
		
		try {
			new LoginForm();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//fac_username_tf,fac_password_tf,stud_username_tf,stud_password_tf
		if(arg0.getSource()==fac_username_tf)
			fac_username_tf.setText("");
		if(arg0.getSource()==fac_password_tf)
			fac_password_tf.setText("");
		if(arg0.getSource()==stud_username_tf)
			stud_username_tf.setText("");
		if(arg0.getSource()==stud_password_tf)
			stud_password_tf.setText("");
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
