package nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * SinkChannel
 * @author donald
 * 2017年4月13日
 * 上午9:26:49
 */
public class PipeSink implements Runnable {
	private Pipe.SinkChannel sinkChannel;
	
	public PipeSink(Pipe.SinkChannel sinkChannel) {
		this.sinkChannel = sinkChannel;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		System.out.println("=========The sink is start!===========");
		try {
			sinkChannel.write(ByteBuffer.wrap(new String("Hello source!").getBytes("UTF-8")));
			System.out.println("send message to source is done...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
