package entry_objects;

import java.io.Serializable;
import java.util.Date;

import other.Service;

public interface InformationEntry extends Serializable {
	
	public Date getDate();
	
	public Service getService();
	
	public String toString();
	
}
