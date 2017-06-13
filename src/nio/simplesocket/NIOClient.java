package nio.simplesocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Client
 * @author donald
 * 2017年4月11日
 * 下午9:24:09
 */
public class NIOClient {
	//manager the channel
	private Selector selector;
	/**
	 * stat Client
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		NIOClient client = new NIOClient();
		client.initClient("10.16.7.107",10000);
		client.listen();
	}
	/**
	 * get the Socket and finish some initial work
	 * @param ip Server ip
	 * @param port connect Server port
	 * @throws IOException
	 */
	public void initClient(String ip,int port) throws IOException{
		//get the Socket
		SocketChannel channel = SocketChannel.open();
		// set no blocking mode
		channel.configureBlocking(false);
		//connect the Server
		channel.connect(new InetSocketAddress(ip,port));
		//get the channel manager
		this.selector = Selector.open();
		//Register the channel to manager and bind the event
		channel.register(selector,SelectionKey.OP_CONNECT);
		}
	/**
	 * use asking mode to listen the event of selector
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void listen() throws IOException{
		System.out.println("===========The Client is start!===========");
		while(true){
			selector.select();
			Iterator ite =  this.selector.selectedKeys().iterator();
			while(ite.hasNext()){
				SelectionKey key = (SelectionKey)ite.next();
				ite.remove();
				if(key.isConnectable()){
					SocketChannel channel = (SocketChannel)key.channel();
                    //during connecting, finish the connect
                    if(channel.isConnectionPending()){
                    	channel.finishConnect();
                    }
					channel.configureBlocking(false);
					channel.write(ByteBuffer.wrap(new String("Hello Server!").getBytes()));
					channel.register(this.selector, SelectionKey.OP_READ);
				}
				else if (key.isReadable()) read(key);
			}
			
		}
	}
	/**
	 * deal with the message come from the server
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(100);
		channel.read(buf);
		byte[] data = buf.array();
		String msg = new String(data).trim();
		System.out.println("message come from server:"+msg);
	}
	
}
