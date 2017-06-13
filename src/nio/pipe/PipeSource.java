package nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * SourceChannel
 * @author donald
 * 2017年4月13日
 * 上午8:56:17
 */
public class PipeSource implements Runnable {
	private Selector selector;
	private  Pipe.SourceChannel sourceChannel;

	public PipeSource(Pipe.SourceChannel sourceChannel) {
		this.sourceChannel = sourceChannel;
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void init() throws IOException{
		sourceChannel.configureBlocking(false);
		this.selector = Selector.open();
		sourceChannel.register(selector, SelectionKey.OP_READ);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		System.out.println("=========The source is start!===========");
		try{
			while(true){
				selector.select();
				Iterator ite =  this.selector.selectedKeys().iterator();
				while(ite.hasNext()){
					SelectionKey key = (SelectionKey)ite.next();
					ite.remove();
					if (key.isReadable()) read(key);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void read(SelectionKey key) throws IOException{
		Pipe.SourceChannel channel = (Pipe.SourceChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(100);
		channel.read(buf);
		byte[] data = buf.array();
		String msg = new String(data,"UTF-8").trim();
		System.out.println("message come from sink:"+msg);
	}
}
