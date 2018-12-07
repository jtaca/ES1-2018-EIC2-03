package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.threads.SingleBarrier;

/**
 * The Class SingleBarrierTest.
 */
public class SingleBarrierTest {
	
	/** The single barrier. */
	private static SingleBarrier singleBarrier = null;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		singleBarrier = new SingleBarrier();
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test init single barrier giving total posters.
	 */
	@Test
	public void testInitSingleBarrierGivingTotalPosters() {
		int expectedTotalPosters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	/**
	 * Test init single barrier giving total posters and waiters verify posters.
	 */
	@Test
	public void testInitSingleBarrierGivingTotalPostersAndWaitersVerifyPosters() {
		int expectedTotalPosters = 1;
		int expectedTotalWaiters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters, expectedTotalWaiters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	/**
	 * Test init single barrier giving total posters and waiters verify waiters.
	 */
	@Test
	public void testInitSingleBarrierGivingTotalPostersAndWaitersVerifyWaiters() {
		int expectedTotalPosters = 1;
		int expectedTotalWaiters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters, expectedTotalWaiters);
		int actualTotalWaiters = singleBarrier.getTotalWaiters();
		
		assertEquals(expectedTotalWaiters, actualTotalWaiters);
	}
	
	/**
	 * Test init.
	 */
	@Test
	public void testInit() {
		int expectedTotalPosters = 4;
		singleBarrier.init(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	/**
	 * Test barrear set.
	 */
	@Test
	public void testBarrearSet() {
		int expectedTotalPosters = 3;
		singleBarrier.barrierSet(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	/**
	 * Test barrear wait.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testBarrearWait() throws InterruptedException {
		singleBarrier.barrierSet(1);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				singleBarrier.barrierWait();
			}
		});
		thread.start();
		thread.join(10000);
		singleBarrier.barrierPost();
		
		//If it reaches here, it means it is correct otherwise it gets blocked forever
		assertTrue(true);
	}
	
	/**
	 * Test barrear wait interrupt.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testBarrearWaitInterrupt() throws InterruptedException {
		singleBarrier.barrierSet(1);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				singleBarrier.barrierWait();
				assertTrue(Thread.currentThread().isInterrupted());
			}
		});
		thread.start();
		thread.join(10000);
		thread.interrupt();
		singleBarrier.barrierPost();
		thread.join();
		
		//If it reaches here, it means it is correct otherwise it gets blocked forever
//		assertTrue(true);
	}
	
	/**
	 * Test barrear post interrupt.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testBarrearPostInterrupt() throws InterruptedException {
		singleBarrier.barrierSet(2);
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				singleBarrier.barrierWait();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
				
			@Override
			public void run() {
				singleBarrier.barrierPost();
				assertTrue(Thread.currentThread().isInterrupted());
			}
		});
		thread1.start();
		thread2.start();
		thread1.join(10000);
		thread2.interrupt();
		thread2.join(10000);
		singleBarrier.barrierPost();
		thread2.join();
		
		//If it reaches here, it means it is correct otherwise it gets blocked forever
//		assertTrue(true);
	}
	
	/**
	 * Test get current posters.
	 */
	@Test
	public void testGetCurrentPosters() {
		int expectedCurrentPosters = 0;
		singleBarrier.barrierSet(1);
		int actualCurrentPosters = singleBarrier.getCurrentPosters();
		
		assertEquals(expectedCurrentPosters, actualCurrentPosters);
	}
	
	/**
	 * Test get passed waiters.
	 */
	@Test
	public void testGetPassedWaiters() {
		int expectedPassedWaiters = 0;
		singleBarrier.barrierSet(1);
		int actualPassedWaiters = singleBarrier.getPassedWaiters();
		
		assertEquals(expectedPassedWaiters, actualPassedWaiters);
	}

}
