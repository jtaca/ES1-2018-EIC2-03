package gui;

import entry_objects.InformationEntry;
import javafx.scene.layout.HBox;
import other.Service;

public class PostBox extends HBox {

	private InformationEntry informationEntry;
	private Service service;

	public PostBox(InformationEntry informationEntry) {
		super();
		this.informationEntry = informationEntry;
		service = informationEntry.getService();
	}

	public InformationEntry getInformationEntry() {
		return informationEntry;
	}

	public Service getService() {
		return service;
	}
}