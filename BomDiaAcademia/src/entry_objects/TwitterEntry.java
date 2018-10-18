package entry_objects;

import java.util.Date;

import other.Service;

public class TwitterEntry implements InformationEntry {

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getService() {
		return Service.TWITTER;
	}

}
