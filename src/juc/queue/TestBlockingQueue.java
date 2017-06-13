package juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 生产消费者，模式
 * @author donald
 * 2017年3月6日
 * 下午9:43:50
 */
public class TestBlockingQueue {
	final int workerNumber;
	final Worker[] workers;
	final ExecutorService threadPool;
	static volatile boolean running = true;
	/**
	 * 
	 * @param workerNumber
	 * @param capacity
	 */
	public TestBlockingQueue(int workerNumber, int capacity) {
		if (workerNumber <= 0)
			throw new IllegalArgumentException();
		this.workerNumber = workerNumber;
		workers = new Worker[workerNumber];
		for (int i = 0; i < workerNumber; i++) {
			workers[i] = new Worker(capacity);
		}
		//
		boolean b = running;
		// kill the resorting
		threadPool = Executors.newFixedThreadPool(workerNumber);
		for (Worker w : workers) {
			threadPool.submit(w);
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	Worker getWorker(int id) {
		return workers[id % workerNumber];
	}
	/**
	 * 
	 * @author donald
	 * 2017年3月18日
	 * 上午10:59:23
	 */
	class Worker implements Runnable {
		final BlockingQueue<Integer> queue;
		/**
		 * 
		 * @param capacity
		 */
		public Worker(int capacity) {
			queue = new LinkedBlockingQueue<Integer>(capacity);
		}
		/**
		 * 
		 */
		public void run() {
			while (true) {
				try {
					consume(queue.take());
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		/**
		 * 
		 * @param id
		 */
		void put(int id) {
			try {
				queue.put(id);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	/**
	 * 
	 * @param id
	 */
	public void accept(int id) {
		// accept client request
		getWorker(id).put(id);
	}
	/**
	 * 
	 * @param id
	 */
	protected void consume(int id) {
		// do the work
		// get the list of friends and save the birthday to cache
	}
}
