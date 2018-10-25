package other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import email.EmailConnection;
import files.ReadAndWriteXMLFile;
import jUnitTests.EmailTesting;

public class Main {
	
	
	
	public static void main(String[] args) {
		// Starts the GUI where it promps the user for credentials for the email and for the facebook and twitter as optional. (email is required)
		// place a boolean to save the information and start the program without promping that window again.
		// Window would require an log out function then
		// require some method to write on a file the credentials, and if there is no credentials yet, just write new ones...
		Scanner sc = new Scanner(System.in);
		//String fileName = "config";
		String username;
		String password;
		int saveInformationOfUser;
		boolean saveInformationOfUserBool = false;
		XMLUserConfiguration user = null;
		
		List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
		
		try {
			//user = ReadAndWriteFile.readUserXMLFile(fileName);
			user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(user == null || (user != null && user.isInformationSaved() == false)) {
				System.out.println("Please insert your email address:");
				username = sc.nextLine();
				System.out.println("Please insert the password for that email:");
				password = sc.nextLine();
				System.out.println("Would you like to save this information for the application to start without asking this again? (1 for yes, 0 for no)");
				saveInformationOfUser = Integer.parseInt(sc.nextLine());
				System.out.println(saveInformationOfUser);
				if(saveInformationOfUser == 1) {
					saveInformationOfUserBool = true;
					System.out.println(saveInformationOfUserBool);
				}
				user = new XMLUserConfiguration(saveInformationOfUserBool, Service.EMAIL, username, password);
				if(saveInformationOfUserBool) {
					//user_config_list = new ArrayList<XMLUserConfiguration>();
					user_config_list.add(user);
					ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
					//ReadAndWriteFile.writeOnXMLFileAsNewFile(fileName, user); // its not creating the file?
				}
				sc.close();
			
			}
		
			// Ja tenho o user (XMLUserConfiguration) neste ponto
			//System.out.println(user.getUsername());
			//System.out.println(user.getPassword());
			//System.out.println(user.isInformationSaved());
			EmailConnection outlook = new EmailConnection(user.getUsername(), user.getPassword());
			//EmailTesting test = new EmailTesting(outlook);
			//test.jUnitTests();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
