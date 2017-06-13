package juc;

public class SleepLoopForResultDemo implements Runnable {
	boolean result = false; 
	volatile boolean finished = false;
	static void sleepWhile(long ms) {
		try { Thread.sleep(ms); } 
		catch (Exception e) {} }public void run() { 
			//do work 
			try{
				System.out.println("Hello, sleep a while."); 
				sleepWhile(2000L); result = true; } 
			finally { finished = true; } }
		public static void main(String[] args) { 
			SleepLoopForResultDemo demo = new SleepLoopForResultDemo();
			Thread t = new Thread(demo); 
			t.start(); 
			while (!demo.finished) { 
				sleepWhile(10L); }
			System.out.println(demo.result); 
//			SleepLoopForResultDemo demo = new SleepLoopForResultDemo(); 
//			Thread t = new Thread(demo);
//			t.start();
//			t.join(); 
//			System.out.println(demo.result);
		}
}
