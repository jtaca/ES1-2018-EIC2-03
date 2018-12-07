package BDA.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import BDA.files.ReadAndWriteFile;

/**
 * The Class Filter.
 * @author Alexandre Mendes
 * @version 3.0
 */
public class Filter {
	
	/** The key words filter list. */
	private List<String> keyWordsFilterList = null;
	
	/** The twitter user filter list. */
	private List<String> twitterUserFilterList = null;
	
	/** The facebook filter list. */
	private List<String> facebookFilterList = null;
	
	/** The adding filter. */
	private boolean addingFilter = false;
	
	/** The running. */
	private boolean running = false;
	
	/** The date limit. */
	private Date dateLimit = new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000); // dateLimit as default is the previous day
	
	/** The Constant KEY_WORDS_FILTER_FILE_NAME. */
	private static final String KEY_WORDS_FILTER_FILE_NAME = "key_words_filter.dat";
	
	/** The Constant TWITTER_USER_FILTER_FILE_NAME. */
	private static final String TWITTER_USER_FILTER_FILE_NAME = "twitter_user_filter.dat";
	
	/** The Constant FACEBOOK_FILTER_FILE_NAME. */
	private static final String FACEBOOK_FILTER_FILE_NAME = "facebook_filter.dat";
	
	/** The Constant DEFAULT_KEY_WORDS_FILTERS. */
	private static final String[] DEFAULT_KEY_WORDS_FILTERS = {"iscte", "universidade", "reitoria", "ista", "biblioteca", "cominvestigar", "tesouraria"};
	
	/** The Constant DEFAULT_TWITTER_USER_FILTERS. */
	public static final String[] DEFAULT_TWITTER_USER_FILTERS = {"ISCTEIUL", "INDEGISCTE", "IBSLisbon", "namiscte", "ISCTE_JC"};
	
	/** The Constant DEFAULT_FACEBOOK_FILTERS. */
	private static final String[] DEFAULT_FACEBOOK_FILTERS = {};
//	private static final String[] DEFAULT_FILTERS = {"ista"};
	
	/** The Constant INSTANCE. */
private static final Filter INSTANCE = new Filter();
	
	/**
	 * Instantiates a new filter.
	 */
	private Filter() {
		if(running == false) {
			running = true;
			loadFilterListFromFile();
		}
	}
	
	/**
	 * Gets the single instance of Filter.
	 *
	 * @return single instance of Filter
	 */
	public static Filter getInstance() {
		return INSTANCE;
	}
	
//	public void startFilter() {
//		if(running == false) {
//			running = true;
//			loadFilterListFromFile();
//		}
//	}
	
	/**
 * Load filter list from file.
 */
private synchronized void loadFilterListFromFile() {
		List<String> key_words_filter = ReadAndWriteFile.loadListOfFilters(KEY_WORDS_FILTER_FILE_NAME);
		List<String> twitterUserFilterList = ReadAndWriteFile.loadListOfFilters(TWITTER_USER_FILTER_FILE_NAME);
		List<String> facebookFilterList = ReadAndWriteFile.loadListOfFilters(FACEBOOK_FILTER_FILE_NAME);
		
		if(key_words_filter == null) {
			key_words_filter = createListWithStaticArray(DEFAULT_KEY_WORDS_FILTERS);
			ReadAndWriteFile.saveListOfFilters(KEY_WORDS_FILTER_FILE_NAME, key_words_filter);
		}
		if(twitterUserFilterList == null) {
			twitterUserFilterList = createListWithStaticArray(DEFAULT_TWITTER_USER_FILTERS);
			ReadAndWriteFile.saveListOfFilters(TWITTER_USER_FILTER_FILE_NAME, twitterUserFilterList);
		}
		if(facebookFilterList == null) {
			facebookFilterList = createListWithStaticArray(DEFAULT_FACEBOOK_FILTERS);
			ReadAndWriteFile.saveListOfFilters(FACEBOOK_FILTER_FILE_NAME, facebookFilterList);
		}
		
		this.keyWordsFilterList = key_words_filter;
		this.twitterUserFilterList = twitterUserFilterList;
		this.facebookFilterList = facebookFilterList;
	}
	
	/**
	 * Creates the list with static array.
	 *
	 * @param staticList the static list
	 * @return the list
	 */
	private List<String> createListWithStaticArray(String[] staticList) {
		List<String> list = new ArrayList<String>();
		for(String s : staticList) {
			list.add(s);
		}
		return list;
	}
	
	/**
	 * Save filter list to file.
	 *
	 * @param service the service
	 * @param filterList the filter list
	 */
	private synchronized void saveFilterListToFile(Service service, List<String> filterList) {
		String file_name;
		switch (service) {
		case EMAIL:
			file_name = KEY_WORDS_FILTER_FILE_NAME;
			break;
		case TWITTER:
			file_name = TWITTER_USER_FILTER_FILE_NAME;
			break;
		case FACEBOOK:
			file_name = FACEBOOK_FILTER_FILE_NAME;
			break;
		default:
			file_name = "";
			break;
		}
		ReadAndWriteFile.saveListOfFilters(file_name, filterList);
	}
	
	/**
	 * Sets the filter.
	 *
	 * @param service the service
	 * @param filter the filter
	 */
	public void setFilter(Service service, List<String> filter) {
		switch (service) {
		case EMAIL:
			synchronized (this) {
				keyWordsFilterList = filter;
			}
			break;
			
		case TWITTER:
			synchronized (this) {
				twitterUserFilterList = filter;
			}
			break;
			
		case FACEBOOK:
			synchronized (this) {
				facebookFilterList = filter;
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * Adds the filter.
	 *
	 * @param service the service
	 * @param filter the filter
	 */
	public synchronized void addFilter(Service service, List<String> filter) {
		switch (service) {
		case EMAIL:
			if(keyWordsFilterList != null) {
				keyWordsFilterList.addAll(filter);
				keyWordsFilterList = new ArrayList<String>(new LinkedHashSet<String>(keyWordsFilterList));
				saveFilterListToFile(service, keyWordsFilterList);
			}
			break;
			
		case TWITTER:
			if(twitterUserFilterList != null) {
				twitterUserFilterList.addAll(filter);
				twitterUserFilterList = new ArrayList<String>(new LinkedHashSet<String>(twitterUserFilterList));
				saveFilterListToFile(service, twitterUserFilterList);
			}
			break;
			
		case FACEBOOK:
			if(facebookFilterList != null) {
				facebookFilterList.addAll(filter);
				facebookFilterList = new ArrayList<String>(new LinkedHashSet<String>(facebookFilterList));
				saveFilterListToFile(service, facebookFilterList);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * Adds the filter.
	 *
	 * @param service the service
	 * @param filter the filter
	 */
	public synchronized void addFilter(Service service, String filter) {
		switch (service) {
		case EMAIL:
			if(keyWordsFilterList != null && !keyWordsFilterList.contains(filter)) {
				keyWordsFilterList.add(filter);
				saveFilterListToFile(service, keyWordsFilterList);
			}
			break;
			
		case TWITTER:
			if(twitterUserFilterList != null && !twitterUserFilterList.contains(filter)) {
				twitterUserFilterList.add(filter);
				saveFilterListToFile(service, twitterUserFilterList);
			}
			break;
			
		case FACEBOOK:
			if(facebookFilterList != null && !facebookFilterList.contains(filter)) {
				facebookFilterList.add(filter);
				saveFilterListToFile(service, facebookFilterList);
			}
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * Removes the filter.
	 *
	 * @param service the service
	 * @param filter the filter
	 */
	public synchronized void removeFilter(Service service, List<String> filter) {
		switch (service) {
		case EMAIL:
			if(keyWordsFilterList != null) {
				keyWordsFilterList.removeAll(filter);
				saveFilterListToFile(service, keyWordsFilterList);
			}
			break;
			
		case TWITTER:
			if(twitterUserFilterList != null) {
				twitterUserFilterList.removeAll(filter);
				saveFilterListToFile(service, twitterUserFilterList);
			}
			break;
			
		case FACEBOOK:
			if(facebookFilterList != null) {
				facebookFilterList.removeAll(filter);
				saveFilterListToFile(service, facebookFilterList);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * Removes the filter.
	 *
	 * @param service the service
	 * @param filter the filter
	 */
	public synchronized void removeFilter(Service service, String filter) {
		switch (service) {
		case EMAIL:
			if(keyWordsFilterList != null && keyWordsFilterList.contains(filter)) {
				keyWordsFilterList.remove(filter);
				saveFilterListToFile(service, keyWordsFilterList);
			}
			break;
			
		case TWITTER:
			if(twitterUserFilterList != null && twitterUserFilterList.contains(filter)) {
				twitterUserFilterList.remove(filter);
				saveFilterListToFile(service, twitterUserFilterList);
			}
			break;
			
		case FACEBOOK:
			if(facebookFilterList != null && facebookFilterList.contains(filter)) {
				facebookFilterList.remove(filter);
				saveFilterListToFile(service, facebookFilterList);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * Gets the filter list.
	 *
	 * @param service the service
	 * @return the filter list
	 */
	public List<String> getFilterList(Service service) {
		List<String> filterList;
		switch (service) {
		case EMAIL:
			filterList = this.keyWordsFilterList;
			break;
		case TWITTER:
			filterList = this.twitterUserFilterList;
			break;
		case FACEBOOK:
			filterList = this.facebookFilterList;
			break;
		default:
			filterList = null;
			break;
		}
		
		return filterList;
	}
	
	/**
	 * Verify if string contains any filter.
	 *
	 * @param string the string
	 * @param filters the filters
	 * @return true, if successful
	 */
	public static boolean verifyIfStringContainsAnyFilter(String string, List<String> filters) {
		for(String filter : filters) {
			if(string.toLowerCase().contains(filter)) {
				return true;
			}		
		}
		return false;
	}
	
	/**
	 * Define date interval from current date.
	 *
	 * @param days the days
	 */
	public synchronized void defineDateIntervalFromCurrentDate(int days) {
		if(days <= 0) {
			days = 1;
		}
		Date currentDate = new Date();
		long subtract_value = days * 24 * 60 * 60 * 1000;
		long date = currentDate.getTime();
		long new_date = date - subtract_value;
		
		dateLimit = new Date(new_date);
	}
	
	/**
	 * Verify if consider date.
	 *
	 * @param date the date
	 * @return true, if successful
	 */
	public boolean verifyIfConsiderDate(Date date) {
		Date dateLimit = this.dateLimit;
		return dateLimit.before(date);
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate(){
		return this.dateLimit;
	}

}
