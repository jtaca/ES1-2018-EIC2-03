package facebook;

import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

//import com.restfb.Connection;
//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.FacebookClient.AccessToken;
//import com.restfb.types.Post;
//import com.restfb.types.User;

public class MainFacebook {
	public static void main(String[] args) {
		
		String accessToken = "EAAePp5MZAcE4BAFZBh78LLdqaZBDYsOazDXGTlZAR85ilpDD191I2DFPemSFmdxk1FB9iWPU2I9qdaqXOzwqoIXRGfMAE0OlZBGEK7Vi2fxhQVLCuk10gNgZBEDy7yz5HMXqZCdwH9ZBRyZAGRJaliKOEczN5hnW57QVQf0SZBzwbs8gZBN1WmVo4bmajbbBaBCFop1YwwsEXfcOgZDZD"; // access token received from Facebook after OAuth authorization
		Facebook facebook = new FacebookTemplate(accessToken);
		//User profile = facebook.userOperations().getUserProfile();
		facebook.feedOperations().updateStatus("This is not working for some unknown reason!");


		//System.out.println(profile.toString());
		
		
		
		
		
		
		
	}
//		
//		String accessToken ;
//		accessToken = "EAAePp5MZAcE4BAA43NBFkS6tqfsCNNJrUL4A961GAgfnB55WkLDrtMUjJ6WQHk3GzYLbNQ0XxIzfAXwri174vALCAhcQjjxTsYX4C2ZCZBxmCwiRrd93g7qIESxwtkkRzcdz6mAv7HP70YZCKCzifJXLlP6zHberdkaOA7F1uBoYtQBnx2rZB7P6asQaZBZCZBWAQVRZC8YrhYgZDZD";	
//		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//		
//		User me2 = fbClient.fetchObject("me", User.class);
//		System.out.println("Facebook:");
//		System.out.println("Id: " + me2.getId());
//		System.out.println("Name: " + me2.getName());
//		
//		Connection<Post> result = fbClient.fetchConnection("me?fields=feed{id,name,attachments,description}",Post.class);
//		System.out.println("\nPosts:" + result);
//		
//		int counter5 = 0;
//		int counterTotal = 0;
//		for (List<Post> page : result) {
//			for (Post aPost : page) {
//				// Filters only posts that contain the word "Inform"
//				if (aPost.getMessage() != null && aPost.getMessage().contains("Mestrado")) {
//				
//					System.out.println("---- Post "+ counter5 + " ----");
//					System.out.println("Id: "+"fb.com/"+aPost.getId());
//					System.out.println("Message: "+aPost.getMessage());
//					System.out.println("Caption: "+aPost.getCaption());
//					System.out.println("Description: "+aPost.getDescription());
//					System.out.println("Created: "+aPost.getCreatedTime());
//					System.out.println("Name: "+aPost.getName());
//					System.out.println("Created: "+aPost.toString());
//					
//					counter5++;
//				}
//				counterTotal++;
//			}
//		}
//		System.out.println("-------------\nNo of Results: " + counter5+"/"+counterTotal);		
//	}

}
