package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import junit.framework.Assert;
import other.Service;
import other.XMLUserConfiguration;

public class ReadAndWriteXMLFileTest {

	private static List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
	private static XMLUserConfiguration config = null;
	private static XMLUserConfiguration config1 = null;

	
	@BeforeClass
	public static void startInstance() {
		try {
			config = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
			config1 = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
			assertNotNull( config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCreateConfigXMLFile() {
		try {
			ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		
		try {
			ReadAndWriteXMLFile.CreateConfigXMLFile(null);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		
		try {
			ReadAndWriteXMLFile.CreateConfigXMLFile(new ArrayList<XMLUserConfiguration>());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		
		
	}

	@Test
	public void testReadConfigXMLFile() {
		try {
			XMLUserConfiguration user = new XMLUserConfiguration(true, Service.EMAIL, "username", "password");
			user_config_list.add(user);
			ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
			
			assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getPassword(),"password");
			assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getUsername(),"username");
			
			user_config_list.remove(user);
			user_config_list.add(config);
			user_config_list.add(config1);
			ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetTwitterUsers() {
		try {
			assertNotNull(ReadAndWriteXMLFile.getTwitterUsers());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
