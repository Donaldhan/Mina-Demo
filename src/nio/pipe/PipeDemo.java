package nio.pipe;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PipeDemo
 * @author donald
 * 2017年4月13日
 * 上午9:27:12
 */
public class PipeDemo {
	public static void main(String[] args) {
		// 创建一个管道
		Pipe pipe = null;
		try {
			pipe = Pipe.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.submit(new PipeSink(pipe.sink()));
		exec.submit(new PipeSource(pipe.source()));
	}
}
