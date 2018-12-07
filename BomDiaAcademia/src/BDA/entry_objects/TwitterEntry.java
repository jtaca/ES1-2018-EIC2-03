package BDA.entry_objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BDA.other.Service;
import twitter4j.MediaEntity;
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

	/** The name. */
	private String name;

	/** The username. */
	private String username;

	/** The content. */
	private String content;

	/** The profile picture URL. */
	private String profilePictureURL;

	/** The retweeter. */
	private String retweeter;

	/** The media URL. */
	private List<String> mediaURL;

	/** The is retweet. */
	private boolean isRetweet;

	/** The status. */
	private Status status;

	/** The date. */
	private Date date;

	/**
	 * Instantiates a new twitter entry.
	 *
	 * @param s the s
	 */
	public TwitterEntry(Status s) {
		mediaURL = new ArrayList<>();

		if (s.isRetweet()) {
			name = s.getRetweetedStatus().getUser().getName();
			username = s.getRetweetedStatus().getUser().getScreenName();
			content = s.getRetweetedStatus().getText().trim();
			profilePictureURL = s.getRetweetedStatus().getUser().get400x400ProfileImageURL();
			retweeter = s.getUser().getName();

			for (MediaEntity media : s.getRetweetedStatus().getMediaEntities())
				if (media.getType().equals("video"))
					mediaURL.add(media.getVideoVariants()[0].getUrl() + "!;!video");
				else if (media.getType().equals("animated_gif"))
					mediaURL.add(media.getVideoVariants()[0].getUrl() + "!;!animated_gif");
				else
					mediaURL.add(media.getMediaURLHttps() + "!;!photo");

			isRetweet = true;
		} else {
			name = s.getUser().getName();
			username = s.getUser().getScreenName();
			content = s.getText().trim();
			profilePictureURL = s.getUser().get400x400ProfileImageURL();

			for (MediaEntity media : s.getMediaEntities())
				if (media.getType().equals("video"))
					mediaURL.add(media.getVideoVariants()[0].getUrl() + "!;!video");
				else if (media.getType().equals("animated_gif"))
					mediaURL.add(media.getVideoVariants()[0].getUrl() + "!;!animated_gif");
				else
					mediaURL.add(media.getMediaURLHttps() + "!;!photo");

			isRetweet = false;
		}

		status = s;
		date = s.getCreatedAt();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Gets the profile picture URL.
	 *
	 * @return the profile picture URL
	 */
	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	/**
	 * Gets the retweeter.
	 *
	 * @return the retweeter
	 */
	public String getRetweeter() {
		return retweeter;
	}

	/**
	 * Gets the media URL.
	 *
	 * @return the media URL
	 */
	public List<String> getMediaURL() {
		return mediaURL;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return status.getText();
	}
}
