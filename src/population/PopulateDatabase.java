package population;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class PopulateDatabase {
	
	private String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/kaa408";
	
	public PopulateDatabase() {
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
			
			Statement stmt = conn.createStatement();
			
			populateTitles(conn);
			populateStudents(conn);
			populateLecturers(conn);
			populateTutor(conn);
			populateRegistration(conn);
			populateStudentContact(conn);
			populateNextOfKin(conn);
			populateLecturerContact(conn);
			populateRegistrationType(conn);
			
						
			ResultSet rs =
					stmt.executeQuery(
					"SELECT * "
					+ "FROM Student");
			
			while (rs.next())
			{ int sid = rs.getInt(1);
			sid=rs.getInt("studentID");
			System.out.println(sid);
			}			
			
			
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
	

	private void populateRegistrationType(Connection conn) throws SQLException {
		String populateRegistrationType= "INSERT INTO RegistrationType "
				+ "VALUES(1,'normal');"
				+ "INSERT INTO RegistrationType "
				+ "VALUES(2, 'repeat');"
				+ "INSERT INTO RegistrationType "
				+ "VALUES(3, 'external');";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateRegistrationType);				
	}


	private void populateLecturerContact(Connection conn) throws SQLException {
		String populateLecturerContact= "INSERT INTO LecturerContact "
				+ "VALUES(0, 39, 'lec1@bham.ac.uk');"
				+ "INSERT INTO LecturerContact "
				+ "VALUES(1, 101, 'lec2@hotmail.co.uk');"
				+ "INSERT INTO LecturerContact "
				+ "VALUES(2, 123, 'lec3@gmail.com');"
				+ "INSERT INTO LecturerContact "
				+ "VALUES(3, 384, 'lec4@outlook.co.uk');"
				+ "INSERT INTO LecturerContact "
				+ "VALUES(4,298, 'lec5@hotmail.co.uk');";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateLecturerContact);				
	}


	private void populateNextOfKin(Connection conn) throws SQLException {
		String populateNextOfKin= "INSERT INTO NextOfKinContact "
				+ "VALUES(1, 'Joanne', 'nok1@hotmail.co.uk', ' 23 blah nok, Selly Oak, Birmingham, B35 7HU');"
				+ "INSERT INTO NextOfKinContact "
				+ "VALUES(2, 'Jennifer Al', 'blah@nok2.com', '192 nok avenue, Selly Oak, Birmingham, B12 9OD');"
				+ "INSERT INTO NextOfKinContact "
				+ "VALUES(3, 'Tim', 'nok3@outlook.co.uk', '23 fourth street, Nok Oak, Birmingham, B19 0SS');"
				+ "INSERT INTO NextOfKinContact "
				+ "VALUES(4, 'bar@hnoktmail.co.uk', '12345 six street, Selly Oak, nokirmingham, B69 96Y');"
				+ "INSERT INTO NextOfKinContact "
				+ "VALUES(5, 'nokc@yahoo.co.uk', '29 aston nok boulevard, Selly Oak, Birmingham, B39 UMW');";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateNextOfKin);		
	}


	private void populateStudentContact(Connection conn) throws SQLException {
		String populateStudentContact= "INSERT INTO StudentContact "
				+ "VALUES(1, 'blah@hotmail.co.uk', '19 blah street, Selly Oak, Birmingham, B35 7HU');"
				+ "INSERT INTO StudentContact "
				+ "VALUES(2, 'blah@gmail.com', '192 find avenue, Selly Oak, Birmingham, B12 9OD');"
				+ "INSERT INTO StudentContact "
				+ "VALUES(3, 'foo@outlook.co.uk', '23 fourth street, Selly Oak, Birmingham, B19 0SS');"
				+ "INSERT INTO StudentContact "
				+ "VALUES(4, 'bar@hotmail.co.uk', '12345 six street, Selly Oak, Birmingham, B69 96Y');"
				+ "INSERT INTO StudentContact "
				+ "VALUES(5, 'sheep@yahoo.co.uk', '29 aston webb boulevard, Selly Oak, Birmingham, B39 UMW');";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateStudentContact);			
	}


	private void populateRegistration(Connection conn) throws SQLException {
		String populateStudentRegistration = "INSERT INTO StudentRegistration "
				+ "VALUES(1, 2, 1);"
				+ "INSERT INTO StudentRegistration "
				+ "VALUES(2, 1, 2);"
				+ "INSERT INTO StudentRegistration "
				+ "VALUES(3, 2, 3);"
				+ "INSERT INTO StudentRegistration "
				+ "VALUES(4, 3, 4);"
				+ "INSERT INTO StudentRegistration "
				+ "VALUES(5, 4, 5);";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateStudentRegistration);		
	}


	private void populateTitles(Connection conn) throws SQLException {
		String populateTitlesTable = "INSERT INTO Titles "
				+ "VALUES(1, 'Mr');"
				+ "INSERT INTO Titles "
				+ "VALUES(2, 'Mrs');"
				+ "INSERT INTO Titles "
				+ "VALUES(3, 'Miss');"
				+ "INSERT INTO Titles "
				+ "VALUES(4, 'Dr');"
				+ "INSERT INTO Titles "
				+ "VALUES(5, 'Professor');";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateTitlesTable);
		
	}

	private void populateTutor(Connection conn) throws SQLException {
		String populateTutorTable = "";
		
		for (int j = 0; j < 100; j++) {
				populateTutorTable += "INSERT INTO Tutor "
						+ "VALUES("+j+", "+randBetween(0, 4)+");";
			
		}
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateTutorTable);
	}

	private void populateLecturers(Connection conn) throws SQLException {
		Random random = new Random();
		String populateLecturersTable = "";

		for (int i = 0; i < 5; i++) {
			int titleID = random.nextInt(5)+1;
			populateLecturersTable += "INSERT INTO Lecturer "
					+ "VALUES("+i+", "+titleID+", 'Lecturer" + i +"', 'LFName" + i + "');";
		}
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateLecturersTable);
	}

	private void populateStudents(Connection conn) throws SQLException {
		Random random = new Random();
		String populateStudentsTable = "";
		GregorianCalendar gc = new GregorianCalendar();
		
		for (int i = 0; i < 100; i++) {
			int title = random.nextInt(5)+1; 
			int year = randBetween(1950, 2000);
			 int month = randBetween(1,12);
		     gc.set(Calendar.YEAR, year);
		     int dayOfYear = randBetween(1, 28);

		     gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
			 populateStudentsTable += "INSERT INTO Student "
					+ "VALUES("+i+", "+title+", 'firstName" + i +"', 'SFName" + i + "', '"+ gc.get(Calendar.DAY_OF_YEAR) + "-" + month + "-" + gc.get(Calendar.YEAR)+"');";
				
		}
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateStudentsTable);
		
	}
	
	
	
	 public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }

}
