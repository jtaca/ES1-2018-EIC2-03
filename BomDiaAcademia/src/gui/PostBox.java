package gui;

import entry_objects.InformationEntry;
import javafx.scene.layout.HBox;
import other.Service;

/**
 * The Class PostBox gives a visual representation of an InformationEntry in the
 * posts list.
 */
public class PostBox extends HBox {

	/** The information entry. */
	private InformationEntry informationEntry;

	/** The service. */
	private Service service;

	/**
	 * Instantiates a new post box.
	 *
	 * @param informationEntry the information entry
	 */
	public PostBox(InformationEntry informationEntry) {
		super();
		this.informationEntry = informationEntry;
		service = informationEntry.getService();
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