package entry_objects;

import java.util.Date;

import other.Service;

/**
 * The Class TwitterEntry.
 */
public class TwitterEntry implements InformationEntry {

	/* (non-Javadoc)
	 * @see entry_objects.InformationEntry#getDate()
	 */
	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see entry_objects.InformationEntry#getService()
	 */
	@Override
	public Service getService() {
		return Service.TWITTER;
	}

}
