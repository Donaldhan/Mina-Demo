package juc.automic;


/**
 * Lock和synchronized的性能比较测试
 * @author donald
 * 2017年2月28日
 * 下午7:53:04
 */
public class LockCompareSynchronize {
	static int staticValue = 0;

	public static void main(String[] args) throws Exception {
		final int max = 10;
		final int loopCount = 100000;
		long costTime = 0;
		//测试Lock性能
		for (int m = 0; m < max; m++) {
			long start0 = System.nanoTime();
			final AtomicIntegerWithLock value1 = new AtomicIntegerWithLock(0);
			Thread[] ts = new Thread[max];
			for (int i = 0; i < max; i++) {
				ts[i] = new Thread() {
					public void run() {
						for (int i = 0; i < loopCount; i++) {
							value1.incrementAndGet();
						}
					}
				};
			}
			for (Thread t : ts) {
				t.start();
			}
			for (Thread t : ts) {
				t.join();
			}
			long end0 = System.nanoTime();
			costTime += (end0 - start0);
		}
		System.out.println("Lock所耗时间: " + (costTime));
		System.out.println();
		costTime = 0;
		//测试synchronized性能
		final Object lock = new Object();
		for (int m = 0; m < max; m++) {
			staticValue = 0;
			long start1 = System.nanoTime();
			Thread[] ts = new Thread[max];
			for (int i = 0; i < max; i++) {
				ts[i] = new Thread() {
					public void run() {
						for (int i = 0; i < loopCount; i++) {
							synchronized (lock) {
								++staticValue;
							}
						}
					}
				};
			}
			for (Thread t : ts) {
				t.start();
			}
			for (Thread t : ts) {
				t.join();
			}
			long end1 = System.nanoTime();
			costTime += (end1 - start1);
		}
		System.out.println("synchronized所耗时间: " + (costTime));
	}

}
