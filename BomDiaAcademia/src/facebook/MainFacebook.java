package facebook;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
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
		String accessToken2 = "EAAePp5MZAcE4BAJa2dymr9by3WRfV5ZBQj60AvMlaPsrSvqJBAmlLiejx4ETrgJZCZBDH1w7jLlyCOuZAmQhvQGz9xpiU6UW8EWeamagCbFZBhEOhgp2AGEZBN7CLAukZBZCUdZBl9vlsCsaXIvezJMZBpEHJkAsnqzMVUhCGDW5T8eZA8VhqLzu22Mhwz9hV4SZCRPNUdFdDwiqeCQZDZD";
		FacebookClient fbClient2 = new DefaultFacebookClient(accessToken2);
		User me2 = fbClient2.fetchObject("me", User.class);
		System.out.println("Facebook:");
		System.out.println("Id: " + me2.getId());
		System.out.println("Name: " + me2.getName());

		/* 
		 * Facebook API Tutorials in Java # 4 | Create Your Own Fb APP & Extend User Access Token  
		 * https://www.youtube.com/watch?v=qFZazZ1JXsM&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb&index=5
		 */
		String accessToken4 = "EAAePp5MZAcE4BAJa2dymr9by3WRfV5ZBQj60AvMlaPsrSvqJBAmlLiejx4ETrgJZCZBDH1w7jLlyCOuZAmQhvQGz9xpiU6UW8EWeamagCbFZBhEOhgp2AGEZBN7CLAukZBZCUdZBl9vlsCsaXIvezJMZBpEHJkAsnqzMVUhCGDW5T8eZA8VhqLzu22Mhwz9hV4SZCRPNUdFdDwiqeCQZDZD";
		FacebookClient fbClient4 = new DefaultFacebookClient(accessToken4);
		//AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("490458541421089","b4b0922f88578643b1c3795ed4cde605");
		//System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
		//System.out.println("Expires: " + extendedAccessToken4.getExpires());

		/* 
		 * Facebook API Tutorials in Java # 5 | Get User Timeline Posts
		 * https://www.youtube.com/watch?v=wiFif4gOdFE&index=6&list=PLYPFxrXyK0BwiXNe09hTPjFqYbsWv8gxb
		*/ 
		String accessToken5 ;//= "EAAGZBEccjciEBAJ37ZAIbHKiL1Mo1HHex2pQTcs41dq8azfBvFGgt4eGgKBq12kSssOof51FKO0niKu7AaVKs3dy8W1ilqp4xcjFD1F9mmjJpVyeDnZAffUXRfh7zXL06BuSwQtfHMJbmJ079qCnkT844brHx966cz73JZBZBFy2Bv1rWu7T1rQddZCVpxywZCO6lDxoWDk2gZDZD";
		accessToken5 = "EAAePp5MZAcE4BAJa2dymr9by3WRfV5ZBQj60AvMlaPsrSvqJBAmlLiejx4ETrgJZCZBDH1w7jLlyCOuZAmQhvQGz9xpiU6UW8EWeamagCbFZBhEOhgp2AGEZBN7CLAukZBZCUdZBl9vlsCsaXIvezJMZBpEHJkAsnqzMVUhCGDW5T8eZA8VhqLzu22Mhwz9hV4SZCRPNUdFdDwiqeCQZDZD";	
		FacebookClient fbClient5 = new DefaultFacebookClient(accessToken5);

		Connection<Post> result = fbClient5.fetchConnection("me?fields=feed{id,name,attachments,description}",Post.class);
		System.out.println("\nPosts:" + result);
		int counter5 = 0;
		int counterTotal = 0;
		for (List<Post> page : result) {
			for (Post aPost : page) {
				// Filters only posts that contain the word "Inform"
				if (aPost.getMessage() != null && aPost.getMessage().contains("Mestrado")) {
				
					System.out.println("---- Post "+ counter5 + " ----");
					System.out.println("Id: "+"fb.com/"+aPost.getId());
					System.out.println("Message: "+aPost.getMessage());
					System.out.println("Caption: "+aPost.getCaption());
					System.out.println("Description: "+aPost.getDescription());
					System.out.println("Created: "+aPost.getCreatedTime());
					System.out.println("Name: "+aPost.getName());
					System.out.println("Created: "+aPost.toString());
					
					counter5++;
				}
				counterTotal++;
			}
		}
		System.out.println("-------------\nNo of Results: " + counter5+"/"+counterTotal);		
	}

}
