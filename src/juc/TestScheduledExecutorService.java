package juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 测试ScheduledExecutorService
 * @author donald
 * 2017年3月28日
 * 下午9:37:21
 */
public class TestScheduledExecutorService {
	public static void main(String[] args) throws Exception {
		ScheduledExecutorService execService = Executors.newScheduledThreadPool(3);
		//以固定的时间间隔，执行任务，无论上一个任务是否执行完
//		execService.scheduleAtFixedRate(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName() + " -> " + System.currentTimeMillis());
//				try {
//					Thread.sleep(2000L);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}, 1, 1, TimeUnit.SECONDS);
		//执行任务和下一个任务的执行时间延时相等，两个任务执行的间隔相同
		execService.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " -> " + System.currentTimeMillis());
				try {
					Thread.sleep(2000L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 1, TimeUnit.SECONDS);
//		Thread.sleep(5000L);
//		execService.shutdown();
	}

}
