package juc.executor;

public class TestRunnable implements Runnable {
	private String name;

	public TestRunnable(String name) {
		super();
		this.name = name;
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			 try {
					Thread.sleep(1000);
					System.out.println("线程"+name+"：is running times,"+(i+1)+".....");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

}
