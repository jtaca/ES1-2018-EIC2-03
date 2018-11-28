package entry_objects;

import java.util.Date;

import com.restfb.types.Post;

import other.Service;
import twitter4j.Status;

/**
 * The Class FacebookEntry.
 * @author Joao Aparicio
 * @version 3.0
 */
public class FacebookEntry implements InformationEntry { //
	private Post post;
	private static final long serialVersionUID = 1L;
	private Date date;
	
	
	

	public FacebookEntry(Post post, Date date) {
		this.post = post;
		this.date = date;
	}




	@Override
	public String toString() {
		try {
			return ("Post[ Id: "+post.getId()+";\n Message:"+post.getMessage()+
		    		 ";\n Description:"+post.getDescription()+
		    		 "\n   Data URL: "+post.getAttachments().getData().get(0).getUrl()+
		    		 "\n   Data Title: "+post.getAttachments().getData().get(0).getTitle()+
		    		 "\n   Data Description: "+post.getAttachments().getData().get(0).getDescription()+
		    		 "\n   Data Media: "+post.getAttachments().getData().get(0).getMedia()+
		    		 "\n Likes:"+post.getLikes().getTotalCount()+
		    		 "\n Comments ("+post.getCommentsCount()+"): "+post.getComments()+"]");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	



	public Post getPost() {
		return post;
	}





	public Date getDate() {
		return date;
	}



	/* (non-Javadoc)
	 * @see entry_objects.InformationEntry#getService()
	 */
	@Override
	public Service getService() {
		return Service.FACEBOOK;
	}

}
