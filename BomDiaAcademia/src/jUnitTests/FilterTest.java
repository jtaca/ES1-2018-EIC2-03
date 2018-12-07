package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.files.ReadAndWriteFile;
import BDA.other.Filter;
import BDA.other.Service;

/**
 * The Class FilterTest.
 */
public class FilterTest {
	
	/** The filter. */
	private static Filter filter = null;
	
	/** The key words filter. */
	private static List<String> key_words_filter;
	
	/** The twitter user filter list. */
	private static List<String> twitterUserFilterList;
	
	/** The facebook filter list. */
	private static List<String> facebookFilterList;
	
	/** The Constant KEY_WORDS_FILTER_FILE_NAME. */
	private static final String KEY_WORDS_FILTER_FILE_NAME = "key_words_filter.dat";
	
	/** The Constant TWITTER_USER_FILTER_FILE_NAME. */
	private static final String TWITTER_USER_FILTER_FILE_NAME = "twitter_user_filter.dat";
	
	/** The Constant FACEBOOK_FILTER_FILE_NAME. */
	private static final String FACEBOOK_FILTER_FILE_NAME = "facebook_filter.dat";
	
	/** The Constant DEFAULT_KEY_WORDS_FILTERS. */
	private static final String[] DEFAULT_KEY_WORDS_FILTERS = {"iscte", "universidade", "reitoria", "ista", "biblioteca", "cominvestigar", "tesouraria"};
	
	/** The Constant DEFAULT_TWITTER_USER_FILTERS. */
	private static final String[] DEFAULT_TWITTER_USER_FILTERS = {"ISCTEIUL", "INDEGISCTE", "IBSLisbon", "namiscte", "ISCTE_JC"};
	
	/** The Constant DEFAULT_FACEBOOK_FILTERS. */
	private static final String[] DEFAULT_FACEBOOK_FILTERS = {"iscte"};

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Sets the up before function.
	 */
	@Before
	public void setUpBeforeFunction() {
		filter.setFilter(Service.EMAIL, new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS)));
		filter.setFilter(Service.TWITTER, new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS)));
		filter.setFilter(Service.FACEBOOK, new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS)));
	}
	
	/**
	 * After class.
	 */
	@AfterClass
	public static void afterClass() {
		ReadAndWriteFile.saveListOfFilters(KEY_WORDS_FILTER_FILE_NAME, key_words_filter);
		ReadAndWriteFile.saveListOfFilters(TWITTER_USER_FILTER_FILE_NAME, twitterUserFilterList);
		ReadAndWriteFile.saveListOfFilters(FACEBOOK_FILTER_FILE_NAME, facebookFilterList);
	}
	

	/**
	 * Test set filter email.
	 */
	@Test
	public void testSetFilterEmail() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.EMAIL, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test set filter twitter.
	 */
	@Test
	public void testSetFilterTwitter() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.TWITTER, expectedFilterList);

		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test set filter facebook.
	 */
	@Test
	public void testSetFilterFacebook() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.FACEBOOK, expectedFilterList);

		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test add filter list email.
	 */
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
	
	/**
	 * Test add filter list twitter.
	 */
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
	
	/**
	 * Test add filter list facebook.
	 */
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
	
	/**
	 * Test add filter single email.
	 */
	@Test
	public void testAddFilterSingleEmail() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.EMAIL, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test add filter single twitter.
	 */
	@Test
	public void testAddFilterSingleTwitter() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.TWITTER, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test add filter single facebook.
	 */
	@Test
	public void testAddFilterSingleFacebook() {
		String filterToAdd = "ola";
		
		filter.addFilter(Service.FACEBOOK, filterToAdd);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS));
		expectedFilterList.add("ola");
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter list email.
	 */
	@Test
	public void testRemoveFilterListEmail() {
		String filterName = "reitoria";
		List<String> filterList = new ArrayList<String>();
		filterList.add(filterName);
		
		filter.removeFilter(Service.EMAIL, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS));
		expectedFilterList.remove(filterName);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter list twitter.
	 */
	@Test
	public void testRemoveFilterListTwitter() {
		String filterName = "namiscte";
		List<String> filterList = new ArrayList<String>();
		filterList.add(filterName);
		
		filter.removeFilter(Service.TWITTER, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS));
		expectedFilterList.remove(filterName);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter list facebook.
	 */
	@Test
	public void testRemoveFilterListFacebook() {
		String filterName = "iscte";
		List<String> filterList = new ArrayList<String>();
		filterList.add(filterName);
		
		filter.removeFilter(Service.FACEBOOK, filterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS));
		expectedFilterList.remove(filterName);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter single email.
	 */
	@Test
	public void testRemoveFilterSingleEmail() {
		String filterToRemove = "reitoria";
		
		filter.removeFilter(Service.EMAIL, filterToRemove);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_KEY_WORDS_FILTERS));
		expectedFilterList.remove(filterToRemove);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter single twitter.
	 */
	@Test
	public void testRemoveFilterSingleTwitter() {
		String filterToRemove = "namiscte";
		
		filter.removeFilter(Service.TWITTER, filterToRemove);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_TWITTER_USER_FILTERS));
		expectedFilterList.remove(filterToRemove);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test remove filter single facebook.
	 */
	@Test
	public void testRemoveFilterSingleFacebook() {
		String filterToRemove = "iscte";
		
		filter.removeFilter(Service.FACEBOOK, filterToRemove);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		List<String> expectedFilterList = new ArrayList<String>(Arrays.asList(DEFAULT_FACEBOOK_FILTERS));
		expectedFilterList.remove(filterToRemove);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test get filter list email.
	 */
	@Test
	public void testGetFilterListEmail() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.EMAIL, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.EMAIL);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test get filter list twitter.
	 */
	@Test
	public void testGetFilterListTwitter() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.TWITTER, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.TWITTER);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test get filter list facebook.
	 */
	@Test
	public void testGetFilterListFacebook() {
		List<String> expectedFilterList = new ArrayList<String>();
		expectedFilterList.add("ola");
		filter.setFilter(Service.FACEBOOK, expectedFilterList);
		
		List<String> actualFilterList = filter.getFilterList(Service.FACEBOOK);
		
		assertEquals(expectedFilterList, actualFilterList);
	}
	
	/**
	 * Test positive verify if string contains any filter.
	 */
	@Test
	public void testPositiveVerifyIfStringContainsAnyFilter() {
		String testingString = "biblioteca@iscte-iul.pt";
		List<String> filterList = filter.getFilterList(Service.EMAIL);
		
		boolean result = Filter.verifyIfStringContainsAnyFilter(testingString, filterList);
		
		assertTrue(result);
	}
	
	/**
	 * Test negative verify if string contains any filter.
	 */
	@Test
	public void testNegativeVerifyIfStringContainsAnyFilter() {
		String testingString = "lala@gmail.com";
		List<String> filterList = filter.getFilterList(Service.EMAIL);
		
		boolean result = Filter.verifyIfStringContainsAnyFilter(testingString, filterList);
		
		assertFalse(result);
	}
	
	/**
	 * Test define date interval from current date greater than 1.
	 */
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
	
	/**
	 * Test define date interval from current date less than 1.
	 */
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
	
	/**
	 * Test verify if consider date current date.
	 */
	@Test
	public void testVerifyIfConsiderDateCurrentDate() {
		Date date = new Date();
		boolean result = filter.verifyIfConsiderDate(date);
		
		assertTrue(result);
	}
	
	/**
	 * Test verify if consider date limit.
	 */
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
	
	/**
	 * Test verify if consider date failed.
	 */
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
