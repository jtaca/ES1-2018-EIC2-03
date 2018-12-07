/*
 * 
 */
package BDA.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import BDA.comparators.DateComparator;
import BDA.entry_objects.EmailEntry;
import BDA.entry_objects.InformationEntry;
import BDA.files.ReadAndWriteFile;
import BDA.other.Filter;
import BDA.other.Service;
import BDA.tasks.CreateEmailWriterTask;


/**
 * The Class EmailConnection.
 * @author Alexandre Mendes
 * @version 3.0
 */
public class EmailConnection implements BDA.interfaces.ServiceInstance {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The connected. */
	private boolean isConnected = false;
	
	/** The Constant EMAIL_FILE_NAME. */
	private static final String EMAIL_FILE_NAME = "emailEntrys.dat";
	
	/**
	 * Instantiates a new email connection.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public EmailConnection(String username, String password) { // Constructor
		this.username = username;
		this.password = password;
	} // Fim do Constructor
	
	/**
	 * Gets the text from message.
	 *
	 * @param message the message
	 * @return the text from message
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getTextFromMessage(Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}

	/**
	 * Gets the text from mime multipart.
	 *
	 * @param mimeMultipart the mime multipart
	 * @return the text from mime multipart
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException {
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
	
	/**
	 * Verify login.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */
	public static boolean verifyLogin(String username, String password) {
		Store emailStore = null;
		//Folder emailFolder = null;
		boolean connected = false;
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			Session emailSession = Session.getDefaultInstance(properties);
			emailStore = emailSession.getStore("imaps");
			emailStore.connect("outlook.office365.com", username, password); // outlook.office365.com // imap.gmail.com
			connected = true;
			/*// getting the inbox folder
			emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			Message messages[] = emailFolder.getMessages();
			//System.out.println(emailFolder.getMessageCount());
			connected = true;
			*/
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return connected;
			
	}
	
	/**
	 * Receive mail.
	 *
	 * @return the list
	 */
	public List<InformationEntry> receiveMail() {
		List<InformationEntry> information_entry_list = new ArrayList<InformationEntry>();
		//ReadAndWriteFile readAndWriteFiles = new ReadAndWriteFile();
		Store emailStore = null;
		Folder emailFolder = null;
		isConnected = false;
		boolean correctLoginInfo = true;
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			Session emailSession = Session.getDefaultInstance(properties);
			emailStore = emailSession.getStore("imaps");
			emailStore.connect("outlook.office365.com", username, password); // outlook.office365.com // imap.gmail.com
			isConnected = true;
			// getting the inbox folder
			emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			Message messages[] = emailFolder.getMessages();
			//System.out.println(emailFolder.getMessageCount());
			
			Date date;
			String writerName;
			String subject;
			String content;
			InformationEntry informationEntry;
			
			List<String> filters = Filter.getInstance().getFilterList(Service.EMAIL);
			
			for(int i = messages.length - 1 ; i >= 0 ; i--) {
				date = messages[i].getSentDate();
				if(!Filter.getInstance().verifyIfConsiderDate(date)) {
					break;
				}
				writerName = messages[i].getFrom()[0].toString();
				subject = messages[i].getSubject();
//					if(writerName.toLowerCase().contains(filter) || subject.toLowerCase().contains(filter)) {
				if(Filter.verifyIfStringContainsAnyFilter(writerName, filters) || Filter.verifyIfStringContainsAnyFilter(subject, filters)) {
					
					content = getTextFromMessage(messages[i]);
					
					informationEntry = new EmailEntry(username, date, writerName, subject, content);
					
					information_entry_list.add(informationEntry);
				}
				
				
				
				
				//ReadAndWriteFile.writeOnFileAsNewFile("" + (messages.length - i), informationEntry);
				
				//System.out.println("" + (messages.length - i));
			}
			
			/*
			try {
				
				//ReadAndWriteXMLFile.CreateInformationEntryXMLFile(information_entry_list); // require work, it has errors!
				System.out.println("Created the XML file for the Information Entrys.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			
			
		} catch(NoSuchProviderException nspe) {
			nspe.printStackTrace();
			//connected = false;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			System.out.println("Failed authentication (Receive mail)");
			correctLoginInfo = false;
			
			//connected = false;
		} catch (Exception e) {
			e.printStackTrace();
			//connected = false;
		} finally {
			// closing emailFolder and emailStore.
			try {
				if(emailFolder != null)
					emailFolder.close(false);
				if(emailStore != null)
					emailStore.close();
			} catch (MessagingException me) {
				me.printStackTrace();
				isConnected = false;
			}
			
		}
		
		try {
//			boolean login = EmailConnection.verifyLogin(username, password);
//			System.out.println(login);
			//System.out.println(correctLoginInfo);
			if(correctLoginInfo) {
				if(information_entry_list.isEmpty()) {
					information_entry_list = ReadAndWriteFile.loadListOfInformationEntry(EMAIL_FILE_NAME);
					//information_entry_list = ReadAndWriteXMLFile.ReadInformationEntryXMLFile();
					if(information_entry_list != null) {
						System.out.println("Loaded the Information Entrys from the file.");
					}
					//System.out.println(information_entry_list);
				} else {
					information_entry_list.sort(new DateComparator());
					ReadAndWriteFile.saveListOfInformationEntry(EMAIL_FILE_NAME, information_entry_list);
					System.out.println("Emails saved.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0 ; i < information_entry_list.size() ; i++) { // should return this array instead for it to be displayed on the UI
			System.out.println("Email Number " + (i+1) + ".");
			//System.out.println("From: " + ((EmailEntry)information_entry_list.get(i)).getWriterName());
			//System.out.println("Sent date: " + information_entry_list.get(i).getDate());
			//System.out.println("Subject: " + ((EmailEntry)information_entry_list.get(i)).getSubject());
			//System.out.println("Message: " + ((EmailEntry)information_entry_list.get(i)).getContent());
		}
		
		return information_entry_list;
		
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return isConnected;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Send email.
	 *
	 * @param sendEmailTo the send email to
	 * @param subject the subject
	 * @param message the message
	 */
	public void sendEmail(String sendEmailTo, String subject, String message) {
		Transport transport = null;
		try {
			String host = "smtp.office365.com"; // "smtp.office365.com" // smtp.gmail.com // smtp-mail-outlook.com // smtp.office365.com // mail.protection.outlook.com // m.outlook.com // "smtp-mail.outlook.com" // "Outlook.office365.com"
			boolean sessionDebug = false;
			
			Properties props = System.getProperties();
			
//			props.put("mail.smtp.user", username);
//			props.put("mail.smtp.password", password);
			
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587"); // 587 // 465 // 25
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");
			
//			props.put("mail.smtp.startlls.enable", "true");
			
//			Authenticator auth = new Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(username, password);
//				}
//			};
			
			//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); // Este erro tem acontecido desde o inicio, secalhar ï¿½ algo que se possa remover no futuro? (vi isto em algum lado e adicionei thats why)
			SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		    Session mailSession = Session.getInstance(props, authenticator);
			
			
//			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(username));
			InternetAddress[] address = {new InternetAddress(sendEmailTo)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(message);
			
			transport = mailSession.getTransport("smtp");
			System.out.println("Vou errar aqui!");
//			Transport.send(msg, msg.getAllRecipients());
			transport.connect(host, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			System.out.println("Message sent successfully");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.ServiceInstance#getService()
	 */
	@Override
	public Service getService() {
		return Service.EMAIL;
	}
	
	/**
	 * Send email with threads.
	 *
	 * @param emailConnection the email connection
	 * @param sendEmailTo the send email to
	 * @param subject the subject
	 * @param message the message
	 */
	public static void sendEmailWithThreads(EmailConnection emailConnection, String sendEmailTo, String subject, String message) {
		Thread thread = new Thread(new CreateEmailWriterTask(emailConnection, sendEmailTo, subject, message));
		thread.start();
	}
	
}
