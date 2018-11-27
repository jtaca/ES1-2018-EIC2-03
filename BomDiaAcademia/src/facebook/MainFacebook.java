/*
 * 
 */
package facebook;

import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.Account;
import com.restfb.types.Comment;
import com.restfb.types.GraphResponse;
import com.restfb.types.Link;
import com.restfb.types.Message;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

public class MainFacebook {
	public static void main(String[] args) {

		String accessToken2 = "EAAePp5MZAcE4BALVlJYSGZAC3dk9unBPASbYwL0OhQVXEopfaKtr1JxLghJWkh2JyOdS4N6v3TLl47nwlMflueUHvtlRZAhG4muHhsCFvyd2ZAfE7YB1j3ysrRc3J0kpu8ykOgw3Pn23llVxeWXQ3f9MyIxOJCCJuFZCPjdKH5zIUdR7d2BTrUcv4CIcVSDZAzXBjRG23wFAZDZD";
		FacebookClient fbClient2 = new DefaultFacebookClient(accessToken2);
		User me2 = fbClient2.fetchObject("me", User.class);
		System.out.println("Facebook:");
		System.out.println("Id: " + me2.getId());
		System.out.println("Name: " + me2.getName());
		
		
//		GraphResponse publishMessageResponse =
//				fbClient2.publish("me/feed", GraphResponse.class,
//				    Parameter.with("message", "RestFB test"));
//
//				System.out.println("Published message ID: " + publishMessageResponse.getId());
//		
//		Working
		
//		AccessToken accessToken =
//				  new DefaultFacebookClient().obtainExtendedAccessToken("2128274727202894",
//				    "5b08263178f3db9cbd189e2100f0ee54", accessToken2);
			
		Connection<Post> myFeed = fbClient2.fetchConnection("me/feed", Post.class);
		
		Iterator<List<Post>> it = myFeed.iterator();

		while(it.hasNext()) {
		   List<Post> myFeedPage = it.next();
		   
		   for (Post post : myFeedPage) {
			   
		     System.out.println("Post: " + post.getId()+ ", Message: "+ post.getMessage() +", Updated time: "+ post.getUpdatedTime());
		     
		     String postId = post.getId();
		    		 Connection<Comment> commentConnection 
		    		    = fbClient2.fetchConnection(postId + "/comments", 
		    		          Comment.class, Parameter.with("limit", 100));
		    		 
		    		 
    		 int personalLimit = 50;
    		 for (List<Comment> commentPage : commentConnection) {
    		   for (Comment comment : commentPage) {
    		     System.out.println("Id: " + comment.getId());
    		     System.out.println(comment.getMessage());
    		     personalLimit--;

    		     // break both loops
    		     if (personalLimit == 0) {
    		        return;
    		     }
    		   }
    		   
    		   Post post1 = fbClient2.fetchObject(postId, Post.class, Parameter.with("fields", "from,to,likes.summary(true),description.summary(true),"));
    		   System.out.println(post1.toString());
    		 }
		   }
		}
		
	}

}
