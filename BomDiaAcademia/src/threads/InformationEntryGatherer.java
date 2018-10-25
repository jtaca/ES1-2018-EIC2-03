package threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.DateComparator;
import entry_objects.InformationEntry;

/**
 * The Class InformationEntryGatherer.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class InformationEntryGatherer { //

	/** The list. */
	private List<InformationEntry> list;
	
	/** The barrier. */
	private SingleBarrier barrier;
	// Should add GUI here if it isnt static
	
	/**
	 * Instantiates a new information entry gatherer.
	 *
	 * @param size the size
	 */
	public InformationEntryGatherer(int size) { // Should add the GUI here or make the GUI static in order for this class to access it
		list = new ArrayList<InformationEntry>();
		barrier = new SingleBarrier(size);
	}
	
	/**
	 * Wait for every ocurrence.
	 */
	public void waitForEveryOcurrence() {
		barrier.barrierWait();
		list.sort(new DateComparator()); // O comparator pode estar a dar uma ordem crescente e não decrescente como queremos, tem de ser testado
		// Give the list to the GUI for it to show in the window
	}
	
	/**
	 * Adds the result.
	 *
	 * @param list_of_entrys the list of entrys
	 */
	public synchronized void addResult(List<InformationEntry> list_of_entrys) {
		list.addAll(list_of_entrys);
		barrier.barrierPost();
	}

}
