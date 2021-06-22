package Wiley_DoctorAppointment;

import java.util.Scanner;

public class DocApp {

	public static void main(String[] args) throws Exception {
		Doctor d = new Doctor();
		Patient p = new Patient();
		Admin a = new Admin();
		while(true) {
			System.out.println("***---------------------Welcome to the Online Doctor Appointment---------------------***");
			System.out.println("**Enter the type of User:");
			System.out.println("1) Admin\n2) Doctor\n3) Patient\n4) Exit");
			Scanner in = new Scanner(System.in);
			System.out.print("\n**Enter your choice: ");
			int user = in.nextInt();
			System.out.println();
			switch(user) {
			case 1:
				a.adminStart();
				break;
			case 2:
				d.docStart();
				break;
			case 3:
				p.patientStart();
				break;
			default:
				System.out.println("******Thanks for using our Online Doctor Appointment service******");
				in.close();
				return;
			}
		}
	}
}
