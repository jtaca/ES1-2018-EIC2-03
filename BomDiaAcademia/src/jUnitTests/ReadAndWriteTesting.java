package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import email.EmailConnection;
import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import files.ReadAndWriteFile;
import files.ReadAndWriteXMLFile;
import other.Service;
import other.XMLUserConfiguration;

public class ReadAndWriteTesting {
	 
	private static List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
	private static XMLUserConfiguration config = null;
	
	@BeforeClass
	public static void startInstance() {
		try {
			config = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
			assertNotNull( config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testXMLWriting() {
		
		try {
			XMLUserConfiguration user = new XMLUserConfiguration(true, Service.EMAIL, "username", "password");
			user_config_list.add(user);
			ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
			assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getPassword(),"password");
			assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getUsername(),"username");
			user_config_list.remove(user);
			user_config_list.add(config);
			ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	@Test
	public void testReadAndWriteFile() {
		List<InformationEntry> information_entry_list = null;
		Date date = new Date();
		information_entry_list = new ArrayList<InformationEntry>();
		information_entry_list.add(new EmailEntry(date, "Person", "Subject", "Content"));
		
		ReadAndWriteFile.saveListOfInformationEntry("emailEntrysTest.dat", information_entry_list);
		ReadAndWriteFile.loadListOfInformationEntry("emailEntrysTest.dat");
		
		assertEquals(ReadAndWriteFile.loadListOfInformationEntry("emailEntrysTest.dat").get(0).getDate(),date);
		
		assertTrue(((EmailEntry)ReadAndWriteFile.loadListOfInformationEntry("emailEntrysTest.dat").get(0)).getContent().contains("Content"));
		assertTrue(((EmailEntry)ReadAndWriteFile.loadListOfInformationEntry("emailEntrysTest.dat").get(0)).getWriterName().contains("Person"));
		assertTrue(((EmailEntry)ReadAndWriteFile.loadListOfInformationEntry("emailEntrysTest.dat").get(0)).getSubject().contains("Subject"));		
		
		System.out.println(ReadAndWriteFile.loadListOfInformationEntry("nonExistingFile.dat"));
		assertEquals( ReadAndWriteFile.loadListOfInformationEntry("nonExistingFile.dat"),new ArrayList<InformationEntry>());
		
		
		
		
	}
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
