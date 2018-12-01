package entry_objects;

import java.util.Date;

import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Post;
import com.restfb.types.StoryAttachment;
import com.restfb.types.StoryAttachment.Media;

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
	private String AttachmentTitle;
	private String AttachmentUrl;
	private String AttachmentDescription;
	private Media AttachmentMedia;
	private String Message;
	private String id;
	private String Description;
	private String Picture;
	private Long LikeCount;
	private Comments Comments;
	private Object CommentCount;
	
	
	
	
	

	public FacebookEntry(Post post, Date date) {
		this.post = post;
		//this.date = date;
		this.date = date;
		try {
			this.Message = this.post.getMessage();
		} catch (NullPointerException e) {
			System.out.println("no Message: "+e);
		}
		try {
			this.id = this.post.getId();
		} catch (NullPointerException e) {
			System.out.println("ID error: "+e);
		}
		
		try {
			this.Description = this.post.getDescription();
		} catch (NullPointerException e) {
			System.out.println("no Description: "+e);
		}
		try {
			this.Picture = this.post.getPicture();
		} catch (NullPointerException e) {
			System.out.println("no Picture: "+e);
		}
		try {
			this.id = this.post.getId();
		} catch (NullPointerException e) {
			System.out.println("ID error: "+e);
		}
		
		
		
		try {
			this.LikeCount = this.post.getLikes().getTotalCount();
		} catch (NullPointerException e) {
			System.out.println("no Likes Record: "+e);
		}
		try {
			this.Comments = this.post.getComments();
		} catch (NullPointerException e) {
			System.out.println("no Comments: "+e);
		}	
		
		try {
			this.CommentCount = this.post.getCommentsCount();
		} catch (NullPointerException e) {
			System.out.println("no CommentsCount: "+e);
		}			
		
		
		try {
			this.AttachmentTitle = this.post.getAttachments().getData().get(0).getTitle();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Title: "+e);
		}
		try {
			this.AttachmentUrl = this.post.getAttachments().getData().get(0).getUrl();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Url: "+e);
		}
		
		try {
			this.AttachmentDescription = this.post.getAttachments().getData().get(0).getDescription();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Description: "+e);
		}
		try {
			this.AttachmentMedia = this.post.getAttachments().getData().get(0).getMedia();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Media: "+e);
		}
		
		
		
		//System.out.println(date.toString());
	}




//	@Override
//	public String toString() {
//		String out;
//
//		
//		try {
//			out = "Post[ Id: "+post.getId()+" Date: "+this.date+
//				";\n Message:"+post.getMessage();
//			try {
//				out +=   "\n   Data URL: "+post.getAttachments().getData().get(0).getUrl()+
//			    		 "\n   Data Title: "+post.getAttachments().getData().get(0).getTitle()+
//			    		 "\n   Data Description: "+post.getAttachments().getData().get(0).getDescription()+
//			    		 "\n   Data Media: "+post.getAttachments().getData().get(0).getMedia();
//			} catch (NullPointerException e) {
//				System.out.println("No attachments found: "+e);
//				
//			}
//			out+= ";\n Description:"+post.getDescription()+
//			"\n Likes:"+post.getLikes().getTotalCount()+
//    		"\n Comments ("+post.getCommentsCount()+"): "+post.getComments()+"]";
//					
//		} catch (Exception e) {
//			System.out.println(e);
//			out = post.toString();
//		}
//		return out;
//	}


	public Post getPost() {
		return post;
	}





	@Override
	public String toString() {
		return "FacebookEntry [" +  ", date=" + date + ", AttachmentTitle=" + AttachmentTitle
				+ ", AttachmentUrl=" + AttachmentUrl + ", AttachmentDescription=" + AttachmentDescription
				+ ", AttachmentMedia=" + AttachmentMedia + ", Message=" + Message + ", id=" + id + ", Description="
				+ Description + ", Picture=" + Picture + ", LikeCount=" + LikeCount + ", Comments=" + Comments
				+ ", CommentCount=" + CommentCount + "]";
	}




	public String getAttachmentTitle() {
		return AttachmentTitle;
	}




	public String getAttachmentUrl() {
		return AttachmentUrl;
	}




	public String getAttachmentDescription() {
		return AttachmentDescription;
	}




	public Media getAttachmentMedia() {
		return AttachmentMedia;
	}




	public String getMessage() {
		return Message;
	}




	public String getId() {
		return id;
	}




	public String getDescription() {
		return Description;
	}




	public String getPicture() {
		return Picture;
	}




	public Long getLikeCount() {
		return LikeCount;
	}




	public Comments getComments() {
		return Comments;
	}




	public Object getCommentCount() {
		return CommentCount;
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
