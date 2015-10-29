package database;

import java.sql.*;
import javax.sql.*;

public class DatabaseCreation {

	private String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/kaa408";

	public DatabaseCreation()

	{
		String username = "kaa408";
		String pass = "uudespav";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}
		System.out.println("PostgreSQL driver registered.");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbName, username, pass);
			
			String dropTables = "DROP TABLE IF EXISTS Student, "
					+ "Lecturer, StudentRegistration, StudentContact,"
					+ " NextOfKinContact, LecturerContact, Tutor, "
					+ "Titles, RegistrationType CASCADE";
			
			String createTitlesTable = "CREATE TABLE Titles(" +
					"titleID INTEGER PRIMARY KEY," +
					"titleString CHAR(9))";
			
			String createStudentTable = "CREATE TABLE Student ("+
				"studentID INTEGER PRIMARY KEY, " +
				"titleID INTEGER, " +
				"foreName CHAR(20) NOT NULL, " +
				"familyName CHAR(20), "+
				"dateOfBirth DATE, " + 
				"FOREIGN KEY (titleID) REFERENCES Titles (titleID))";
			
			String createLecturerTable = "CREATE TABLE Lecturer ("
					+ "lecturerID INTEGER PRIMARY KEY,"
					+ "titleID INTEGER,"
					+ "foreName CHAR(20) NOT NULL, " 
					+ "familyName CHAR(20), "
					+ "FOREIGN KEY (titleID) REFERENCES Titles (titleID))";
			
			String createStudentRegistrationTable = "CREATE TABLE StudentRegistration ("
					+ "registrationTypeID INTEGER PRIMARY KEY,"
					+ "yearOfStudy DATE NOT NULL,"
					+ "studentID INTEGER, "
					+ "FOREIGN KEY (studentID) REFERENCES Student (studentID))";
			
			String createStudentContactTable = "CREATE TABLE StudentContact ("+
					"studentID INTEGER , " +
					"eMailAddress CHAR(254), " +
					"postalAddress VARCHAR , " + 
					"FOREIGN KEY (studentID) REFERENCES Student (studentID))";
			
			String createNextOfKinTable = "CREATE TABLE NextOfKinContact ("+
					"studentID INTEGER , " +
					"foreName CHAR(20), " +
					"eMailAddress CHAR(254) UNIQUE, " +
					"postalAddress VARCHAR , " + 
					"FOREIGN KEY (studentID) REFERENCES Student (studentID))";
			
			String createLecturerContactTable = "CREATE TABLE LecturerContact ("
					+ "lecturerID INTEGER ,"
					+ "office INTEGER, "
					+ "eMailAddress CHAR(254) UNIQUE, " 
					+ "FOREIGN KEY (lecturerID) REFERENCES Lecturer (lecturerID))";
			
			String createTutorTable = "CREATE TABLE Tutor ("
					+ "studentID INTEGER , " 
					+ "lecturerID INTEGER ,"
					+ "FOREIGN KEY (studentID) REFERENCES Student (studentID), "
					+ "FOREIGN KEY (lecturerID) REFERENCES Lecturer (lecturerID))";
			
			String createRegistrationTypeTable = "CREATE TABLE RegistrationType ("
					+ "registrationTypeID INTEGER PRIMARY KEY ,"
					+ "description VARCHAR )";

						
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(dropTables);
			stmt.executeUpdate(createTitlesTable);
			stmt.executeUpdate(createStudentTable);
			stmt.executeUpdate(createLecturerTable);
			stmt.executeUpdate(createStudentRegistrationTable);
			stmt.executeUpdate(createLecturerContactTable);
			stmt.executeUpdate(createNextOfKinTable);
			stmt.executeUpdate(createTutorTable);
			stmt.executeUpdate(createStudentContactTable);
			stmt.executeUpdate(createRegistrationTypeTable);


			
			ResultSet rs =
					stmt.executeQuery(
					"SELECT * "
					+ "FROM Student");
			
			while (rs.next())
			{ int sid = rs.getInt(1);
			sid=rs.getInt("sid");
			System.out.println(sid);
			}
			//PreparedStatement createStudent = conn.prepareStatement(createStudentTable);
			
			
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			System.out.println("Database accessed!");
		} else {
			System.out.println("Failed to make connection");
		}
		
		
		

	}


}
