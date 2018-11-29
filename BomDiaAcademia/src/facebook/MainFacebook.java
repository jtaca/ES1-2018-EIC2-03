/*
 * 
 */
package facebook;

import java.io.SequenceInputStream;
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
import com.restfb.types.Likes;
import com.restfb.types.Likes.LikeItem;
import com.restfb.types.Link;
import com.restfb.types.Message;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

public class MainFacebook {
	public static void main(String[] args) {

		//String accessToken (AKA USER) = "EAAePp5MZAcE4BAInZCUbLfwuisPqseRZAMNX2xqyebsGEAm3KctHzWrikzdNRXYpX49J3vNo8vz0h7mKRctVFk0aZAZCMQd0VmCq9zv84dcRZAVp7ZAFN3NVLk04qyoQrNAOSWpqZCjCXP7S0fdGHRkjqoZApFZBmEAARCipUHpkZCBXgZDZD";
		String accessToken2 = "EAAePp5MZAcE4BANuO4pcvl7kWxeagvcvJ2rPVVmlBLeoljRRg0UEcRrFrZAqKA18bMfxBI2Viv6TXtA8ZBSPdHwQl3pioifUrUvTXZADJTb3tJUPHO8nhZA2X2ATEAn7qfQ0Ks5sr5gMTiS2CaZAX57DeI6rSOmx1sx6cqaZBuFqAtXokKvp3ZBC";
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
			 String postId = post.getId();  
		     //System.out.println("Post: " + post.getId()+ ", Message: "+ post.getMessage() +", Updated time: "+ post.getUpdatedTime());
			 Post post1 = fbClient2.fetchObject(postId, Post.class, Parameter.with("fields", "from,to,likes.summary(true),description.summary(true),comments.summary(true),message.summary(true),attachments.summary(true)"));
		     System.out.println("Post[ Id: "+post1.getId()+";\n Message:"+post1.getMessage()+
		    		 ";\n Description:"+post1.getDescription());
		    		 //";\n Data:"+post1.getAttachments());
		    		
		    		 
		     try {
		    	 System.out.println("   Data URL: "+post1.getAttachments().getData().get(0).getUrl());
		    	 System.out.println("   Data Title: "+post1.getAttachments().getData().get(0).getTitle());
		    	 System.out.println("   Data Description: "+post1.getAttachments().getData().get(0).getDescription());
		    	 System.out.println("   Data Media: "+post1.getAttachments().getData().get(0).getMedia());
			     
			} catch (Exception e) {
				System.out.println(e);
			}
		    System.out.println("\n Likes:"+post1.getLikes().getTotalCount());
		    
			fbClient2.publish(post.getId()+"/likes", Boolean.class); 
			
			Likes like = post.getLikes();
			// retrieve a specific like, then
			//like.
			//fbClient2.delete(like);
			GraphResponse publishMessageResponse = 
					fbClient2.publish("me/feed", GraphResponse.class,
					    Parameter.with("message", "RestFB testy"));

			System.out.println("Published message ID: " + publishMessageResponse.getId());
		    System.out.println(" Comments ("+post1.getCommentsCount()+"): "+post1.getComments()+"]");
		    
		    fbClient2.publish(post1.getId()+"/comments", String.class, Parameter.with("message", "Your comment here"));
		    	
		    
		     
//		     if(post1.getAttachments()!= null) {
//		    	 
//			    	 
//		     }
		    	
		     //System.out.println("    Comment[ "+post1.getComments().toString()+"]");
		     
//		     String postId = post.getId();
//		    		 Connection<Comment> commentConnection 
//		    		    = fbClient2.fetchConnection(postId + "/comments", 
//		    		          Comment.class, Parameter.with("limit", 10));
//		    		 
		    		 
//    		 int personalLimit = 50;
//    		 for (List<Comment> commentPage : commentConnection) {
//    		   for (Comment comment : commentPage) {
//    		     System.out.println("Id: " + comment.getId());
//    		     System.out.println(comment.getMessage());
//    		     personalLimit--;
//
//    		     // break both loops
//    		     if (personalLimit == 0) {
//    		        return;
//    		     }
//    		   }
//    		  
//    		 }
		   }
		}
		
	}

}
