package main;

import java.util.Scanner;

import population.PopulateDatabase;
import database.DatabaseCreation;
import databaseInterface.DatabaseInterface;

public class Setup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
		new DatabaseCreation();
		new PopulateDatabase();
		Scanner input= new Scanner(System.in);
		System.out.println("Enter UI to begin querying the database. To end the program type END");
		String response = input.next();
		while( response != "EXIT"){
			if (response.equals("UI")){
				new DatabaseInterface();
			}
			response = input.next();
		}
		

	}

}
