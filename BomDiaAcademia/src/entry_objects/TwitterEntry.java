package entry_objects;

import java.util.Date;

import other.Service;
import twitter4j.Status;

/**
 * The Class TwitterEntry.
 */
public class TwitterEntry implements InformationEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Status status;
	private Date date;

	public TwitterEntry(Status status) {
		this.status = status;
		date = status.getCreatedAt();
	}

	public Status getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entry_objects.InformationEntry#getDate()
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entry_objects.InformationEntry#getService()
	 */
	@Override
	public Service getService() {
		return Service.TWITTER;
	}

}
