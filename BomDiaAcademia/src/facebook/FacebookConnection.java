package facebook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.exception.FacebookException;
import com.restfb.types.Post;
import com.restfb.types.User;

import entry_objects.FacebookEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import other.Filter;
import other.Service;

public class FacebookConnection {
	
	private static String accessToken2 = "EAAePp5MZAcE4BAMdraxWkVpfud3OFZBvNZCqMBOLU2ue3DxgWApgZA3f4ZA5rPM3ueimfZAp2PRBPNEuTdx0Hi3JTqIKeNCzgT1gY9s239sQJM3lLOA3Y2OSFtyrzHjp4n1EWhbYbXqyn7U3W8cm5mRilcZAjwERVZC1JUuRYUgRuCZBt8OuCdtBJ9e6aZBkoAZCWGTCc7s60E3kAZDZD";
	private static FacebookClient fbClient2 = init();
	private static User me2 ;
	
	public static FacebookClient getInstance() {
		return fbClient2;
	}

	
	public static void ExtendAccessToken() {
		
		fbClient2.obtainExtendedAccessToken("2128274727202894", "5b08263178f3db9cbd189e2100f0ee54", accessToken2);
		
	}
	
	private static DefaultFacebookClient init() {
	
		try {
			fbClient2 = new DefaultFacebookClient(accessToken2);
			me2 = fbClient2.fetchObject("me", User.class);
			
			System.out.println("Facebook:");
			System.out.println("Id: " + me2.getId());
			System.out.println("Name: " + me2.getName());
			
		} catch (FacebookException e) {
			System.out.println(e);
		}
		
		return (DefaultFacebookClient) fbClient2;
		
	}
	
	
	
	public static List<InformationEntry> requestFacebook() {
		List<InformationEntry> list = new ArrayList<>();

		try {
			
			Connection<Post> myFeed = fbClient2.fetchConnection("me/feed", Post.class);
			Iterator<List<Post>> it = myFeed.iterator();
			
			while(it.hasNext()) {
			   List<Post> myFeedPage = it.next();
			   for (Post post : myFeedPage) {
				 String postId = post.getId();  
			     //System.out.println("Post: " + post.getId()+ ", Message: "+ post.getMessage() +", Updated time: "+ post.getUpdatedTime());
				 Post post1 = fbClient2.fetchObject(postId, Post.class, Parameter.with("fields", "from,to,likes.summary(true),description.summary(true),comments.summary(true),message.summary(true),attachments.summary(true)"));
				
				 list.add(new FacebookEntry(post1, post1.getCreatedTime()));
			   }	
			   
			} 
			return list;
		} catch (FacebookException te) {
			te.printStackTrace();
			return null;
		}
	}
	

	

	
	

	

}
