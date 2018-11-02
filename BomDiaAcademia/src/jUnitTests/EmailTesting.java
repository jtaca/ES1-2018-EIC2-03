
package jUnitTests;

import static org.junit.Assert.*;

import java.util.List;


import email.EmailConnection;
import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import files.ReadAndWriteXMLFile;
import other.XMLUserConfiguration;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * The Class EmailTesting.
 */
public class EmailTesting {
	
	/** The email. */
	private static EmailConnection email;
	
	/** The user. */
	private static XMLUserConfiguration user = null;
	
	/** The user configuration list. */

	/**
	 * Start instance.
	 */
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

	/**
	 * Test received email.
	 */
	@Test
	public void testRecievedEmail() {
		List<InformationEntry> recievedEmails = email.receiveMail();
		assertNotNull( recievedEmails);
		
		
	}

	/**
	 * Test send email.
	 */
	@Test
	public void testSendEmail() {
		email.sendEmail(user.getUsername(), "test", "test");
		List<InformationEntry> recievedEmails = email.receiveMail();
		System.out.println(((EmailEntry)recievedEmails.get(0)).getSubject());
		assertTrue(((EmailEntry)recievedEmails.get(0)).getSubject().contains("test"));
		assertTrue(((EmailEntry)recievedEmails.get(0)).getContent().contains("test"));
		//assertEquals("test",((EmailEntry)recievedEmails.get(0)).getContent());
		//System.out.println(((EmailEntry)recievedEmails.get(0)).getWriterName());
		
	}


	/**
	 * Test incorrect credentials.
	 */
	@Test
	public void testIncorrectCredetials() {
		EmailConnection emailTest = new EmailConnection(user.getUsername(), "notThePassword");
		List<InformationEntry> recievedEmails = emailTest.receiveMail();
		assertFalse(emailTest.isConnected());
		emailTest = new EmailConnection("dummy@iscte-iul.pt", "notThePassword");
		recievedEmails = emailTest.receiveMail();
		assertFalse(emailTest.isConnected());
	}



}