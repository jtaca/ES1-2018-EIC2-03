package entry_objects;

import java.util.Date;

import other.Service;
import twitter4j.Status;

/**
 * The Class TwitterEntry.
 * 
 * @author Alexandre Mendes
 * @version 1.0
 */
public class TwitterEntry implements InformationEntry { //

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private Status status;
	
	/** The date. */
	private Date date;
	
	/** The is retweet. */
	private boolean isRetweet;

	/**
	 * Instantiates a new twitter entry.
	 *
	 * @param status the status
	 */
	public TwitterEntry(Status status) {
		this.status = status;
		date = status.getCreatedAt();
		isRetweet = status.isRetweet();
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Checks if is retweet.
	 *
	 * @return true, if is retweet
	 */
	public boolean isRetweet() {
		return isRetweet;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return status.getText();
	}
}
