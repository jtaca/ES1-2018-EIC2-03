package other;

import java.util.ArrayList;
import java.util.List;

import threads.TaskTable;

public class Filter {
	
	private List<String> filterList = null;
	private boolean addingFilter = false;
	
	private static final Filter INSTANCE = new Filter();
	
	private Filter() {
	}
	
	public static Filter getInstance() {
		return INSTANCE;
	}
	
	public void startFilter() {
		if(filterList == null) {
			filterList = new ArrayList<String>();
			loadFilterListFromFile();
		}
	}
	
	private synchronized void loadFilterListFromFile() {
		
	}
	
	private synchronized void saveFilterListToFile(List<String> filterList) {
		
	}
	
	public void addFilter(List<String> filter) throws Exception {
		if(addingFilter == false) {
			addingFilter = true;
			
			// instructions
			
			addingFilter = false;
		} else {
			throw new Exception("Filter one filter is already being added.");
		}
	}
	
	public void addFilter(String filter) throws Exception {
		if(addingFilter == false) {
			addingFilter = true;
			
			// instructions
			
			addingFilter = false;
		} else {
			throw new Exception("Filter one filter is already being added.");
		}
		
	}
	
	public List<String> getFilterList() {
		return filterList;
	}

}
