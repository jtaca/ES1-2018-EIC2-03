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
	private String profileImageUrl;
	private String Author; 
	
	
	
	
	
	/**
	 * Constructor
	 */
	public FacebookEntry(Post post, Date date, String profileImageUrl, String Author) {
		this.post = post;
		//this.date = date; 
		this.date = date;
		this.profileImageUrl = profileImageUrl;
		this.Author = Author;
		try {
			this.Message = this.post.getMessage();
		} catch (NullPointerException e) {
			System.out.println("no Message: "+e);
			this.Message = "";
		}
		try {
			this.id = this.post.getId();
		} catch (NullPointerException e) {
			System.out.println("ID error: "+e);
			this.id = "";
		}
		
		try {
			this.Description = this.post.getDescription();
		} catch (Exception e) {
			System.out.println("no Description: "+e);
			this.Description = "";
		}
		try {
			this.Picture = this.post.getPicture();
		} catch (NullPointerException e) {
			System.out.println("no Picture: "+e);
			this.Picture = "";
		}

		
		
		
		try {
			this.LikeCount = this.post.getLikes().getTotalCount();
		} catch (NullPointerException e) {
			System.out.println("no Likes Record: "+e);
			this.LikeCount = (long) 0;
		}
		try {
			this.Comments = this.post.getComments();
		} catch (NullPointerException e) {
			System.out.println("no Comments: "+e);
			this.Comments = new Comments();
		}	
		
		try {
			this.CommentCount = this.post.getCommentsCount();
		} catch (NullPointerException e) {
			System.out.println("no CommentsCount: "+e);
			this.CommentCount = 0;
		}			
		
		
		try {
			this.AttachmentTitle = this.post.getAttachments().getData().get(0).getTitle();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Title: "+e);
			this.AttachmentTitle = "";
		}
		try {
			this.AttachmentUrl = this.post.getAttachments().getData().get(0).getUrl();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Url: "+e);
			this.AttachmentUrl ="";
		}
		
		try {
			this.AttachmentDescription = this.post.getAttachments().getData().get(0).getDescription();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Description: "+e);
			this.AttachmentDescription ="";
		}
		try {
			this.AttachmentMedia = this.post.getAttachments().getData().get(0).getMedia();
		} catch (NullPointerException e) {
			System.out.println("no Attachments Media: "+e);
			this.AttachmentMedia =new Media();
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

	/**
	 * getter for the post (it contains most of the information needed)
	 */
	public Post getPost() {
		return post;
	}




	/**
	 * getter for the profile image of the page
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	


	/**
	 * getter for the post author
	 */
	public String getAuthor() {
		return Author;
	}




	/**
	 * Shows the attributes and their content
	 */
	@Override
	public String toString() {
		return "FacebookEntry [" +  "date=" + date + ", Author=" + Author +", AttachmentTitle=" + AttachmentTitle
				+ ", AttachmentUrl=" + AttachmentUrl + ", AttachmentDescription=" + AttachmentDescription
				+ ", AttachmentMedia=" + AttachmentMedia + ", Message=" + Message + ", id=" + id + ", Description="
				+ Description + ", Picture=" + Picture + ", LikeCount=" + LikeCount + ", Comments=" + Comments
				+ ", CommentCount=" + CommentCount + ", ProfileImageUrl= "+this.profileImageUrl+"]";
	}



	/**
	 * getter of the title on the Attachments if there are any
	 */
	public String getAttachmentTitle() {
		return AttachmentTitle;
	}



	/**
	 * getter of the Attachments if there are any
	 */
	public String getAttachmentUrl() {
		return AttachmentUrl;
	}



	/**
	 * getter of the description on the Attachments if there are any
	 */
	public String getAttachmentDescription() {
		return AttachmentDescription;
	}



	/**
	 * getter of the media on the Attachments if there are any
	 */
	public Media getAttachmentMedia() {
		return AttachmentMedia;
	}



	/**
	 * getter of the message on the post
	 */
	public String getMessage() {
		return Message;
	}



	/**
	 * getter of the id of the post
	 */
	public String getId() {
		return id;
	}



	/**
	 * getter of the description on the post
	 */
	public String getDescription() {
		return this.Description;
	}



	/**
	 * getter of the picture on the post
	 */
	public String getPicture() {
		return this.Picture;
	}



	/**
	 * getter of the number of likes on the post
	 */
	public Long getLikeCount() {
		return LikeCount;
	}



	/**
	 * getter of the comments on the post
	 */
	public Comments getComments() {
		return Comments;
	}



	/**
	 * getter of the number of comments on the post
	 */
	public Object getCommentCount() {
		return CommentCount;
	}



	/**
	 * getter of the date on the post
	 */
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
