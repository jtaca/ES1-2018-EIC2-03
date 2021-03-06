package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import BDA.files.ReadAndWriteXMLFile;
import BDA.other.Service;
import BDA.other.XMLUserConfiguration;

/**
 * The Class ReadAndWriteXMLFileTest.
 */
public class ReadAndWriteXMLFileTest {

	/** The user config list. */
	private static List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();
	
	/** The config. */
	private static XMLUserConfiguration config = null;
	
	/** The config 1. */
	private static XMLUserConfiguration config1 = null;

	
	/**
	 * Start instance.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void startInstance() throws Exception {
		config = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		config1 = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
		assertNotNull( config);
	}
	
	/**
	 * Test create config XML file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCreateConfigXMLFile() throws Exception {
		ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
		
		ReadAndWriteXMLFile.CreateConfigXMLFile(null);
		
		ReadAndWriteXMLFile.CreateConfigXMLFile(new ArrayList<XMLUserConfiguration>());
	}

	/**
	 * Test read config XML file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testReadConfigXMLFile() throws Exception {
		XMLUserConfiguration user = new XMLUserConfiguration(true, Service.EMAIL, "username", "password");
		user_config_list.add(user);
		ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
		
		assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getPassword(),"password");
		assertEquals(ReadAndWriteXMLFile.ReadConfigXMLFile().get(0).getUsername(),"username");
		
		user_config_list.remove(user);
		user_config_list.add(config);
		user_config_list.add(config1);
		ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
	}

	/**
	 * Test get twitter users.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetTwitterUsers() throws Exception {
		assertNotNull(ReadAndWriteXMLFile.getTwitterUsers());
	}

}
