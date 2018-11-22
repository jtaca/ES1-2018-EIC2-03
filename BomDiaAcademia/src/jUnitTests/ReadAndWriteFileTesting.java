package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import files.ReadAndWriteFile;

public class ReadAndWriteFileTesting {
	

	private List<String> keyWordsFilterList = null;
	private static final String KEY_WORDS_FILTER_FILE_NAME = "key_words_filter.dat";
	private static final String[] DEFAULT_KEY_WORDS_FILTERS = {"iscte", "universidade", "reitoria", "ista", "biblioteca", "cominvestigar", "tesouraria"};
	

	@Test
	public void testReadAndWriteFile() {
		List<String> key_words_filter = ReadAndWriteFile.loadListOfFilters(KEY_WORDS_FILTER_FILE_NAME);
		
		
		
		
		
		
		
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testSaveListOfInformationEntry() {
		
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



}
