package juc.latch;

import java.util.concurrent.CountDownLatch;
/**
 * 
 * @author donald
 * 2017年3月6日
 * 下午12:52:49
 */
public class PerformanceTool {
	public long timecost(final int times, final Runnable task) throws InterruptedException {
		if (times <= 0)
			throw new IllegalArgumentException();
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch overLatch = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						startLatch.await();
						task.run();
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					} finally {
						overLatch.countDown();
					}
				}
			}).start();
		}
		//
		long start = System.nanoTime();
		startLatch.countDown();
		overLatch.await();
		return System.nanoTime() - start;
	}

}
