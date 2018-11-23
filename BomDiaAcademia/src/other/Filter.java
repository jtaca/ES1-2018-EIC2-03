package other;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import files.ReadAndWriteFile;

public class Filter {
	
	private List<String> keyWordsFilterList = null;
	private List<String> twitterUserFilterList = null;
	private List<String> facebookFilterList = null;
	
	private boolean addingFilter = false;
	private boolean running = false;
	
	private Date dateLimit = new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000); // dateLimit as default is the previous day
	
	private static final String KEY_WORDS_FILTER_FILE_NAME = "key_words_filter.dat";
	private static final String TWITTER_USER_FILTER_FILE_NAME = "twitter_user_filter.dat";
	private static final String FACEBOOK_FILTER_FILE_NAME = "facebook_filter.dat";
	
	private static final String[] DEFAULT_KEY_WORDS_FILTERS = {"iscte", "universidade", "reitoria", "ista", "biblioteca", "cominvestigar", "tesouraria"};
	private static final String[] DEFAULT_TWITTER_USER_FILTERS = {"ISCTEIUL", "INDEGISCTE", "IBSLisbon", "namiscte", "ISCTE_JC"};
	private static final String[] DEFAULT_FACEBOOK_FILTERS = {};
//	private static final String[] DEFAULT_FILTERS = {"ista"};
	
	private static final Filter INSTANCE = new Filter();
	
	private Filter() {
		if(running == false) {
			running = true;
			loadFilterListFromFile();
		}
	}
	
	public static Filter getInstance() {
		return INSTANCE;
	}
	
//	public void startFilter() {
//		if(running == false) {
//			running = true;
//			loadFilterListFromFile();
//		}
//	}
	
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
	
	private List<String> createListWithStaticArray(String[] staticList) {
		List<String> list = new ArrayList<String>();
		for(String s : staticList) {
			list.add(s);
		}
		return list;
	}
	
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
	
	public static boolean verifyIfStringContainsAnyFilter(String string, List<String> filters) {
		for(String filter : filters) {
			if(string.toLowerCase().contains(filter)) {
				return true;
			}		
		}
		return false;
	}
	
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
	
	public boolean verifyIfConsiderDate(Date date) {
		Date dateLimit = this.dateLimit;
		return dateLimit.before(date);
	}
	
	public Date getDate(){
		return this.dateLimit;
	}

}
