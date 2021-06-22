import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;


public class Doctor {
    String url;
    String uname;
    String pwd;
	static Connection con;
	static Statement st;
	static String query;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);
	static int doccid;
	static String doccname;
	static ArrayList<Integer> aplist= new ArrayList<Integer>();
	
    public Doctor() throws Exception{
		String url = "jdbc:mysql://localhost:3306/AvailabilitySchema";
		String uname = "root";
		String pass = "root";
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
		con=DriverManager.getConnection(url,uname,pass);
		}
		catch(Exception e) {
			System.out.println("DB error");
		}   
    }
    
    public static void doctorRegister() throws Exception {
    	
		System.out.println("\n*------------------------------New doctor Registration------------------------------*");
		System.out.println("Enter doctor Id:");
		int did=sc.nextInt();
		System.out.println("Enter doctorName:");
		String dName=sc.next();
		System.out.println();

		System.out.println("Enter doctor Gender:");
		String dGender=sc.next();
		System.out.println();
		
		System.out.println("Enter doctor Age:");
		int dAge=sc.nextInt();
		sc.nextLine();
		System.out.println();
		
		System.out.println("Enter doctor Mobile Number:");
		String dMobile=sc.next();
		System.out.println();
		
		System.out.println("Enter doctor email ID:");
		String dEmail=sc.next();
		System.out.println();
		
		System.out.println("Enter Password:");
		String dPassword=sc.next();
		System.out.println();
		query = "INSERT INTO DoctorTable(DoctorId, DoctorName, DoctorGender, DoctorAge, DoctorMobile, DoctorEmail, DoctorPassword) VALUES('"+did+"','"+dName+"','"+dGender+"',"+dAge+","+dMobile+",'"+dEmail+"','"+ dPassword+"')" ;
		try {
			PreparedStatement s=con.prepareStatement("INSERT into DoctorTable values(?,?,?,?,?,?,?)");
			s.setInt(1, did);
			s.setString(2, dName);
			s.setString(3,dGender);
			s.setInt(4, dAge);
			s.setString(5,dMobile);
			s.setString(6, dEmail);
			s.setString(7, dPassword);
			int success = s.executeUpdate(query);
			if(success > 0) System.out.println("--> Doctor account has been Successfully created. <--");
		}
		catch(Exception e){
			System.out.println(e);	
		}
		doctorLogin();
	}
	
		public static void doctorLogin() throws Exception{
			System.out.println("\n*------------------------------Doctor Login------------------------------*");
			System.out.println("Enter Doctor_ID:");	
			int dID=sc.nextInt();
			System.out.println();
			doccid=dID;
			System.out.println("Enter Password:");
			String dPassword=sc.next();
			System.out.println();

			query = "select * from doctorTable where DoctorId = '" + dID + "'";
			PreparedStatement sta=con.prepareStatement("select * from DoctorTable where DoctorId =?");
			sta.setInt(1, dID);
			rs = sta.executeQuery();
			if(rs.next()) 
			{
				if(rs.getString("doctorPassword").equals(dPassword)) {
					System.out.println("Doctor login is successful..\n\n*** Welcome to the Online Appointment "+ rs.getString("doctorName") + " !!");
					System.out.println("**Enter your option:\n1) View Appointments  \n2) Set Availability \n3) Cancel Appointment\n4) Exit");
					doccname=rs.getString("doctorName");
					int choice =sc.nextInt();
					select_option(choice);
				}
				else {
					System.out.println("!!! Password incorrect.. Try again. !!!");
					doctorLogin();
				}
			}
			else {
				System.out.println("!!! Invalid Email_ID.. Try again. !!!");
				doctorLogin();
			}

		}

    
    void doctordashboard(){
        try {
        	PreparedStatement s=con.prepareStatement("Select * from AvailabilityTable");
    		ResultSet r=s.executeQuery();
    		System.out.println("\n+---------+-----------+---------+--------+");
    		System.out.println("|Doctor id"+"|"+"Date"+ "       |"+"In Time"+ "  |"+"Out Time|");
    		System.out.println("+---------+-----------+---------+--------+");
    		while(r.next()) {
    			System.out.println("|"+r.getInt(1)+"      |"+r.getDate(2)+ " |"+r.getTime(3)+ " |"+r.getTime(4)+"|");
    			System.out.println("+---------+-----------+---------+--------+\n");
    		}
        }
        catch(Exception e) {
        	System.out.println("SQL error");
        }
    }
    
	private static void select_option(int case_opt) {
		switch(case_opt) {
		case 1:view_appointments();break;
		case 2:set_availibility();break;
		case 3:cancel_appointments();break;
		default: System.out.println("**Successfully logged out..\n"); 
		}
	}
	private static void view_appointments() {
		System.out.println("The appointments that have been assigned to you are:");
//		System.out.println("Enter the doctor Id:");
		int docId = doccid;
		try {
			PreparedStatement s1=con.prepareStatement("Select * from AppointmentTable WHERE DoctorId=?");
			s1.setInt(1, docId);
    		ResultSet r1=s1.executeQuery();
    		System.out.println("\n+--------------+----------+---------+----------+---------+");
    		System.out.println("|Appointment_Id|Doctor_Id|Patient_Id|Date      |Time     |");
    		System.out.println("+--------------+----------+---------+----------+---------+");
    		while(r1.next()) {
    			System.out.println("|"+r1.getInt(1)+"             |"+r1.getInt(4)+ "         |"+r1.getInt(5)+ "      |"+r1.getDate(2)+"|"+r1.getTime(3)+" |");
    			System.out.println("+--------------+----------+---------+----------+---------+\n");
    			
    		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		System.out.println("**Enter your option:\n1) View Appointments  \n2) Set Availability \n3) Cancel Appointment\n4) Exit");
		int op = sc.nextInt();
		select_option(op);
	}
	private static void set_availibility() {
		try {
			new AvailabilityDates().listAllAvailableDates();
			System.out.println("\nEnter the Availability_Date(YYYY-MM-DD) and inTime(HH:MM:SS) and OutTime:");
			int did=doccid;
//			String dname = doccname;
			String date=sc.next();
			String iT=sc.next();
			String oT=sc.next();
			
			PreparedStatement s1=con.prepareStatement("INSERT into AvailabilityTable values(?,?,?,?)");
			s1.setInt(4, did);
//			s1.setString(2, dname);
			s1.setDate(1, java.sql.Date.valueOf(date));
			s1.setTime(2,java.sql.Time.valueOf(iT));
			s1.setTime(3, java.sql.Time.valueOf(oT));
			
    		int r1=s1.executeUpdate();
    		if(r1>0) {
    			System.out.println("--> Successfully added your availability. <--\n");
    		}
    		
		}
		catch(Exception e) {
			System.out.println(e);
		}		

		System.out.println("**Enter your option\n1) View Appointments  \n2) Set Availability \n3) Cancel Appointment\n4) Exit");
		int op = sc.nextInt();
		select_option(op);
	}
	private static void cancel_appointments() {
		//Display appointments/
		int docId = doccid;
		System.out.println("The appointments that have been assigned to you are:");
		try {
			PreparedStatement s1=con.prepareStatement("Select * from AppointmentTable WHERE DoctorId=?");
			s1.setInt(1, docId);
    		ResultSet r1=s1.executeQuery();
    		System.out.println("+--------------+----------+---------+----------+---------+");
    		System.out.println("|Appointment_Id|Patient_Id|Doctor Id|Date      |Time     |");
    		System.out.println("+--------------+----------+---------+----------+---------+");
    		while(r1.next()) {
    			System.out.println("|"+r1.getInt(1)+"             |"+r1.getInt(5)+ "       |"+r1.getInt(4)+ "        |"+r1.getDate(2)+"|"+r1.getTime(3)+" |");
    			System.out.println("+--------------+----------+---------+----------+---------+");
    			aplist.add(r1.getInt(1));
    		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		System.out.println("Enter the appointment Id:");
		int aid=sc.nextInt();
		if (aplist.contains(aid)) {
		try {
		PreparedStatement sta=con.prepareStatement("Delete from AppointmentTable where aid=?");
		sta.setInt(1, aid);
		int i=sta.executeUpdate();
		if(i>0) {
			System.out.println("--> Successfully Deleted. <--");
		}
	
		}
		catch(Exception e) {
			System.out.println(e);
		}}
		else {
			System.out.println("Not Possible!");
		}
		//	Remove from appointments table, admin must alter/reassign	
		
		System.out.println("Enter your option\n1) View Appointments  \n2) Set Availability \n3) Cancel Appointment\n4) Exit");
		int op = sc.nextInt();
		select_option(op);			
	}
	public void docStart() throws Exception {
		System.out.println("*---------------Doctor login/ Registration------------------*");
		System.out.println(" 1) New doctor registration");
		System.out.println(" 2) Existing doctor login\n");
		System.out.println("Enter Your choice:");
		//new Doctor();
		int n=sc.nextInt();
		switch(n){
			case 1:
				doctorRegister();
				break;
				
			case 2:
				doctorLogin();
				break;
		}
	}
	public static void main(String[] args) throws Exception{
	}
}