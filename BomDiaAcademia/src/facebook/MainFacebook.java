/*
 * 
 */
package facebook;

import java.util.List;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;



/**
 * The Class MainFacebook.
 * @author João Aparício
 * @version 1.0
 */
public class MainFacebook {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		String accessToken ;
		accessToken = "EAAePp5MZAcE4BAEZBB44cDicBaNvGIJeD1aZCaBZB4ARsXrwhALYqKLIcYEZAGOjJZBposTXZAbXmhKByMtisqzhGNOmQnr9PbI0SZBZBlC5pl6iZAe4XfbkCMmw5EZBDcQ6SvZBhY9o0lhQG8z7HrZAZA164OZBX4xog42wqZB8KAu7fU9TFvgo4DOUPFniqwNIADhz41bepHxWZASWBlUNQNZAuSVffN";	
		//String appToken = "EAAePp5MZAcE4BAEEZAG8KSo144aFQ2MZArmaAwR6jrqM2mXPUHpURokX5CtAOyBqFUwJjkUSy8sZAaqwK6fKucZCh6NHwmutO6iC3iaxthM467dUApfllSi24CLo8cvufsDNkcVCSSBSlZCg5Ilr4FGkITfgBq4mgTZA70V0MWgUwf9eem3NA72VKiokEugJ9WpQy2p8CZAE2QZDZD";
		
		Facebook facebook = new FacebookFactory().getInstance();
		
		facebook.setOAuthAppId("2128274727202894","5b08263178f3db9cbd189e2100f0ee54");
		facebook.setOAuthPermissions("2128274727202894|mv9W3YTaU_vEV0cP9txuN38DQ58");
		facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
		try {
			facebook.postStatusMessage("Hello World YOLO.");
		} catch (FacebookException e) {
			
			e.printStackTrace();
		}
		
		
		
	}

}
