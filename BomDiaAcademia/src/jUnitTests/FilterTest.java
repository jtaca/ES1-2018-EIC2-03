package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.files.ReadAndWriteFile;
import BDA.other.Filter;
import BDA.other.Service;

public class FilterTest {
	
	private static Filter filter = null;
	
	private static List<String> key_words_filter;
	private static List<String> twitterUserFilterList;
	private static List<String> facebookFilterList;
	
	private static final String KEY_WORDS_FILTER_FILE_NAME = "key_words_filter.dat";
	private static final String TWITTER_USER_FILTER_FILE_NAME = "twitter_user_filter.dat";
	private static final String FACEBOOK_FILTER_FILE_NAME = "facebook_filter.dat";
	
	private static final String[] DEFAULT_KEY_WORDS_FILTERS = {"iscte", "universidade", "reitoria", "ista", "biblioteca", "cominvestigar", "tesouraria"};
	private static final String[] DEFAULT_TWITTER_USER_FILTERS = {"ISCTEIUL", "INDEGISCTE", "IBSLisbon", "namiscte", "ISCTE_JC"};
	private static final String[] DEFAULT_FACEBOOK_FILTERS = {};

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		key_words_filter = ReadAndWriteFile.loadListOfFilters(KEY_WORDS_FILTER_FILE_NAME);
		twitterUserFilterList = ReadAndWriteFile.loadListOfFilters(TWITTER_USER_FILTER_FILE_NAME);
		facebookFilterList = ReadAndWriteFile.loadListOfFilters(FACEBOOK_FILTER_FILE_NAME);
		
		ReadAndWriteFile.saveListOfFilters(KEY_WORDS_FILTER_FILE_NAME, null);
		ReadAndWriteFile.saveListOfFilters(TWITTER_USER_FILTER_FILE_NAME, null);
		ReadAndWriteFile.saveListOfFilters(FACEBOOK_FILTER_FILE_NAME, null);
		
		filter = Filter.getInstance();
		filter.setFilter(Service.EMAIL, new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS)));
		filter.setFilter(Service.TWITTER, new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS)));
		filter.setFilter(Service.FACEBOOK, new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS)));
		
		
	}
	
	@Before
	public void setUpBeforeFunction() {
		filter.setFilter(Service.EMAIL, new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS)));
		filter.setFilter(Service.TWITTER, new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS)));
		filter.setFilter(Service.FACEBOOK, new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS)));
	}
	
	@AfterClass
	public static void afterClass() {
		ReadAndWriteFile.saveListOfFilters(KEY_WORDS_FILTER_FILE_NAME, key_words_filter);
		ReadAndWriteFile.saveListOfFilters(TWITTER_USER_FILTER_FILE_NAME, twitterUserFilterList);
		ReadAndWriteFile.saveListOfFilters(FACEBOOK_FILTER_FILE_NAME, facebookFilterList);
	}
	

	@Test
	public void testSetFilterEmail() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.EMAIL, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testSetFilterTwitter() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.TWITTER, expectedFilterList);

		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testSetFilterFacebook() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.FACEBOOK, expectedFilterList);

		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterListEmail() {
		List<String> filterList = new ArrayList<String>();
		filterList.add("ola");
		
		filter.addFilter(Service.EMAIL, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterListTwitter() {
		List<String> filterList = new ArrayList<String>();
		filterList.add("ola");
		
		filter.addFilter(Service.TWITTER, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterListFacebook() {
		List<String> filterList = new ArrayList<String>();
		filterList.add("ola");
		
		filter.addFilter(Service.FACEBOOK, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterSingleEmail() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.EMAIL, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterSingleTwitter() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.TWITTER, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testAddFilterSingleFacebook() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.FACEBOOK, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testGetFilterListEmail() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.EMAIL, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testGetFilterListTwitter() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.TWITTER, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testGetFilterListFacebook() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.FACEBOOK, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	@Test
	public void testPositiveVerifyIfStringContainsAnyFilter() {
		String testingString = "biblioteca@iscte-iul.pt";
		List<String> filterList = filter.getFilterList(Service.EMAIL);
		
		boolean result = Filter.verifyIfStringContainsAnyFilter(testingString, filterList);
		
		assertTrue(result);
	}
	
	@Test
	public void testNegativeVerifyIfStringContainsAnyFilter() {
		String testingString = "lala@gmail.com";
		List<String> filterList = filter.getFilterList(Service.EMAIL);
		
		boolean result = Filter.verifyIfStringContainsAnyFilter(testingString, filterList);
		
		assertFalse(result);
	}
	
	@Test
	public void testDefineDateIntervalFromCurrentDateGreaterThan1() {
		int dateInterval = 2;
		Date currentDate = new Date();
		filter.defineDateIntervalFromCurrentDate(dateInterval);
		long subtract_value = dateInterval * 24 * 60 * 60 * 1000;
		long date = currentDate.getTime();
		long new_date = date - subtract_value;
		
		Date expectedDateLimit = new Date(new_date);
		
		Date actualDateLimit = filter.getDate();
		
		assertEquals(expectedDateLimit, actualDateLimit);
	}
	
	@Test
	public void testDefineDateIntervalFromCurrentDateLessThan1() {
		int dateInterval = 0;
		Date currentDate = new Date();
		filter.defineDateIntervalFromCurrentDate(dateInterval);
		long subtract_value = 1 * 24 * 60 * 60 * 1000;
		long date = currentDate.getTime();
		long new_date = date - subtract_value;
		
		Date expectedDateLimit = new Date(new_date);
		
		Date actualDateLimit = filter.getDate();
		
		assertEquals(expectedDateLimit, actualDateLimit);
	}
	
	@Test
	public void testVerifyIfConsiderDateCurrentDate() {
		Date date = new Date();
		boolean result = filter.verifyIfConsiderDate(date);
		
		assertTrue(result);
	}
	
	@Test
	public void testVerifyIfConsiderDateLimit() {
		int dateInterval = 1;
		filter.defineDateIntervalFromCurrentDate(dateInterval);
		Date currentDate = new Date();
		long subtract_value = dateInterval * 24 * 60 * 60 * 1000;
		long date = currentDate.getTime();
		long new_date = date - subtract_value;
		
		Date expectedDateLimit = new Date(new_date);
		
		boolean result = filter.verifyIfConsiderDate(expectedDateLimit);
		
		assertFalse(result);
	}
	
	@Test
	public void testVerifyIfConsiderDateFailed() {
		filter.defineDateIntervalFromCurrentDate(2);
		
		Date currentDate = new Date();
		long subtract_value = 3 * 24 * 60 * 60 * 1000;
		long date = currentDate.getTime();
		long new_date = date - subtract_value;
		
		Date dateToTest = new Date(new_date);
		
		boolean result = filter.verifyIfConsiderDate(dateToTest);
		
		assertFalse(result);
	}
	
	

}
