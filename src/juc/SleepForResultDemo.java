package juc;

public class SleepForResultDemo implements Runnable {
	static boolean result = false; 
	static void sleepWhile(long ms) {
		try { Thread.sleep(ms); }
		catch (Exception e) {} }
	public void run() {
		//do work 
		System.out.println("Hello, sleep a while.");
		sleepWhile(2000L); result = true; } 
	public static void main(String[] args) {
		SleepForResultDemo demo = new SleepForResultDemo(); 
		Thread t = new Thread(demo); 
		t.start();
		sleepWhile(3000L);
		System.out.println(result); }
}

