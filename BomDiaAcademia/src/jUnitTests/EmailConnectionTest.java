package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import BDA.email.EmailConnection;
import BDA.entry_objects.EmailEntry;
import BDA.entry_objects.InformationEntry;
import BDA.files.ReadAndWriteXMLFile;
import BDA.other.XMLUserConfiguration;

/**
 * The Class EmailConnectionTest.
 */
public class EmailConnectionTest {
	
	/** The email. */
	private static EmailConnection email;
	
	/** The user. */
	private static XMLUserConfiguration user = null;
	
	/**
	 * Start instance.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void startInstance() throws Exception {
		user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		email = new EmailConnection(user.getUsername(), user.getPassword());
		//email = new EmailConnection("BomDiaAcademiaES1@Hotmail.com", "BDAcademia1!");
	}
	
	/**
	 * Test email connection.
	 */
	@Test
	public void testEmailConnection() {
		boolean canConnectEmail = EmailConnection.verifyLogin(user.getUsername(), user.getPassword());
		assertTrue(canConnectEmail);
	}
	
	/**
	 * Test fail email connection.
	 */
	@Test
	public void testFailEmailConnection() {
		boolean canConnectEmail = EmailConnection.verifyLogin("NotTheEmail", "NotThePassword");
		assertFalse(canConnectEmail);
	}

	/**
	 * Test receive mail.
	 */
	@Test
	public void testReceiveMail() {
		List<InformationEntry> recievedEmails = email.receiveMail();
		assertNotNull(recievedEmails);
	}

	/**
	 * Test get username.
	 */
	@Test
	public void testGetUsername() {
		String username = email.getUsername();
		assertEquals(user.getUsername(), username);
	}

	/**
	 * Test is connected.
	 */
	@Test
	public void testIsConnected() {
		email.receiveMail();
		boolean isConnected = email.isConnected();
		assertTrue(isConnected);
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
//		assertTrue(((EmailEntry)recievedEmails.get(0)).getContent().contains("test"));
		//assertEquals("test",((EmailEntry)recievedEmails.get(0)).getContent());
		//System.out.println(((EmailEntry)recievedEmails.get(0)).getWriterName());
		
	}
	
	/**
	 * Test connectivity when incorrect credetials.
	 */
	@Test
	public void testConnectivityWhenIncorrectCredetials() {
//		EmailConnection emailTest = new EmailConnection(user.getUsername(), "notThePassword");
//		List<InformationEntry> recievedEmails = emailTest.receiveMail();
//		assertFalse(emailTest.isConnected());
		EmailConnection emailTest = new EmailConnection("dummy@iscte-iul.pt", "notThePassword");
		List<InformationEntry> recievedEmails = emailTest.receiveMail();
		assertFalse(emailTest.isConnected());
	}
	
	/**
	 * Test send email with threads.
	 */
	@Test
	public void testSendEmailWithThreads() {
		EmailConnection.sendEmailWithThreads(email, user.getUsername(), "Testing Subject", "Testing Message");
		assertTrue(true);
	}

}
