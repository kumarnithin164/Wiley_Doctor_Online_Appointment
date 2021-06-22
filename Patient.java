package Wiley_DoctorAppointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Patient extends AvailabilityDates{
	static Connection con;
	static Statement st;
	static String query;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);
	static String emmail;
	static int idd;
	static ArrayList<Integer> aplist= new ArrayList<Integer>();
	public Patient() throws Exception {
		String url = "jdbc:mysql://localhost:3306/AvailabilitySchema";
		String uname = "root";
		String pass = "conormcgregor";
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, uname, pass);
		st = con.createStatement();
	}
	
	public static void patientRegister() throws Exception {

		ArrayList<String> patient= new ArrayList<String>();
		System.out.println("\n*------------------------------New Patient Registration------------------------------*");
		System.out.println("Enter PatientName:");//
		String pName=sc.next();
		patient.add(pName);
		System.out.println();

		System.out.println("Enter Patient Gender:");
		String pGender=sc.next();
		patient.add(pGender);
		System.out.println();
		
		System.out.println("Enter Patient Age:");
		String pAge=sc.next();
		patient.add(pAge);
		System.out.println();
		
		System.out.println("Enter Patient Mobile Number:");
		String pMobile=sc.next();
		patient.add(pMobile);
		System.out.println();
		
		System.out.println("Enter Patient email ID:");
		String pEmail=sc.next();
		patient.add(pEmail);
		System.out.println();
		
		System.out.println("Enter Password:");
		String pPassword=sc.next();
		patient.add(pPassword);
		System.out.println();
		PreparedStatement ps = con.prepareStatement("select * from PatientTable where PatientEmail = ?");
		ps.setString(1, patient.get(4));
		rs = ps.executeQuery();
		if(rs.next()) 
		{
			System.out.println("!!! The patient record already exists.. Please Login !!!");
			patientLogin();
		}
		else {
			query = "INSERT INTO PatientTable(PatientName, PatientGender, PatientAge, PatientMobile, PatientEmail, PatientPassword) VALUES('"+patient.get(0)+"','"+patient.get(1)+"',"+Integer.parseInt(patient.get(2))+","+patient.get(3)+",'"+patient.get(4)+"','"+ patient.get(5)+"')" ;
			int success = st.executeUpdate(query);
			if(success > 0) System.out.println("--> Patient account has been Successfully created. <--");
		}
		
	}
	
		public static void patientLogin() throws Exception{
			System.out.println("\n*------------------------------Patient Login------------------------------*");
			System.out.println("Enter Email_ID:");	
			String pEmail=sc.next();
			emmail=pEmail;
			System.out.println();
	
			System.out.println("Enter Password:");
			String pPassword=sc.next();
			System.out.println();

			query = "select * from PatientTable where PatientEmail = '" + emmail + "'";
			rs = st.executeQuery(query);
			if(rs.next()) 
			{
				if(rs.getString("PatientPassword").equals(pPassword)) {
					System.out.println("Patient login is successful..\n\n*** Welcome to the Online Appointment "+ rs.getString("PatientName") + " !!");
					idd=rs.getInt("PatientID");
				}
				
				else {
					System.out.println("!!! Password incorrect.. Try again. !!!");
					patientLogin();
				}
			}
			else {
				System.out.println("!!! Invalid Email_ID.. Try again. !!!");
				patientLogin();
			}
			patientOptions();
		}
	
	public static void patientOptions() throws Exception{
		System.out.println("Enter the option:");
		System.out.println("1) View my appointments \n2) Add an appointment\n3) Remove an appointment\n4) Exit");
		int op = sc.nextInt();
		switch(op) {
		case 1:
			try
			{
				query = "Select * from AppointmentTable where PatientId = " + idd + ";";
	    		rs=st.executeQuery(query);
	    		System.out.println("***List of all my Appointments..");
	    		System.out.println("+--------------+----------+---------+----------+---------+");
	    		System.out.println("|Appointment Id|Patient Id|Doctor Id|   Date   |   Time  |");
	    		System.out.println("+--------------+----------+---------+----------+---------+");
	    		while(rs.next()) {
	    			aplist.add(rs.getInt("aID"));
	    			System.out.println("|"+rs.getInt("aId")+"             |"+rs.getInt("PatientId")+ "       |"+rs.getInt("DoctorId")+ "        |"+rs.getDate("aDate")+"|"+rs.getTime("aTime")+" |");
	    		}
				System.out.println("+--------------+----------+---------+----------+---------+\n");
	        }
			catch(Exception e) {
				System.out.println(e);
//				System.out.println("!!! Invalid Patient_ID.. Try again. !!!");				
			}
			patientOptions();
			break;
			
		case 2:
			new AvailabilityDates().listAllAvailableDates();
			query = "Select count(*) as counter from AppointmentTable";
    		rs=st.executeQuery(query);
    		rs.next();
//    		int aId = rs.getInt("counter") + 1;
//    		System.out.println("Enter your Patient Id:");
    		int ppid = idd;
    		System.out.println("Enter the Doctor Id:");
    		int did = sc.nextInt();
    		System.out.println("Enter your Appointment date(yyyy-mm-dd):");
    		String ad = sc.next();
    		System.out.println("Enter your Appointment time(hh:mm:ss):");
    		String at = sc.next();
    		query = "INSERT INTO AppointmentTable(PatientId, DoctorId, aDate, aTime) VALUES("+ppid+","+did+",'"+ad+"','"+at+"')" ;
    		try {
    			int success = st.executeUpdate(query);
    			if(success > 0) System.out.println("--> Your Appointment slot has been Successfully created. <--");
    		}
    		catch(Exception e){
    			System.out.println(e);
    			patientLogin();
    		}
    		patientOptions();
    		break;
    	
		case 3:
			try
			{
				query = "Select * from AppointmentTable where PatientId = " + idd + ";";
	    		rs=st.executeQuery(query);
	    		System.out.println("***List of all my Appointments..");
	    		System.out.println("+--------------+----------+---------+----------+---------+");
	    		System.out.println("|Appointment Id|Patient Id|Doctor Id|   Date   |   Time  |");
	    		System.out.println("+--------------+----------+---------+----------+---------+");
	    		while(rs.next()) {
	    			aplist.add(rs.getInt("aID"));
	    			System.out.println("|"+rs.getInt("aId")+"             |"+rs.getInt("PatientId")+ "       |"+rs.getInt("DoctorId")+ "        |"+rs.getDate("aDate")+"|"+rs.getTime("aTime")+" |");
	    		}
				System.out.println("+--------------+----------+---------+----------+---------+\n");
	    		System.out.println();
				System.out.println("***Enter appointment number to be deleted..");
				int todel=sc.nextInt();
				if (aplist.contains(todel)) {
					String query1 = "call delete_appointment("+idd+","+todel+")";;
					st.execute(query1);
					System.out.println("Appointment "+todel+ " removed!");
				}
				else {
					System.out.println("Not possible!");
				}

				
	        }
			catch(Exception e) {
				System.out.println(e);
//				System.out.println("!!! Invalid Patient_ID.. Try again. !!!");				
			}
			
			patientOptions();
			break;
		
		case 4:
			System.out.println("--> Successfully logged out.. <--");
			return;
		}
	}
		
	public void patientStart() throws Exception {
		System.out.println("---------------Patient login/ Registration------------------");
		System.out.println(" 1) New user registration");
		System.out.println(" 2) Existing user/login\n");
		System.out.println("Enter Your choice:");
	
		int n=sc.nextInt();
		switch(n){
			case 1:
				patientRegister();
				break;
				
			case 2:
				patientLogin();
				break;
	
		}
	}
	public static void main(String[] args) throws Exception{
	
	}
}