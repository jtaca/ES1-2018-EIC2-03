package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.threads.SingleBarrier;

public class SingleBarrierTest {
	
	private static SingleBarrier singleBarrier = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		singleBarrier = new SingleBarrier();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitSingleBarrierGivingTotalPosters() {
		int expectedTotalPosters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	@Test
	public void testInitSingleBarrierGivingTotalPostersAndWaitersVerifyPosters() {
		int expectedTotalPosters = 1;
		int expectedTotalWaiters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters, expectedTotalWaiters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	@Test
	public void testInitSingleBarrierGivingTotalPostersAndWaitersVerifyWaiters() {
		int expectedTotalPosters = 1;
		int expectedTotalWaiters = 1;
		SingleBarrier singleBarrier = new SingleBarrier(expectedTotalPosters, expectedTotalWaiters);
		int actualTotalWaiters = singleBarrier.getTotalWaiters();
		
		assertEquals(expectedTotalWaiters, actualTotalWaiters);
	}
	
	@Test
	public void testInit() {
		int expectedTotalPosters = 4;
		singleBarrier.init(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
	@Test
	public void testBarrearSet() {
		int expectedTotalPosters = 3;
		singleBarrier.barrierSet(expectedTotalPosters);
		int actualTotalPosters = singleBarrier.getTotalPosters();
		
		assertEquals(expectedTotalPosters, actualTotalPosters);
	}
	
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
	
	@Test
	public void testGetCurrentPosters() {
		int expectedCurrentPosters = 0;
		singleBarrier.barrierSet(1);
		int actualCurrentPosters = singleBarrier.getCurrentPosters();
		
		assertEquals(expectedCurrentPosters, actualCurrentPosters);
	}
	
	@Test
	public void testGetPassedWaiters() {
		int expectedPassedWaiters = 0;
		singleBarrier.barrierSet(1);
		int actualPassedWaiters = singleBarrier.getPassedWaiters();
		
		assertEquals(expectedPassedWaiters, actualPassedWaiters);
	}

}
