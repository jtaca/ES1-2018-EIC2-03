package entry_objects;

import java.util.Date;

import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.StoryAttachment;

import other.Service;

/**
 * The Class FacebookEntry.
 * @author Joao Aparicio
 * @version 3.0
 */
public class FacebookEntry implements InformationEntry { //
	private Post post;
	private static final long serialVersionUID = 1L;
	private Date date;
	private StoryAttachment Attachments;
	
	
	

	public FacebookEntry(Post post, Date date) {
		this.post = post;
		//this.date = date;
		this.date = date;
		this.Attachments = this.post.getAttachments().getData().get(0);
		//System.out.println(date.toString());
	}




	@Override
	public String toString() {
		String out;

		
		try {
			out = "Post[ Id: "+post.getId()+" Date: "+this.date+
				";\n Message:"+post.getMessage();
			try {
				out +=   "\n   Data URL: "+post.getAttachments().getData().get(0).getUrl()+
			    		 "\n   Data Title: "+post.getAttachments().getData().get(0).getTitle()+
			    		 "\n   Data Description: "+post.getAttachments().getData().get(0).getDescription()+
			    		 "\n   Data Media: "+post.getAttachments().getData().get(0).getMedia();
			} catch (NullPointerException e) {
				System.out.println("No attachments found: "+e);
				
			}
			out+= ";\n Description:"+post.getDescription()+
			"\n Likes:"+post.getLikes().getTotalCount()+
    		"\n Comments ("+post.getCommentsCount()+"): "+post.getComments()+"]";
					
		} catch (Exception e) {
			System.out.println(e);
			out = post.toString();
		}
		return out;
	}
	



	public StoryAttachment getAttachments() {
		return Attachments;
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
