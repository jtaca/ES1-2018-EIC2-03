package jUnitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import email.EmailConnection;
import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import files.ReadAndWriteXMLFile;
import other.XMLUserConfiguration;

public class ReadAndWriteTesting {
	 
	private static List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
	private static XMLUserConfiguration config = null;
	
	@BeforeClass
	public static void startInstance() {
		try {
			config = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testXMLInput() {
		assertNotNull( config);
		
		
	}

//	@Test
//	public void testSendEmail() {
//		email.sendEmail(user.getUsername(), "test", "test");
//		List<InformationEntry> recievedEmails = email.receiveMail();
//		assertTrue(((EmailEntry)recievedEmails.get(0)).getSubject().contains("test"));
//		assertTrue(((EmailEntry)recievedEmails.get(0)).getContent().contains("test"));
//		//assertEquals("test",((EmailEntry)recievedEmails.get(0)).getContent());
//		//System.out.println(((EmailEntry)recievedEmails.get(0)).getWriterName());
//		
//	}
//
//
//	@Test
//	public void testIncorrectCredetials() {
//		EmailConnection emailTest = new EmailConnection(user.getUsername(), "notThePassword");
//		List<InformationEntry> recievedEmails = emailTest.receiveMail();
//		assertFalse(emailTest.isConnected());
//		System.out.println("Is Connected: "+ emailTest.isConnected());
//		emailTest = new EmailConnection("dummy@iscte-iul.pt", "notThePassword");
//		recievedEmails = emailTest.receiveMail();
//		assertFalse(emailTest.isConnected());
//	}

}
