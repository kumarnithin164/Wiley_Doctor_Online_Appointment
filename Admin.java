package Wiley_DoctorAppointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Admin {
	int adminId=0;	
    String adminName="";
    String contactNumber="";
    String email="";
    String password="";
    String url;
    String uname;
    String pwd;
    static Connection con;
    
    boolean adminRegister(int adminId,String adminName,String contactNumber,String email,String password){
        if(this.adminId==0) {
    	this.adminId=adminId;
        this.adminName=adminName;
        this.contactNumber=contactNumber;
        this.email=email;
        this.password=password;
        System.out.println("Registration Successful");
        admindashboard();
        return true;
        }
        else {
        
        System.out.println("Already registered login please");
        return false;
        
        }
    }
    
    public Admin() throws ClassNotFoundException{
		String url = "jdbc:mysql://localhost:3306/AvailabilitySchema";
		String uname = "root";
		String pass = "conormcgregor";
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
		con=DriverManager.getConnection(url,uname,pass);
		}
		catch(Exception e) {
			System.out.println("DB error");
		}
        
        
    }
    
    boolean adminLogin(int adminId,String password) throws SQLException{
    	PreparedStatement s=con.prepareStatement("Select AdminId, Password from AdminTable");
		ResultSet r=s.executeQuery();
		if(r.next()) {
        if(r.getInt("AdminId")==adminId && r.getString("Password").equals(password)) {
        	System.out.println("Successfully logged in..\n");
        	admindashboard();
        	return true;
        }
        else {
        	System.out.println("Invalid credentials!!!\n");
        	return false;
        }
	  }else System.out.println("No Admin found..\n");
		return false;
    }
    
    void admindashboard(){
        try {
        	PreparedStatement s=con.prepareStatement("Select * from AvailabilityTable order by DoctorDate, InTime");
    		ResultSet r=s.executeQuery();
    		System.out.println("***List of all the Available Doctors..");
    		System.out.println("+---------+-----------+---------+--------+");
    		System.out.println("|Doctor id"+"|"+"Date"+ "       |"+"In Time"+ "  |"+"Out Time|");
    		System.out.println("+---------+-----------+---------+--------+");
    		while(r.next()) {
    			System.out.println("|"+r.getInt("DoctorId")+"        |"+r.getDate("DoctorDate")+ " |"+r.getTime("InTime")+ " |"+r.getTime("OutTime")+"|");
    		}
			System.out.println("+---------+-----------+---------+--------+\n");

    		System.out.println();
    		PreparedStatement s1=con.prepareStatement("Select * from AppointmentTable order by aDate, aTime");
    		ResultSet r1=s1.executeQuery();
    		System.out.println("***List of all the Appointments..");
    		System.out.println("+--------------+----------+---------+----------+---------+");
    		System.out.println("|Appointment Id|Patient Id|Doctor Id|   Date   |   Time  |");
    		System.out.println("+--------------+----------+---------+----------+---------+");
    		while(r1.next()) {
    			System.out.println("|"+r1.getInt("aId")+"             |"+r1.getInt("PatientId")+ "       |"+r1.getInt("DoctorId")+ "        |"+r1.getDate("aDate")+"|"+r1.getTime("aTime")+" |");
    		}
			System.out.println("+--------------+----------+---------+----------+---------+\n");
        }
        catch(Exception e) {
        	System.out.println("SQL error");
        }
    }
    
    void adminViewDoctor(int doctorId){
    	try {
        	PreparedStatement s=con.prepareStatement("Select * from AvailabilityTable where DoctorId=?");
        	s.setInt(1, doctorId);
    		ResultSet r=s.executeQuery();
    		if(!r.next()) { System.out.println("The Doctor "+doctorId+ " is busy and not available!!!\n"); return; }
    			System.out.println("***List of all the slots Doctor " + doctorId + " is available on..");
	    		System.out.println("+---------+-----------+---------+--------+");
	    		System.out.println("|Doctor id"+"|"+"Date"+ "       |"+"In Time"+ "  |"+"Out Time|");
	    		System.out.println("+---------+-----------+---------+--------+");
	    	do{
	    		System.out.println("|"+r.getInt("DoctorId")+"        |"+r.getDate("DoctorDate")+ " |"+r.getTime("InTime")+ " |"+r.getTime("OutTime")+"|");
	    	}while(r.next()); 
			System.out.println("+---------+-----------+---------+--------+\n");
    		
        }
        catch(Exception e) {
        	System.out.println("SQL error");
        }
    }
    void removeAppointment(int appointmentId){
     //remove appointment for a doctor 
    	try {

    		PreparedStatement s1=con.prepareStatement("DELETE from AppointmentTable where aId=?");
	    	s1.setInt(1, appointmentId);
	    	int i=s1.executeUpdate();
	    	if(i>0) {
	    		System.out.println("Successful removal!!!\n");
	    		// con.commit();
	    		admindashboard();
	    	}
	    	else {
	    		System.out.println("Removal unsuccessful!!!\n");
	    	}
    	}
    	 catch(Exception e) {
         	System.out.println("SQL error");
         }
    }
    
    void modifyAppointment(int appointmentId,int docid){
    	try {
        	PreparedStatement s2=con.prepareStatement("UPDATE AppointmentTable SET DoctorId=? where aId=?");
        	s2.setInt(1, docid);
        	s2.setInt(2, appointmentId);
        	int i=s2.executeUpdate();
        	if(i>0) {
        		System.out.println("Successful modification..!!!");
        		// con.commit();
        		admindashboard();
        	}
        	}
        	 catch(Exception e) {
             	System.out.println("SQL error");
             }
    }

    public void adminStart() throws Exception {
		boolean loop=true;
		boolean loop2=true;
		Scanner sc=new Scanner(System.in);
		int c1;
		Admin d=new Admin();
		while(loop) {
		System.out.println("***------------------------------Welcome to the Admin page------------------------------***");
		System.out.println("Enter the option: \n1) Login \n2) Exit");
		System.out.print("Enter your choice: ");
		c1=sc.nextInt();
		System.out.println();
		switch(c1) {
		case 1:
			System.out.println("***Login...");
			System.out.print("Enter Admin Id: ");
			int a_id=sc.nextInt();
			System.out.print("Enter password: ");
			String apw=sc.next();
			loop2=d.adminLogin(a_id, apw);
			
			while(loop2) {
				System.out.println("Enter the option: \n1) View by doctor id \n2) Remove appointment \n3) Modify appointment \n4) Exit");
				System.out.print("Enter your choice: ");
				int c2=sc.nextInt();
				System.out.println();
				switch(c2) {
					case 1:
						System.out.println("***View by doctor ID..");
						System.out.println("Enter the doctor's id:");
						int did=sc.nextInt();
						d.adminViewDoctor(did);
						break;
					case 2:
						System.out.println("***Remove appointment..");
						PreparedStatement s1=con.prepareStatement("Select * from AppointmentTable order by aDate, aTime");
			    		ResultSet r1=s1.executeQuery();
			    		System.out.println("***List of all the Appointments..");
			    		System.out.println("+--------------+----------+---------+----------+---------+");
			    		System.out.println("|Appointment Id|Patient Id|Doctor Id|   Date   |   Time  |");
			    		System.out.println("+--------------+----------+---------+----------+---------+");
			    		while(r1.next()) {
			    			System.out.println("|"+r1.getInt("aId")+"             |"+r1.getInt("PatientId")+ "       |"+r1.getInt("DoctorId")+ "        |"+r1.getDate("aDate")+"|"+r1.getTime("aTime")+" |");
			    		}
						System.out.println("+--------------+----------+---------+----------+---------+\n");
				    	
						System.out.print("Enter the appointment id: ");
						int ad=sc.nextInt();
						System.out.println();
						d.removeAppointment(ad);
						break;
					case 3:
						System.out.println("***Modify appointment..");
			    		 s1=con.prepareStatement("Select * from AppointmentTable");
			    		 r1=s1.executeQuery();
			    		System.out.println("***List of all the Appointments..");
			    		System.out.println("+--------------+----------+---------+----------+---------+");
			    		System.out.println("|Appointment Id|Patient Id|Doctor Id|   Date   |   Time  |");
			    		System.out.println("+--------------+----------+---------+----------+---------+");
			    		while(r1.next()) {
			    			System.out.println("|"+r1.getInt("aId")+"             |"+r1.getInt("PatientId")+ "       |"+r1.getInt("DoctorId")+ "        |"+r1.getDate("aDate")+"|"+r1.getTime("aTime")+" |");
			    		}
						System.out.println("+--------------+----------+---------+----------+---------+\n");
						System.out.print("Enter the appointment id: ");
						int aad=sc.nextInt();
						System.out.println();
						s1=con.prepareStatement("Select * from AppointmentTable where aID=?");
						s1.setInt(1, aad);
			    		r1=s1.executeQuery();
			    		if(!r1.next()) { System.out.println("No such appointments.. Abort abort!!!\n"); break; }
			    		new AvailabilityDates().listAllAvailableDates();
						System.out.print("Enter the available Doctor's id: ");
						int add=sc.nextInt();
						System.out.println();
						s1=con.prepareStatement("Select * from AvailabilityTable where DoctorId=?");
						s1.setInt(1, add);
			    		r1=s1.executeQuery();
			    		if(!r1.next()) { System.out.println("No such Doctor available.. Abort abort!!!\n"); break; }
						d.modifyAppointment(aad, add);
						break;
					case 4: 
						loop2=false;
						break;
				}
			}
			break;
		case 2:
			loop=false;
			System.out.println("--> Successfully logged out. <--\n");
			break;
			}
		}
    }
	public static void main(String[] args) throws Exception {

	}
}