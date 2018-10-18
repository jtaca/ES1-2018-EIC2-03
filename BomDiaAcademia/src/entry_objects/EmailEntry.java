package entry_objects;

import java.util.Date;

import other.Service;

public class EmailEntry implements InformationEntry {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private String writerName;
	private String subject;
	private String content;
	
	public EmailEntry(Date date, String writerName, String subject, String content) {
		this.date = date;
		this.writerName = writerName;
		this.subject = subject;
		this.content = content;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Service getService() {
		return Service.EMAIL;
	}
	
	public String getWriterName() {
		return writerName;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return "Date: " + getDate().getTime() + "" + System.getProperty("line.separator") 
		+ "Service: " + getService().stringFormat() + System.getProperty("line.separator")
		+ "WriterName: " + getWriterName() + System.getProperty("line.separator")
		+ "Subject: " + getSubject() + System.getProperty("line.separator")
		+ "Content: " + getContent() + System.getProperty("line.separator");
	}
	
}
