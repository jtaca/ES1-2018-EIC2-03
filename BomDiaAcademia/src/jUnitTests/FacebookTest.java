package jUnitTests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.restfb.types.GraphResponse;

import entry_objects.InformationEntry;
import facebook.FacebookConnection;
import other.Service;
import entry_objects.FacebookEntry;

public class FacebookTest {
	List<InformationEntry> in;
	FacebookEntry testEntry;

	@Test
	public final void testGetInstance() {
		try {
			assertNotNull(FacebookConnection.getInstance());
		} catch (Exception e) {
			fail("Not expected: "+e);
		}
		
	}


	@Test
	public final void testExtendAccessToken() {
		try {
			FacebookConnection.ExtendAccessToken("2128274727202894", "5b08263178f3db9cbd189e2100f0ee54");
			FacebookConnection.ExtendAccessToken(null, null);
			FacebookConnection.ExtendAccessToken("null", "null");
		} catch (Exception e) {
			fail("Not expected: "+e);
		}
		
	}

	@Test
	public final void testRequestFacebook() {
		try {
			in = FacebookConnection.requestFacebook("me/feed");
			FacebookConnection.requestFacebook("me");
			FacebookConnection.requestFacebook("null");
			FacebookConnection.requestFacebook(null);
			
			
		} catch (Exception e) {
			fail("Not expected: "+e);
		}
		testEntry = ((FacebookEntry) in.get(0));
		assertNotNull(in);
		assertNotNull(testEntry.getPost());
		assertNotNull(testEntry.getAttachmentDescription());
		assertNotNull(testEntry.getAttachmentMedia());
		assertNotNull(testEntry.getAttachmentTitle());
		assertNotNull(testEntry.getAttachmentUrl());
		assertNotNull(testEntry.getAuthor());
		assertNotNull(testEntry.getCommentCount());
		assertNotNull(testEntry.getComments());
		assertNotNull(testEntry.getDate());
		//assertNotNull(testEntry.getDescription());
		assertNotNull(testEntry.getId());
		assertNotNull(testEntry.getLikeCount());
		assertNotNull(testEntry.getMessage());
		//assertNotNull(testEntry.getPicture());
		assertNotNull(testEntry.getProfileImageUrl());
		assertNotNull(testEntry.getService());
		assertNotNull(testEntry.toString());
		try {
			FacebookEntry testEntry2 = new FacebookEntry(null, null, null, null);
		} catch (Exception e) {
			fail("null in constructor: "+e);
		}
		
		
	}

	@Test
	public final void testLike() {
		try {
			in = FacebookConnection.requestFacebook("me/feed");
			testEntry = ((FacebookEntry) in.get(0));
			System.out.println(testEntry.getId());
			FacebookConnection.like(testEntry.getId());
			FacebookConnection.like("0");
			FacebookConnection.like("erfiwbf");
			FacebookConnection.like(null);
		} catch (Exception e) {
			fail("Not anticipated: "+e);
		}
		
		
	}

	@Test
	public final void testPost() {
		try {
			Random rand = new Random();
			int n = rand.nextInt(50) + 1;
			GraphResponse b = FacebookConnection.post("Random Number: "+ n );
			assertNotNull(b);
			
			FacebookConnection.post("SameText");
			FacebookConnection.post(null);
			
		} catch (Exception e) {
			fail();
		}
		
		
	}

	@Test
	public final void testCommentOnPost() {
		try {
			in = FacebookConnection.requestFacebook("me/feed");
			testEntry = ((FacebookEntry) in.get(0));
			
			FacebookConnection.commentOnPost(testEntry.getId(),"Great comment");
			FacebookConnection.commentOnPost("bwilq", null);
			FacebookConnection.commentOnPost(null,"Great comment");
			FacebookConnection.commentOnPost(null, null);
		} catch (Exception e) {
			fail();
		}
		
		
		
		
	}

	@Test
	public final void testGetAccessToken2() {
		assertNotNull(FacebookConnection.getAccessToken2());
	}


	@Test
	public final void testGetService() {
		assertNotNull(FacebookConnection.getInstance().getService());
		assertEquals(Service.FACEBOOK, FacebookConnection.getInstance().getService());
	}

}
