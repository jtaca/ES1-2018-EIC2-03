package entry_objects;

import java.util.Date;

import other.Service;

/**
 * The Class EmailEntry.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class EmailEntry implements InformationEntry {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The date. */
	private Date date;
	
	/** The writer name. */
	private String writerName;
	
	/** The subject. */
	private String subject;
	
	/** The content. */
	private String content;
	
	/**
	 * Instantiates a new email entry.
	 *
	 * @param date the date
	 * @param writerName the writer name
	 * @param subject the subject
	 * @param content the content
	 */
	public EmailEntry(Date date, String writerName, String subject, String content) {
		this.date = date;
		this.writerName = writerName;
		this.subject = subject;
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see entry_objects.InformationEntry#getDate()
	 */
	public Date getDate() {
		return date;
	}
	
	/* (non-Javadoc)
	 * @see entry_objects.InformationEntry#getService()
	 */
	public Service getService() {
		return Service.EMAIL;
	}
	
	/**
	 * Gets the writer name.
	 *
	 * @return the writer name
	 */
	public String getWriterName() {
		return writerName;
	}
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Date: " + getDate().getTime() + "" + System.getProperty("line.separator") 
		+ "Service: " + getService().stringFormat() + System.getProperty("line.separator")
		+ "WriterName: " + getWriterName() + System.getProperty("line.separator")
		+ "Subject: " + getSubject() + System.getProperty("line.separator")
		+ "Content: " + getContent() + System.getProperty("line.separator");
	}
	
}
