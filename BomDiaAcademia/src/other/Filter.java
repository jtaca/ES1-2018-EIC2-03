package other;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import files.ReadAndWriteFile;

public class Filter {
	
	private List<String> filterList = null;
	private boolean addingFilter = false;
	private boolean running = false;
	
	private Date dateLimit = new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000); // dateLimit as default is the previous day
	
	private static final String FILTER_FILE_NAME = "filter.dat";
	private static final String[] DEFAULT_FILTERS = {"ISCTEIUL", "INDEGISCTE", "IBSLisbon", "namiscte", "ISCTE_JC"};
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
		List<String> filters = ReadAndWriteFile.loadListOfFilters(FILTER_FILE_NAME);
		if(filters == null) {
			filters = new ArrayList<String>();
			for(String s : DEFAULT_FILTERS) {
				filters.add(s);
			}
			boolean file_saved = ReadAndWriteFile.saveListOfFilters(FILTER_FILE_NAME, filters);
			if(file_saved) {
				System.out.println("File was saved");
			} else {
				System.out.println("File wasnt saved");
			}
		}
		filterList = filters;
	}
	
	private synchronized void saveFilterListToFile(List<String> filterList) {
		boolean file_saved = ReadAndWriteFile.saveListOfFilters(FILTER_FILE_NAME, filterList);
		if(file_saved) {
			System.out.println("File was saved");
		} else {
			System.out.println("File wasnt saved");
		}
	}
	
	public void addFilter(List<String> filter) throws Exception {
		if(addingFilter == false) {
			addingFilter = true;
			
			if(filterList != null) {
				filterList.addAll(filter);
				// lets remove duplicates and keep order
				filterList = new ArrayList<String>(new LinkedHashSet<String>(filterList));
				saveFilterListToFile(filterList);
			}
			
			addingFilter = false;
		} else {
			throw new Exception("Filter one filter is already being added.");
		}
	}
	
	public void addFilter(String filter) throws Exception {
		if(addingFilter == false) {
			addingFilter = true;
			
			if(filterList != null && !filterList.contains(filter)) {
				filterList.add(filter);
				saveFilterListToFile(filterList);
			}
			
			addingFilter = false;
		} else {
			throw new Exception("Filter one filter is already being added.");
		}
		
	}
	
	public List<String> getFilterList() {
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
