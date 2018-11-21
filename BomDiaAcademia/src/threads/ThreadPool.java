package threads;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ThreadPool.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class ThreadPool { //
	
	/** The threads working. */
	private boolean threadsWorking = false;
	
	/** The Constant MINIMUM_NUMBER_OF_THREADS. */
	private static final int MINIMUM_NUMBER_OF_THREADS = 10;
	
	/** The number of threads. */
	private int numberOfThreads;
	
	/** The threads. */
	private List<Worker> threads;
	
	/** The Constant INSTANCE. */
	private static final ThreadPool INSTANCE = new ThreadPool();
	
	/**
	 * Instantiates a new thread pool.
	 */
	private ThreadPool() {
		threads = new ArrayList<Worker>();
		numberOfThreads = Runtime.getRuntime().availableProcessors();
		
		System.out.println("Number of cores: " + numberOfThreads);
		numberOfThreads = Math.max(MINIMUM_NUMBER_OF_THREADS, numberOfThreads);
		System.out.println("Number of threads: " + numberOfThreads);
		
	}
	
	/**
	 * Gets the single instance of ThreadPool.
	 *
	 * @return single instance of ThreadPool
	 */
	public static ThreadPool getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Stop threads.
	 */
	public void stopThreads() {
		if(threadsWorking == true) {
			for(Worker w : threads) {
				w.stopWorking();
				w.interrupt();
			}
			threads.clear();
			threadsWorking = false;
		}
	}
	
	/**
	 * Start threads.
	 */
	public void startThreads() { // require to be called for the threads to be created and start.
		if(threadsWorking == false) {
			Worker worker;
			for(int i = 0 ; i < numberOfThreads ; i++) {
				worker = new Worker();
				threads.add(worker);
				worker.start();
			}
			threadsWorking = true;
		}
	}
	
	

}
