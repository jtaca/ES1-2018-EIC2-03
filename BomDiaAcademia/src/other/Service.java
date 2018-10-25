package other;

/**
 * The Enum Service.
 * @author Alexandre Mendes
 * @version 1.0
 */
public enum Service {
	
	/** The email. */
	EMAIL(0), 
 /** The facebook. */
 FACEBOOK(1), 
 /** The twitter. */
 TWITTER(2);
	
	/** The type of service number. */
	private int typeOfServiceNumber;
	
	/**
	 * Instantiates a new service.
	 *
	 * @param typeOfServiceNumber the type of service number
	 */
	private Service(int typeOfServiceNumber) {
		this.typeOfServiceNumber = typeOfServiceNumber;
	}
	
	/**
	 * Gets the type of service number.
	 *
	 * @return the type of service number
	 */
	public int getTypeOfServiceNumber() {
		return typeOfServiceNumber;
	}
	
	/**
	 * String format.
	 *
	 * @return the string
	 */
	public String stringFormat() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
