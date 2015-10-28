package main;

import population.PopulateDatabase;
import database.DatabaseCreation;

public class Setup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
		new DatabaseCreation();
		new PopulateDatabase();
		
	}

}
