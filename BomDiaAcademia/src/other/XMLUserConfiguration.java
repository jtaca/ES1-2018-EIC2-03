package other;

import java.io.IOException;

public class XMLUserConfiguration {
	
	private boolean saveInformation = false;
	private Service service; // 0 = email, 1 = facebook, 2 = twitter;
	private String username = "";
	private String password = "";
	
	public XMLUserConfiguration(boolean saveInformation, Service service, String username, String password) throws Exception {
		if (service == null) {
			throw new Exception("Please insert a valid Service.");
		}
		this.saveInformation = saveInformation;
		this.service = service;
		this.username = username;
		this.password = password;
	}
	
	public XMLUserConfiguration(boolean saveInformation, int service_number, String username, String password) throws Exception {
		this(saveInformation, Service.values()[service_number], username, password);
	}
	
	public boolean isInformationSaved() {
		return saveInformation;
	}
	
	private int informationSavedAsInt() {
		if(saveInformation) {
			return 1; // true
		} else {
			return 0; // false
		}
	}
	
	public int getTypeOfServiceNumber() {
		return service.getTypeOfServiceNumber();
	}
	
	public String getTypeOfServiceInString() {
		return service.stringFormat();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return "Save Information: " + informationSavedAsInt() + System.getProperty("line.separator") 
		+ "Type of Service: " + getTypeOfServiceInString() + System.getProperty("line.separator")
		+ "Username: " + getUsername() + System.getProperty("line.separator")
		+ "Password: " + getPassword() + System.getProperty("line.separator");
	}

}
