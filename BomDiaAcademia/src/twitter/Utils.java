package twitter;

import files.ReadAndWriteXMLFile;
import other.XMLUserConfiguration;

public class Utils {
	public static String getConsumerKeyFromXML() throws Exception{
		XMLUserConfiguration twitterKeys = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
		return twitterKeys.getTwitterConsumerKey();
	}
	public static String getSecretKeyFromXML() throws Exception{
		XMLUserConfiguration twitterKeys = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
		return twitterKeys.getTwitterSecretKey();
	}
}
