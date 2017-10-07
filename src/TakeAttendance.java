import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.Context;
import javax.swing.*;

public class TakeAttendance extends JFrame implements ActionListener{

	JScrollPane attendance_pane;
	JPanel btn_pnl,container_pnl,head_pnl;
	JButton submit_btn,all_present_btn,all_absent_btn,back_btn;
	JLabel class_lbl;
	Statement statement=null;
	String subject_tableName,class_tableName;
	//ResultSet rs=null;
	ArrayList<String> presentStuds=new ArrayList<String>();
	Connection con;
	
	TakeAttendance(String sub_code, String class_code,JFrame prev_frame) throws Exception{
		
		 Class.forName("com.mysql.jdbc.Driver");
    	  con=DriverManager.getConnection(
    			 "jdbc:mysql://localhost:3306/attendance","root","");
    	 statement=con.createStatement();
    	 subject_tableName=sub_code;
    	 class_tableName=class_code;
		setSize(700,500);
		setDefaultLookAndFeelDecorated(true);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		//initialization
		class_lbl=new JLabel(class_code);
		head_pnl=new JPanel(new FlowLayout());
		btn_pnl=new JPanel();
		submit_btn=new JButton("SUBMIT");
		back_btn=new JButton("Back");
		all_present_btn=new JButton("Mark all Present");
		all_absent_btn=new JButton("Mark all Absent");
		container_pnl=new JPanel();
		attendance_pane=new JScrollPane(container_pnl);
		
        setContentPane(new JLabel(new ImageIcon("shredded.png")));
        
		//add to scroll
		ResultSet rs=statement.executeQuery("select * from "+class_code+" order by stud_class_roll");
		while(rs.next())
		addAttendanceItem(container_pnl,rs.getString("stud_name"),rs.getString("stud_univ_roll"),rs.getInt("stud_class_roll")+"");
		rs.close();
		//setting styles
	    container_pnl.setOpaque(false);
	    head_pnl.setOpaque(false);
	    btn_pnl.setOpaque(false);
	    class_lbl.setForeground(Color .white);
		//attendance_pane.setBackground(Color.BLACK);
		btn_pnl.setLayout(new FlowLayout());
		container_pnl.setLayout(new BoxLayout(container_pnl,BoxLayout.Y_AXIS));
		class_lbl.setFont(new Font(class_lbl.getName(), Font.ITALIC, 20));
		attendance_pane.getViewport().setOpaque(false);
		attendance_pane.setOpaque(false);
		//setting positions
	    attendance_pane.setBounds(15,70,665,330);
	    btn_pnl.setBounds(0, 410, 690, 100);
	    head_pnl.setBounds(0, 10, 700, 50);
	    back_btn.setBounds(10,10,100,40);
	
	    //Event handling
	    all_present_btn.addActionListener(this);
	    all_absent_btn.addActionListener(this);
	    back_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				prev_frame.setVisible(true);
				dispose();
			}
	    	
	    });
	    
	    submit_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//get all components
				Component[] component=container_pnl.getComponents();
				for(Component c:component){
					Container container=(Container)c;
					Component[] comp=container.getComponents();
					try{
					JRadioButton p=(JRadioButton)comp[3];
					JRadioButton a=(JRadioButton)comp[4];
					
					if(!p.isSelected()&&!a.isSelected()){
						JOptionPane.showMessageDialog (null, "Please Mark all students!","SUBMIT FAILED",JOptionPane.ERROR_MESSAGE);
						break;
					}
					else{
						prev_frame.setVisible(true);
						dispose();
					}
					}catch(Exception e){}
				}
				for(String c:presentStuds){
					System.out.print(c+" ");
				}
				
				
				// TODO Auto-generated method stub
			  //  JDialog.setDefaultLookAndFeelDecorated(true);
			   // int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to Submit?\n Press Yes to Confirm\n Press No to return\n", "Confirm",
			   //     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				try {
					updateAttendanceInDatabase(presentStuds);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			   /* if (response == JOptionPane.NO_OPTION) {
			      System.out.println("No button clicked");
			    } else if (response == JOptionPane.YES_OPTION) {
			      System.out.println("Yes button clicked");
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			      System.out.println("JOptionPane closed");
			    }*/
			  }
			
	    	
	    });
	    
	    
	    //add
	    head_pnl.add(class_lbl);
	    btn_pnl.add(all_present_btn);
	    btn_pnl.add(submit_btn);
	    btn_pnl.add(all_absent_btn);
	    
	    //add to frame
	    add(head_pnl);
	    add(attendance_pane);
	    add(btn_pnl);
	    add(back_btn);
	    

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        this.setLocation(x, y);
	    
        
        
        submit_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0;i<presentStuds.size();i++){
				    System.out.println(presentStuds.get(i));
				} 
			}
        	
        	 
        });
	
	    
	    setVisible(true);
	    setResizable(false);
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       try {
		//new TakeAttendance("tcs501","btechcs5a",this);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	void addAttendanceItem(JPanel contain,String name,String univ_roll,String roll){
	     JLabel roll_lbl=new JLabel(roll);
	     JLabel univ_roll_lbl=new JLabel(univ_roll);
	     JLabel name_lbl=new JLabel(name);
	     JPanel pnl=new JPanel();
			pnl.setLayout(null);
			pnl.setBackground(new Color(0,0,0,100));
			//pnl.setOpaque(false);
		JRadioButton present=new JRadioButton("Present");
		present.setPreferredSize(new Dimension(10,10));
		//present.setSelected(true);
		JRadioButton absent=new JRadioButton("Absent");
		ButtonGroup bg=new ButtonGroup();
		bg.add(present);
		bg.add(absent);
		
		roll_lbl.setBounds(30,13,50,50);roll_lbl.setForeground(Color .white);
		name_lbl.setBounds(90,13,250,50);name_lbl.setForeground(Color .white);
		univ_roll_lbl.setBounds(350,13,150,50);univ_roll_lbl.setForeground(Color .white);
		present.setBounds(450,23,80,30);
		absent.setBounds(560,23,75,30);
		pnl.add(roll_lbl);
		pnl.add(name_lbl);
		pnl.add(univ_roll_lbl);
		pnl.add(present);
		pnl.add(absent);
		contain.add(pnl);
		pnl.setPreferredSize(new Dimension(100,70));
		contain.add(Box.createRigidArea(new Dimension(0,5)));
		present.addActionListener(new ActionListener(){
        
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				performOnPresentClicked( present, pnl,univ_roll);
			}
			
		});
		
		absent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				 performOnAbsentClicked( absent,pnl,univ_roll)	;
			
			}
			
			
		});
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getSource()==all_present_btn){
			Component[] component=container_pnl.getComponents();
			for(byte i=0;i<component.length;i++){
				//component[i].setBackground(Color.GREEN);
				Container con=(Container)component[i];
				Component c[]=con.getComponents();
				//System.out.println(c.length);
				try{
			     JRadioButton rb=(JRadioButton) c[3];
			     JLabel lbl=(JLabel)c[2];
			     performOnPresentClicked(rb,(JPanel)component[i],lbl.getText());
				rb.setSelected(true);
				}catch(Exception ae){}
			}
		}
			else if(e.getSource()==all_absent_btn){
				Component[] component=container_pnl.getComponents();
				for(byte i=0;i<component.length;i++){

					//component[i].setBackground(Color.RED);
					Container con=(Container)component[i];
					Component c[]=con.getComponents();
					//System.out.println(c.length);
					try{
				     JRadioButton rb=(JRadioButton) c[4];
				     JLabel lbl=(JLabel)c[2];
				     System.out.println(lbl.getText());
				     performOnAbsentClicked(rb,(JPanel)component[i],lbl.getText().toString());
					rb.setSelected(true);
					}catch(Exception ae){}
				}
				
			
		}
		
		
		
	}
	
	void performOnAbsentClicked(JRadioButton absent,JPanel pnl,String univ_roll){
		System.out.println("\n\n\n");
		//if(absent.isSelected()){
			pnl.setBackground(new Color(225,0,0));
		
			ListIterator<String> it = presentStuds.listIterator();
			while (it.hasNext()) {
			  if (it.next().contains(univ_roll)) {
			    it.remove();
			  }
			}
		
		//}
	
	}
	
      void performOnPresentClicked(JRadioButton present,JPanel pnl,String univ_roll){
    	//  if(present.isSelected()){
				pnl.setBackground(new Color(0,225,0));
				presentStuds.add(univ_roll);
		//	}
	}
      
      void updateAttendanceInDatabase(ArrayList<String> present_studs) throws SQLException{
    	 Statement statement2=con.createStatement();
    	 ResultSet rs2=null;
    	  for(String roll : present_studs){
    		  ResultSet rs=statement.executeQuery("SELECT * FROM "+subject_tableName+
    				  " WHERE stud_univ_roll = \""+roll+"\"");
    		  if(!rs.next()){
    		 statement.executeUpdate("INSERT INTO "+subject_tableName
    				 +"(stud_univ_roll,total_lect,attended_lect) values("+roll+",0,0)");
    		  System.out.println("inside insert");
    		  }
    		  statement.executeUpdate("UPDATE  "+subject_tableName+" SET attended_lect"
    	  		 		+ " = attended_lect + 1 WHERE stud_univ_roll= \""+roll+"\""); 
    		  System.out.println("inside update");
    	  }
    	  
    	 
    	  
    	  ResultSet rs=statement.executeQuery("SELECT stud_univ_roll FROM "+class_tableName);
    	  
    	  while(rs.next()){
    		  
    		  rs2=statement2.executeQuery("SELECT * FROM "+subject_tableName+
    				  " WHERE stud_univ_roll = \""+rs.getString("stud_univ_roll")+"\"");
    		  if(!rs2.next())
    			  try{
    	    		 statement2.executeUpdate("INSERT INTO "+subject_tableName
    	    				 +"(stud_univ_roll,total_lect,attended_lect) values("+rs.getString("stud_univ_roll")+",0,0)");
    			  }catch(NullPointerException e){System.out.println(e);}
    	    		 try{
    	    		  statement2.executeUpdate("UPDATE  "+subject_tableName+" SET total_lect"
    	    	  		 		+ " = total_lect + 1 WHERE stud_univ_roll= \""+rs.getString("stud_univ_roll")+"\"");  
    	    		}catch(NullPointerException e){System.out.println(e);}
    	  }
      }

}
