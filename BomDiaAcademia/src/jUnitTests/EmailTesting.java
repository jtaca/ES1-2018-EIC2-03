
package jUnitTests;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import email.EmailConnection;
import entry_objects.InformationEntry;
import files.ReadAndWriteXMLFile;
import other.Service;
import other.XMLUserConfiguration;

import org.junit.Test;

public class EmailTesting {

	@Test
	public void test() {
		Scanner sc = new Scanner(System.in);
		String username;
		String password;
		int saveInformationOfUser;
		boolean saveInformationOfUserBool = false;
		XMLUserConfiguration user = null;
		
		List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
		
		try {
			user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(user == null || (user != null && user.isInformationSaved() == false)) {
				System.out.println("Please insert your email address:");
				username = sc.nextLine();
				System.out.println("Please insert the password (for that email):");
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
					user_config_list.add(user);
					ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
				}
				sc.close();
			
			}
			EmailConnection outlook = new EmailConnection(user.getUsername(), user.getPassword());
			List<InformationEntry> recievedEmail = outlook.receiveMail();
			testRecievedEmail(recievedEmail);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void testRecievedEmail(List<InformationEntry> recievedEmail) {
		assertTrue( recievedEmail != null  );
	}

}
