package other;

import java.util.ArrayList;
import java.util.List;

import email.EmailConnection;
import files.ReadAndWriteXMLFile;
import tasks.EmailReaderTask;
import tasks.FacebookPostReaderTask;
import tasks.ServiceReadTask;
import tasks.TwitterPostReaderTask;
import threads.ThreadPool;

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
			ServiceReadTask task;
			List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
			List<XMLUserConfiguration> list_of_user_configuration = ReadAndWriteXMLFile.ReadConfigXMLFile();
			for(XMLUserConfiguration xml_user_config : list_of_user_configuration) {
				switch (xml_user_config.getService()) {
				case EMAIL:
					task = new EmailReaderTask(new EmailConnection(xml_user_config.getUsername(), xml_user_config.getPassword()));
					break;
					
				case TWITTER:
					task = new TwitterPostReaderTask(); // Require to be implemented object oriented and not function oriented
					break;
					
				case FACEBOOK:
					task = new FacebookPostReaderTask(); // Require to be implemented object oriented and not function oriented
					break;

				default:
					task = null;
					break;
				}
				if(task != null) {
					tasks.add(task);
				}
			}
			ThreadPool.refreshGUIWithThreads(tasks);
		} catch (Exception e) {
			System.out.println("Cant Refresh. There is no XML file yet.");
		}
		
	}

}
