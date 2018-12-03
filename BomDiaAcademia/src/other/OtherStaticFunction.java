package other;

import java.util.ArrayList;
import java.util.List;

import email.EmailConnection;
import facebook.FacebookConnection;
import files.ReadAndWriteXMLFile;
import tasks.EmailReaderTask;
import tasks.FacebookPostReaderTask;
import tasks.ServiceReadTask;
import tasks.TwitterPostReaderTask;
import threads.ThreadPool;
import twitter.TwitterFunctions;

/**
 * The Class OtherStaticFunction.
 * @author Alexandre Mendes
 * @version 2.0
 */
public class OtherStaticFunction {
	
	/**
	 * Refresh GUI with threads.
	 */
	public static void refreshGUIWithThreads() {
		try {
			ServiceReadTask task = null;
			
			boolean use_info_from_file_to_email = true;
			boolean use_info_from_file_to_twitter = true;
			boolean use_info_from_file_to_facebook = true;
			
			List<EmailConnection> email_connections = ControlCenter.getInstance().getEmailList();
			List<TwitterFunctions> twitter_functions = ControlCenter.getInstance().getTwitterList();
			List<FacebookConnection> facebook_connections = ControlCenter.getInstance().getFacebookList();
			
			List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
			
			if(email_connections != null && !email_connections.isEmpty()) {
				for(EmailConnection email : email_connections) {
					tasks.add(new EmailReaderTask(email));
				}
				use_info_from_file_to_email = false;
			}
			if(twitter_functions != null && !twitter_functions.isEmpty()) {
				for(TwitterFunctions twitter : twitter_functions) {
					tasks.add(new TwitterPostReaderTask());
				}
				use_info_from_file_to_twitter = false;
			}
			if(facebook_connections != null && !facebook_connections.isEmpty()) {
				for(FacebookConnection facebook : facebook_connections) {
					tasks.add(new FacebookPostReaderTask());
				}
				use_info_from_file_to_facebook = false;
			}
			
			if(use_info_from_file_to_email || use_info_from_file_to_twitter || use_info_from_file_to_facebook) {
				List<XMLUserConfiguration> list_of_user_configuration = ReadAndWriteXMLFile.ReadConfigXMLFile();
				for(XMLUserConfiguration xml_user_config : list_of_user_configuration) {
					switch (xml_user_config.getService()) {
					case EMAIL:
						if(use_info_from_file_to_email == true) {
							task = new EmailReaderTask(new EmailConnection(xml_user_config.getUsername(), xml_user_config.getPassword()));
						}
						break;
						
					case TWITTER:
						if(use_info_from_file_to_twitter == true) {
							task = new TwitterPostReaderTask(); // Require to be implemented object oriented and not function oriented
						}
						break;
						
					case FACEBOOK:
						if(use_info_from_file_to_facebook == true) {
							task = new FacebookPostReaderTask(); // Require to be implemented object oriented and not function oriented
						}
						break;

					}
					if(task != null) {
						tasks.add(task);
						task = null;
					}
				}
			}
			ThreadPool.refreshGUIWithThreads(tasks);
		} catch (Exception e) {
			System.out.println("Cant Refresh. There is no XML file yet.");
		}
		
	}

}
