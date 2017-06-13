package juc.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author donald 2017年3月29日 下午9:20:15
 */
public class TestExecutor {
	public static void main(String[] args) {
		// ExecutorService executorService = Executors.newCachedThreadPool();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		TestRunnable test1 = new TestRunnable("one");
		TestRunnable test2 = new TestRunnable("two");
		executorService.submit(test1);
		executorService.submit(test2);
		executorService.shutdown();
		/*
		 * if(executorService.isShutdown()){
		 * System.out.println("线程池关闭................."); }
		 */
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("等待线程池关闭.................");
		}
		/*
		 * if(executorService.isTerminated()){
		 * System.out.println("线程池关闭................."); } else{
		 * System.out.println("等待线程池关闭................."); }
		 */
	}

}
