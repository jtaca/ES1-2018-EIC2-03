package entry_objects;

import java.util.Date;

import other.Service;

public class FacebookEntry implements InformationEntry {

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getService() {
		return Service.FACEBOOK;
	}

}
