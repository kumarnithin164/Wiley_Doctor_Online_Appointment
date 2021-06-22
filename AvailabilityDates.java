package Wiley_DoctorAppointment;
import java.sql.*;

public class AvailabilityDates {
	static Connection con;
	static Statement st;
	static String query;
	static ResultSet rs;
	
	public AvailabilityDates() throws Exception {
		String url = "jdbc:mysql://localhost:3306/AvailabilitySchema";
		String uname = "root";
		String pass = "conormcgregor";
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, uname, pass);
		st = con.createStatement();
	}
	
	public void listAllAvailableDates() throws Exception{
		query = "select * from AvailabilityTable order by DoctorDate, InTime";
		rs = st.executeQuery(query);
		System.out.println(" ***The list of all the available doctor dates (order by Date):");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+");
		System.out.println(" |    Doctor_ID    |   Doctor_Date   |     In_Time     |     Out_Time    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+");
		while(rs.next())
		System.out.println(" | \t "+rs.getInt("DoctorId") + "\t   |   " + rs.getDate("DoctorDate") + "    |     " + rs.getTime("InTime") + "    |     " + rs.getTime("OutTime") + "    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+\n");
		st.close();
		con.close();
	}
	
	public void listByDate(String d) throws Exception{
		query = "select * from AvailabilityTable where DoctorDate = '" + d + "'" + " order by InTime";
		rs = st.executeQuery(query);
		System.out.println(" ***The list of all the available doctor on " + d + " (order by InTime):");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+");
		System.out.println(" |    Doctor_ID    |   Doctor_Name   |   Doctor_Date   |     In_Time     |     Out_Time    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+");
		while(rs.next())
			System.out.println(" | \t "+rs.getInt("DoctorId") + "\t   |    " + rs.getString("DoctorName") + "     |   " + rs.getDate("DoctorDate") + "    |     " + rs.getTime("InTime") + "    |     " + rs.getTime("OutTime") + "    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+\n");
		st.close();
		con.close();
	}
	
	public void listByDoctor(int i) throws Exception{
		query = "select * from AvailabilityTable where DoctorId = " + i +  " order by DoctorDate, InTime";
		rs = st.executeQuery(query);
		System.out.println(" ***The list of all the dates on which doctor " + i + " is available (order by Date):");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+");
		System.out.println(" |    Doctor_ID    |   Doctor_Name   |   Doctor_Date   |     In_Time     |     Out_Time    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+");
		while(rs.next())
			System.out.println(" | \t "+rs.getInt("DoctorId") + "\t   |    " + rs.getString("DoctorName") + "     |   " + rs.getDate("DoctorDate") + "    |     " + rs.getTime("InTime") + "    |     " + rs.getTime("OutTime") + "    |");
		System.out.println(" +-----------------+-----------------+-----------------+-----------------+-----------------+\n");
		st.close();
		con.close();
	}
	public static void main(String[] args) throws Exception{
		
	}
}

