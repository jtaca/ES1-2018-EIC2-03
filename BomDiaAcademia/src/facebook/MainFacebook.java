package facebook;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Post;
import com.restfb.types.User;

public class MainFacebook {
	public static void main(String[] args) {
		
		String accessToken ;
		accessToken = "EAAePp5MZAcE4BAA43NBFkS6tqfsCNNJrUL4A961GAgfnB55WkLDrtMUjJ6WQHk3GzYLbNQ0XxIzfAXwri174vALCAhcQjjxTsYX4C2ZCZBxmCwiRrd93g7qIESxwtkkRzcdz6mAv7HP70YZCKCzifJXLlP6zHberdkaOA7F1uBoYtQBnx2rZB7P6asQaZBZCZBWAQVRZC8YrhYgZDZD";	
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		
		User me2 = fbClient.fetchObject("me", User.class);
		System.out.println("Facebook:");
		System.out.println("Id: " + me2.getId());
		System.out.println("Name: " + me2.getName());
		User me = fbClient.fetchObject("me?fields=feed", User.class);
		
		Post result = fbClient.fetchObject("me",Post.class);
		System.out.println("\nPosts:" + result);
		
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
	}

}
