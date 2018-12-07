package BDA.facebook;

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
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;
import com.restfb.types.GraphResponse;
import com.restfb.types.Post;
import com.restfb.types.User;

import BDA.entry_objects.FacebookEntry;
import BDA.entry_objects.InformationEntry;
import BDA.entry_objects.TwitterEntry;
import BDA.interfaces.ServiceInstance;
import BDA.other.Filter;
import BDA.other.Service;

/** This class handles the Facebook connection and respective functions
 * @author Joao Aparicio
 *
 */
public class FacebookConnection implements ServiceInstance {
	
	//activity going to: https://www.facebook.com/Bomdiaacademia-318510688875649/?modal=admin_todo_tour
	/**
	 * User Access Token
	 */
	private static String accessToken ;
	/**
	 * Access Token for the BomDiaAcademia app
	 */
	private static String accessToken2 ;
	/**
	 * Client used in every user connection
	 */
	private static FacebookClient fbClient;
	/**
	 * Client used in every app connection
	 */
	private static FacebookClient fbClient2;
	/**
	 * 
	 */
	private static User me2 ;
	/**
	 * Instance used for the singleton
	 */
	private static FacebookConnection INSTANCE = new FacebookConnection();
	private static User me;
	
	private static final String loginLink = "https://developers.facebook.com/tools/access_token/";
	
	/**
	 * Constructor
	 */
	private FacebookConnection() {
		accessToken = "";
		accessToken2 = "EAAC3MdcjZCi4BAFr7wro731ZBYu2If7jatZCuDZB2OPgqRmbNVZCQPWmGOPaDUaUp5gmPMViiK6L6FaZBfcBJZCWfZACKeIqZCA5CXgBnFxZAIckEWyGTK2JjiMzSrpFrQONsP7RWgnMQeHrPscpzZCv6qMH4BAhPdrtOEV1HpTYOQ9u49WJORSmQdg";
		fbClient2 = init("me");
		System.out.println("Facebook:");
		System.out.println("Id: " + me2.getId());
		System.out.println("Name: " + me2.getName());
	}
	
	/**
	 * @return the instance of the singleton
	 */
	public static FacebookConnection getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Starts a connection with Facebook
	 */
	@SuppressWarnings("deprecation")
	private static DefaultFacebookClient init(String s) {
	
		try {
			fbClient2 = new DefaultFacebookClient(accessToken2);
			me2 = fbClient2.fetchObject(s, User.class);
			try {
				me2.getAbout();
			} catch (FacebookException e) {
				System.out.println(""+e);
			}
		
	} catch (FacebookException e) {
		System.out.println(e);
	}

		//logIn(accessToken2);
		
		return (DefaultFacebookClient) fbClient2;
		
	}


//	/**
//	 * Setter for app token, to be implemented later.
//	 */
//	public static void setAccessToken2(String accessToken2) {
//		FacebookConnection.accessToken2 = accessToken2;
//	}
	
	/**
	 * Extends the app token
	 */
	public static void ExtendAccessToken(String app, String secret) {
		try {
			fbClient2.obtainExtendedAccessToken(app, secret, accessToken2);
			
			
			
		} catch (FacebookException e) {
			System.out.println("FB exception: "+e);
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	
	
	
	/**
	 * Request for the bom dia academia page
	 */
	public static List<InformationEntry> requestFacebook(String s) {
		List<InformationEntry> list = new ArrayList<>();
		try {
			Connection<Post> myFeed = fbClient2.fetchConnection(s, Post.class);
			Iterator<List<Post>> it = myFeed.iterator();
			
			while(it.hasNext()) {
			   List<Post> myFeedPage = it.next();
			   for (Post post : myFeedPage) {
				 String postId = post.getId();  
			     //System.out.println("Post: " + post.getId()+ ", Message: "+ post.getMessage() +", Updated time: "+ post.getUpdatedTime());
				Post post1 = fbClient2.fetchObject(postId, Post.class, Parameter.with("fields", "name,created_time,from,full_picture,picture,to,likes.summary(true),description.summary(true),comments.summary(true),message.summary(true),attachments.summary(true)"));
				
				String author = "BomDiaAcademiaISCTE";
				
				JsonObject jsonObject = fbClient2.fetchObject("/BomDiaAcademiaISCTE/picture", JsonObject.class,
						Parameter.with("type", "large"), Parameter.with("redirect", "false"));
						JsonValue jsonValue = jsonObject.get("data");
						JsonObject object = jsonValue.asObject();
						String profileImageUrl = object.get("url").asString();
				
				try {
					String linkurl = post1.getAttachments().getData().get(0).getUrl();
					//String name = linkurl.split("/")[3];
					//System.out.println(linkurl.split("/")[3]);
					if (!linkurl.split("/")[3].contains("l.php")) {
						author = linkurl.split("/")[3];
						jsonObject = fbClient2.fetchObject("/"+author+"/picture", JsonObject.class,
								Parameter.with("type", "large"), Parameter.with("redirect", "false"));
								jsonValue = jsonObject.get("data");
								object = jsonValue.asObject();
								profileImageUrl = object.get("url").asString();
					}
					
					
				} catch (Exception e) {
					System.out.println(e);
					
				}
				 list.add(new FacebookEntry(post1, post1.getCreatedTime(), profileImageUrl,author));
			   }	
			   
			} 
			
		} catch (FacebookException e) {
			e.printStackTrace();
			FacebookConnection.ExtendAccessToken("2128274727202894", "5b08263178f3db9cbd189e2100f0ee54");
		} catch (Exception te) {
			System.out.println("unexpected :"+te);
		}
		return list;
	
		
	}
	
	/**
	 * Likes the post with the id given
	 */
	public static void like(String id){
		try {
			fbClient2.publish(id+"/likes", Boolean.class); 
		} catch (FacebookException e) {
			System.out.println("invalid id: "+e);
		}catch (NullPointerException e) {
			System.out.println("invalid id: "+e);
		}
	}
	
	public static void userLike(String id){
		try {
			fbClient.publish(id+"/likes", Boolean.class); 
		} catch (FacebookException e) {
			System.out.println("invalid id (user): "+e);
		}catch (NullPointerException e) {
			System.out.println("invalid id (user): "+e);
		}
	}
	
	
	public static String getLoginlink() {
		return loginLink;
	}

	public static void logIn(String accessToken) {
		try {
			FacebookConnection.accessToken = accessToken;
			fbClient = new DefaultFacebookClient(accessToken);
			me = fbClient.fetchObject("me", User.class);
			System.out.println("Facebook User:");
			System.out.println("Id: " + me.getId());
			System.out.println("Name: " + me.getName());
		} catch (FacebookException e) {
			System.out.println(e);
		}
	}
	
	public static void userCommentOnPost(String id, String message) {
		if(message != null && id != null) {
			try {
				fbClient.publish(id+"/comments", String.class, Parameter.with("message",message));
			} catch (FacebookException e) {
				System.out.println("userCommentOnPost: "+e);
			}
		}else{
			System.out.println("Invalid ID or message:(Maybe login needed?):");
		}
	}

	/**
	 * Posts on the bom dia academia page with the message given
	 */
	public static GraphResponse post(String message) {
		if(message != null) {
			try {
				GraphResponse publishMessageResponse = 
						fbClient2.publish("me/feed", GraphResponse.class,
						    Parameter.with("message", message));

				System.out.println("Published message ID: " + publishMessageResponse.getId());
				return publishMessageResponse;
			} catch (FacebookException e) {
				System.out.println("Error publishing: s"+e);
			}
			
		}
		return null;	
		
		
	}
	
	/**
	 * comments the post with the id and message given
	 */
	public static void commentOnPost(String id, String message) {
		if(message != null && id != null) {
			try {
				fbClient2.publish(id+"/comments", String.class, Parameter.with("message",message));
			} catch (FacebookException e) {
				System.out.println("commentOnPost: "+e);
			}
		}else{
			System.out.println("Invalid ID or message");
		}
	}
	
	
	
	
	/**
	 * getter for the access token 
	 */
	public static String getAccessToken2() {
		return accessToken2;
	}
	
	/**
	 * main for demonstrating how to use the methods in this function
	 */
//	public static void main(String[] args) {
//		FacebookConnection fb = getInstance();
//		 List<InformationEntry> a = fb.requestFacebook(accessToken2);
//		 for (int i = 0; i < a.size(); i++) {
//			System.out.println( a.get(i).toString());
//		}
//		//System.out.println(a);
//		like(((FacebookEntry) a.get(0)).getPost().getId());
//		logIn("EAAC3MdcjZCi4BALOuxuN5VAXSkWIV5qjo4bueT6Fs4llSqMrv4mZAOfyzIfmgX6NomM1Qa7SZCpqq2IWMSVlHJQyaRF2OjTkBTb3ib00UbDZCeAsFCiiqHC1WAN4E8Yh0nPtCOBMwiLQOmb0jtRH3iYP7962N0cZD");
//		userCommentOnPost(((FacebookEntry) a.get(0)).getPost().getId(), "Comment XPTO");
//		//GraphResponse b = post("FaceId");
//		//like(b.getId());
//		//commentOnPost(b.getId(),"Grateful Comment 😛");
//		//ExtendAccessToken();
//		
//		
//	}
	
	/**
	 * Getter for the type of service in use
	 */
	@Override
	public Service getService() {
		return Service.FACEBOOK;
	}



}
