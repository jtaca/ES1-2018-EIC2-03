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
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

public class MainFacebook {
	public static void main(String[] args) {
		
		/* 
		 * Facebook API Tutorials in Java # 1 | Setup Development Environment 
		 * https://www.youtube.com/watch?v=m14hYs1T3FA&index=1&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb
		 */
		/* 
		 * Facebook API Tutorials in Java # 2 | Get User Access Token
		 * https://www.youtube.com/watch?v=GwbO_PdwK_4&index=2&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb
		 */
		String accessToken2 = "EAAePp5MZAcE4BAGUWqzL0zev6o8upuGMPEQFZCD5rxTefRv5KxnZAk7XJZB4CmasfmjsN54jiLXdwiacnsLMDLPzVZCFdPV4ekUbeI6de6SM0Yf17NPZCIWozWpPmQmuEzXlZB5ESQhuwdfHoALdM7Cw4Y3wfqKG8GXCGrLna3WiSO9WcZB7IHsrQ3k3LgpIdSwpS4TPym1iCU9kJzdRrVZCN";
		FacebookClient fbClient2 = new DefaultFacebookClient(accessToken2);
		User me2 = fbClient2.fetchObject("me", User.class);
		System.out.println("Facebook:");
		System.out.println("Id: " + me2.getId());
		System.out.println("Name: " + me2.getName());
		
		/* 
		 * Facebook API Tutorials in Java # 4 | Create Your Own Fb APP & Extend User Access Token  
		 * https://www.youtube.com/watch?v=qFZazZ1JXsM&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb&index=5
		 */
//		String accessToken4 = "EAAePp5MZAcE4BAGUWqzL0zev6o8upuGMPEQFZCD5rxTefRv5KxnZAk7XJZB4CmasfmjsN54jiLXdwiacnsLMDLPzVZCFdPV4ekUbeI6de6SM0Yf17NPZCIWozWpPmQmuEzXlZB5ESQhuwdfHoALdM7Cw4Y3wfqKG8GXCGrLna3WiSO9WcZB7IHsrQ3k3LgpIdSwpS4TPym1iCU9kJzdRrVZCN";
//		FacebookClient fbClient4 = new 	DefaultFacebookClient(accessToken4);
		Connection<Post> myFeed = fbClient2.fetchConnection("me/feed", Post.class);
		
		// Get the iterator 
		Iterator<List<Post>> it = myFeed.iterator();

		while(it.hasNext()) {
		   List<Post> myFeedPage = it.next();
			   
		   // This is the same functionality as the example above
		   for (Post post : myFeedPage) {
		     System.out.println("Post: " + post.getId());
		     
		     String postId = post.getId();
		    		 Connection<Comment> commentConnection 
		    		    = fbClient2.fetchConnection(postId + "/comments", 
		    		          Comment.class, Parameter.with("limit", 10));

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
		    		 }
		     
		   }
		}
		//AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("2128274727202894|mv9W3YTaU_vEV0cP9txuN38DQ58");
		
		//Connection<Post> result = fbClient4.fetchConnection("me?fields=feed{id,name,attachments,description}",Post.class);
		//System.out.println("\nPosts:" + result);
		//AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("490458541421089","b4b0922f88578643b1c3795ed4cde605");
		//System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
		//System.out.println("Expires: " + extendedAccessToken4.getExpires());

		/* 
		 * Facebook API Tutorials in Java # 5 | Get User Timeline Posts
		 * https://www.youtube.com/watch?v=wiFif4gOdFE&index=6&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb
		*/ 
//		String accessToken5 ;//= "EAAGZBEccjciEBAJ37ZAIbHKiL1Mo1HHex2pQTcs41dq8azfBvFGgt4eGgKBq12kSssOof51FKO0niKu7AaVKs3dy8W1ilqp4xcjFD1F9mmjJpVyeDnZAffUXRfh7zXL06BuSwQtfHMJbmJ079qCnkT844brHx966cz73JZBZBFy2Bv1rWu7T1rQddZCVpxywZCO6lDxoWDk2gZDZD";
//		accessToken5 = "EAAePp5MZAcE4BAGUWqzL0zev6o8upuGMPEQFZCD5rxTefRv5KxnZAk7XJZB4CmasfmjsN54jiLXdwiacnsLMDLPzVZCFdPV4ekUbeI6de6SM0Yf17NPZCIWozWpPmQmuEzXlZB5ESQhuwdfHoALdM7Cw4Y3wfqKG8GXCGrLna3WiSO9WcZB7IHsrQ3k3LgpIdSwpS4TPym1iCU9kJzdRrVZCN";	
//		FacebookClient fbClient5 = new DefaultFacebookClient(accessToken5);
//
//		Connection<Post> result = fbClient5.fetchConnection("me?fields=feed{id,name,attachments,description}",Post.class);
//		System.out.println("\nPosts:" + result);
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
