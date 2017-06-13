package juc.executor;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池拒绝执行任务策略
 * 
 * @author donald 2017年3月24日 上午8:56:07
 */
public class TestRejectedExecutionHandler {
	/**
	 * 
	 * @param msg
	 */
	static void log(String msg) {
		System.out.println("time："+System.currentTimeMillis() + " -> " + msg);
	}

	public static void main(String[] args) throws Exception {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(1));
		//直接丢弃
//		 pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		//丢弃old线程任务
//		 pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		//抛出异常
//		 pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		//调用线程执行多余任务
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 10; i++) {
			final int index = i;
			pool.submit(new Runnable() {
				public void run() {
					log("run task:" + index + " -> " + Thread.currentThread().getName());
					try {
						Thread.sleep(1000L);
					} catch (Exception e) {
						e.printStackTrace();
					}
					log("run over:" + index + " -> " + Thread.currentThread().getName());
				}
			});
		}
		log("==========before sleep");
		Thread.sleep(4000L);
		log("==========before shutdown()");
		pool.shutdown();
		log("==========after shutdown(),pool.isTerminated=" + pool.isTerminated());
		pool.awaitTermination(1000L, TimeUnit.SECONDS);
		log("==========now,pool.isTerminated=" + pool.isTerminated());
	}
	
}
