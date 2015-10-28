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
			
						
			ResultSet rs =
					stmt.executeQuery(
					"SELECT * "
					+ "FROM Titles");
			
			while (rs.next())
			{ int sid = rs.getInt(1);
			sid=rs.getInt("titleID");
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
					+ "VALUES("+i+", "+title+", 'firstName" + i +"', 'SFName" + i + "', '"+gc.get(Calendar.YEAR) + "-" + month + "-" + gc.get(Calendar.DAY_OF_YEAR) +"');";
				
		}
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(populateStudentsTable);
		
	}
	
	 public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }

}
