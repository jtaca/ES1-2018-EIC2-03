package entry_objects;

import java.io.Serializable;
import java.util.Date;

import other.Service;

/**
 * The Interface InformationEntry.
 */
public interface InformationEntry extends Serializable {
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate();
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService();
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString();
	
	
	
}
