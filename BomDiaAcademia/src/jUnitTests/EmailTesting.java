
package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import email.EmailConnection;
import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import files.ReadAndWriteXMLFile;
import other.XMLUserConfiguration;

import org.junit.BeforeClass;
import org.junit.Test;

public class EmailTesting {
	
	private static EmailConnection email;
	private static XMLUserConfiguration user = null;
	private static List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();

	@BeforeClass
	public static void startInstance() {
		try {
			user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
			email = new EmailConnection(user.getUsername(), user.getPassword());
			//email = new EmailConnection("BomDiaAcademiaES1@Hotmail.com", "BDAcademia1!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testRecievedEmail() {
		List<InformationEntry> recievedEmails = email.receiveMail();
		assertNotNull( recievedEmails);
		
		
	}

	@Test
	public void testSendEmail() {
		email.sendEmail(user.getUsername(), "test", "test");
		List<InformationEntry> recievedEmails = email.receiveMail();
		assertTrue(((EmailEntry)recievedEmails.get(0)).getSubject().contains("test"));
		assertTrue(((EmailEntry)recievedEmails.get(0)).getContent().contains("test"));
		//assertEquals("test",((EmailEntry)recievedEmails.get(0)).getContent());
		System.out.println(((EmailEntry)recievedEmails.get(0)).getWriterName());
		
	}


	@Test
	public void testIncorrectCredetials() {
		
		
	}



}
