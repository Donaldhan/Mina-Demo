package juc.latch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * 测试CyclicBarrier
 * @author donald
 * 2017年3月6日
 * 下午12:52:10
 */
public class TestCyclicBarrier {
	final CyclicBarrier barrier;//屏障锁
	final int MAX_TASK;//屏障锁可共享数
	/**
	 * 
	 * @param cnt
	 */
	public TestCyclicBarrier(int cnt) {
		//主线程也需要锁一次，所以cnt + 1，主线程等待一组10个线程执行完，
		//同时主线程释放锁，达到屏障点,执行下一组线程
		barrier = new CyclicBarrier(cnt + 1);
		//每一次，可运行的任务锁
		MAX_TASK = cnt;
	}

	public void doWork(final Runnable work) {
		new Thread() {
			public void run() {
				work.run();
				try {
					//释放屏障共享锁
					int index = barrier.await();
					//检查进度
					doWithIndex(index);
				} catch (InterruptedException e) {
					return;
				} catch (BrokenBarrierException e) {
					return;
				}
			}
		}.start();
	}
   /**
    * 检查线程组完成进度，根据不通过的进度，可以做一些通知工作
    * @param index
    */
	private void doWithIndex(int index) {
		if (index == MAX_TASK / 2) {
			System.out.println("Left 50%");
		} 
		if (index == 0) {
			System.out.println("run over");
		}
	}

	public void waitForNext() {
		try {
			//主线程释放屏障共享锁，检查进度
			doWithIndex(barrier.await());
		} catch (InterruptedException e) {
			return;
		} catch (BrokenBarrierException e) {
			return;
		}
	}

	public static void main(String[] args) {
		final int count = 10;
		TestCyclicBarrier demo = new TestCyclicBarrier(count);
		for (int i = 0; i < 100; i++) {
			demo.doWork(new Runnable() {
				public void run() {
					// do something
					try {
						Thread.sleep(1000L);
					} catch (Exception e) {
						return;
					}
				}
			});
			if ((i + 1) % count == 0) {
				/*
				 每10个线程一组，或者锁一个屏障点，当每组的10个线程，都完成时， 才执行下一组线程
				 */
				demo.waitForNext();
			}
		}
	}

}
