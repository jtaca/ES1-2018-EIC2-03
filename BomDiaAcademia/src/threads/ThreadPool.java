package threads;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
	
	private boolean threadsWorking = false;
	
	private static final int MINIMUM_NUMBER_OF_THREADS = 10;
	private int numberOfThreads;
	private List<Worker> threads;
	
	private static final ThreadPool INSTANCE = new ThreadPool();
	
	private ThreadPool() {
		threads = new ArrayList<Worker>();
		numberOfThreads = Runtime.getRuntime().availableProcessors();
		
		System.out.println("Number of cores: " + numberOfThreads);
		numberOfThreads = Math.max(MINIMUM_NUMBER_OF_THREADS, numberOfThreads);
		System.out.println("Number of threads: " + numberOfThreads);
		
	}
	
	public static ThreadPool getInstance() {
		return INSTANCE;
	}
	
	public void stopThreads() {
		if(threadsWorking == true) {
			for(Worker w : threads) {
				w.stopWorking();
				w.interrupt();
				threads.remove(w);
			}
			threadsWorking = false;
		}
	}
	
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
