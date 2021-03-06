package databaseInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class DatabaseInterface {

	private String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/kaa408";

	public DatabaseInterface() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}
		System.out.println("PostgreSQL driver registered.");
		Connection conn = null;
		String username = "kaa408";
		String pass = "uudespav";
		try{
			conn = DriverManager.getConnection(dbName, username, pass);
			System.out.println("Welcome to the interface! type EXIT when you have finished using the program.");
			System.out.println("To register a new student type 'new student'\n"
					+ "To add a new tutor for a student type 'new tutor'\n"
					+ "To produce a report for a student type 'student report'\n"
					+ "To produce a report for a lecturer type 'lecturer report'");
			Scanner input = new Scanner(System.in);
			String response = input.nextLine();
			
			//main menu
			while (response != "EXIT"){
				switch(response){
				case "new student": registerStudent(conn);
									break;
				case "new tutor":	registerTutor(conn);
									break;
				case "student report": studentReport(conn);
									break;
				case "lecturer report": lecturerReport(conn);
									break;
				}
				System.out.println("To register a new student type 'new student'\n"
						+ "To add a new tutor for a student type 'new tutor'\n"
						+ "To produce a report for a student type 'student report'\n"
						+ "To produce a report for a lecturer type 'lecturer report'");
				response = input.nextLine();
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			System.out.println("Database accessed!");
		} else {
			System.out.println("Failed to make connection");
		}
		
	}
	
	private void studentReport(Connection conn) throws SQLException {
		PreparedStatement getStudent = conn.prepareStatement("SELECT "
				+ "t.titleString, s.foreName, s.familyName, s.dateOfBirth, s.studentID, sr.yearOfStudy, "
				+ "rt.description, sc.eMailAddress, sc.postalAddress, nokc.foreName, nokc.eMailAddress, "
				+ "nokc.postalAddress"
				+ "  FROM Student s, StudentRegistration sr, StudentContact  sc, NextOfKinContact  nokc, "
				+ "Titles t, registrationType  rt  WHERE s.studentID=? and s.titleID =t.titleID and s.studentID =sr.studentID "
				+ "and s.studentID = sc.studentID and s.studentID = nokc.studentID and sr.registrationTypeID = rt.registrationTypeID;");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the student ID number of the student you wish to collect a report for.");
		int student = scan.nextInt();
		getStudent.setInt(1, student);
		ResultSet rs = getStudent.executeQuery();
		 ResultSetMetaData rsmd = rs.getMetaData();
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            String columnValue = rs.getString(i);
		            System.out.println(rsmd.getColumnName(i) +": " + columnValue);
		        }
		    }
		PreparedStatement getStudentTutor = conn.prepareStatement("SELECT Lecturer.foreName, Lecturer.lecturerID FROM Student, Tutor, Lecturer "
				+ "WHERE Tutor.studentID = ? and Tutor.studentID = Student.studentID and Tutor.lecturerID=Lecturer.lecturerID ");
		getStudentTutor.setInt(1, student);
		ResultSet rs2 = getStudentTutor.executeQuery();
		 ResultSetMetaData rsmd2 = rs2.getMetaData();
		    int columnsNumber2 = rsmd2.getColumnCount();
		    while (rs2.next()) {
		        for (int i = 1; i <= columnsNumber2; i++) {
		            String columnValue2 = rs2.getString(i);
		            System.out.println(rsmd2.getColumnName(i) +": " + columnValue2);
		        }
		    }
		
	}

	private void lecturerReport(Connection conn) throws SQLException {
		PreparedStatement getLecturerTutees = conn.prepareStatement("SELECT t.titleString, s.foreName, s.familyName, s.studentID, rt.description, "
				+ "s.dateOfBirth, sc.eMailAddress, sc.postalAddress"
				+ "FROM Student as s, RegistrationType rt, StudentContact sc, Lecturer l, Titles t, Tutor tu, StudentRegistration sr"
				+ "WHERE lecturerID=? and s.studentID = tu.studentID and l.lecturerID=tu.lecturerID and s.studentID = sc.studentID and"
				+ "sr.registrationTypeID = rt.registrationTypeID and sr.studentID = s.studentID and s.titleID = t.titleID"
				+ "ORDER BY sr.yearOfStudy;");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the lecturer ID number of the lecturer you wish to collect a report for.");
		int lecturer = scan.nextInt();
		getLecturerTutees.setInt(1, lecturer);
		ResultSet rs = getLecturerTutees.executeQuery();
		 ResultSetMetaData rsmd = rs.getMetaData();
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            String columnValue = rs.getString(i);
		            System.out.println(rsmd.getColumnName(i) +": " + columnValue);
		        }
		    }
	}

	private void registerTutor(Connection conn) throws SQLException{
		PreparedStatement updateTutor = conn.prepareStatement("INSERT INTO Tutor "
				+ "VALUES (?, ?);");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the ID number of the student you wish to add another tutor for.");
		int studentID = scan.nextInt();
		updateTutor.setInt(1, studentID);
		System.out.println("Please enter the ID number of the lecturer you wish to make that student's tutor.");
		int tutorID = scan.nextInt();
		updateTutor.setInt(2, tutorID);
		while(true){
			try{
				updateTutor.executeUpdate();
				break;
			}catch(SQLException e){
				System.out.println("Please re-enter the studentID and the lecturerID "
						+ "ensuring that both the student ID and the lecture ID"
						+ " already exist in the database.");
				System.out.println("Please enter the ID number of the student you wish to add another tutor for.");
				studentID = scan.nextInt();
				System.out.println("Please enter the ID number of the lecturer you wish to make that student's tutor.");
				tutorID= scan.nextInt();
			}
		
		}
		System.out.println("Successfully added tutor to student.");
		
	}

	private void registerStudent(Connection conn) throws SQLException, ParseException {
		PreparedStatement newStudent = conn.prepareStatement("INSERT INTO Student "
				+ "VALUES (?, ?, ?, ?, ?);");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the new student's ID number");
		int studentID = scan.nextInt();
		
		Statement stmt = conn.createStatement();
		do{
			try{
				newStudent.setInt(1, studentID);
				break;
			}catch(SQLException e){
				if(e.getErrorCode() == 23505){
					System.out.println("Please re-enter the ID number of the student"
							+ " ensuring that that student ID number does not exist already.");
					studentID = scan.nextInt();
				}
			}
		}while(true);
		
		System.out.println("Please enter the new student's title ID, where 1 = Mr, 2 = Mrs, 3 = Miss, 4 = Dr and 5 = Professor");
		int titleID = scan.nextInt();
		newStudent.setInt(2, titleID);
		System.out.println("Please enter the new student's forename");
		String forename = scan.next();
		newStudent.setString(3, forename);
		System.out.println("Please enter the new student's family name");
		String familyName = scan.next();
		newStudent.setString(4, familyName);
		System.out.println("Please enter the new student's Date of Birth in the form day-month-year e.g. 08-11-1993");
		String dateOfBirth = scan.next();
		
		while (true){
			try{
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
				Date date = format.parse(dateOfBirth);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				newStudent.setDate(5, sqlDate);
				break;
			}catch (Exception e) {
				System.out.println("Please enter a valid date following the format day-month-year between 1900 and 2000");
				dateOfBirth = scan.next();
			}
			
		}
		
		while(true){
			try{
				newStudent.executeUpdate();
				break;
			}catch(SQLException e){
				if(e.getSQLState().equals("23505")){ // unique violation
					System.out.println("Please re-enter the ID number of the student"
							+ " ensuring that that student ID number does not exist already.");
					studentID = scan.nextInt();
					newStudent.setInt(1, studentID);
				}
			}
		
		}
		System.out.println("Successfully updated the database with new student's details");
	}

}
