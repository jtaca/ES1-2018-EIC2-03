package gui;

import java.util.Date;

import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import javafx.scene.layout.HBox;
import other.Service;

/**
 * The Class PostBox gives a visual representation of an InformationEntry in the
 * posts list.
 * 
 * @author Rostislav Andreev
 * @version 2.0
 */
public class PostBox extends HBox {

	/** The information entry. */
	private InformationEntry informationEntry;

	/** The service. */
	private Service service;

	private String postAuthor;

	private String emailReceiver;

	private Date date;

	/**
	 * Instantiates a new post box.
	 *
	 * @param informationEntry the information entry
	 */
	public PostBox(InformationEntry informationEntry) {
		super();
		this.informationEntry = informationEntry;
		service = informationEntry.getService();
		date = informationEntry.getDate();

		if (informationEntry instanceof EmailEntry) {
			postAuthor = ((EmailEntry) informationEntry).getWriterName();
			emailReceiver = ((EmailEntry) informationEntry).getReceiverEmail();
		} else if (informationEntry instanceof TwitterEntry)
			postAuthor = ((TwitterEntry) informationEntry).getUsername();
	}

	public Date getDate() {
		return date;
	}

	public String getPostAuthor() {
		return postAuthor;
	}

	public String getEmailReceiver() {
		return emailReceiver;
	}

	/**
	 * Gets the information entry.
	 *
	 * @return the information entry
	 */
	public InformationEntry getInformationEntry() {
		return informationEntry;
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {
		return service;
	}
}