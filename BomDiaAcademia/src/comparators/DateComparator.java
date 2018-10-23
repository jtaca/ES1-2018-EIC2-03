package comparators;

import java.util.Comparator;

import entry_objects.InformationEntry;

/**
 * The Class DateComparator.
 */
public class DateComparator implements Comparator<InformationEntry> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(InformationEntry informationEntry1, InformationEntry informationEntry2) {
		return informationEntry2.getDate().compareTo(informationEntry1.getDate()); // Não faço ideia se é para fazer assim
	}
		
}
