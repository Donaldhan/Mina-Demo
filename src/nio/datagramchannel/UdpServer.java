package nio.datagramchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Sever
 * @author donald
 * 2017年4月11日
 * 下午9:24:03
 */
public class UdpServer {
	//manager the channel
	private Selector selector;
	/**
	 * stat Server
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		UdpServer server = new UdpServer();
		server.initServer("10.16.7.107", 10000);
		server.listen();
	}
	/**
	 * get the ServerSocket and finish some initial work
	 * @param port
	 * @throws IOException
	 */
	public void initServer(String host, int port) throws IOException{
		//get the ServerSocket
		DatagramChannel serverChannel = DatagramChannel.open();
		// set no blocking mode
		serverChannel.configureBlocking(false);
		//bind the port
		serverChannel.socket().bind(new InetSocketAddress(host, port));
		//get the channel manager
		this.selector = Selector.open();
		//Register the channel to manager and bind the event
		serverChannel.register(selector,SelectionKey.OP_READ);
		serverChannel.connect(new InetSocketAddress("10.16.7.107", 10001));
		while(!serverChannel.isConnected()){
			//空转等待连接
		}
	}
	/**
	 * use asking mode to listen the event of selector
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void listen() throws IOException{
		System.out.println("=========The Server is start!===========");
		while(true){
			selector.select();
			Iterator ite =  this.selector.selectedKeys().iterator();
			while(ite.hasNext()){
				SelectionKey key = (SelectionKey)ite.next();
				ite.remove();
				if (key.isReadable()) read(key);
			}
			
		}
	}
	/**
	 * deal with the message come from the client
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
		DatagramChannel channel = (DatagramChannel) key.channel();
		System.out.println("is Connected："+channel.isConnected());
		ByteBuffer buf = ByteBuffer.allocate(100);
		InetSocketAddress socketAddress = (InetSocketAddress) channel.getRemoteAddress(); 
		System.out.println("client ip and port:"+socketAddress.getHostString()+","+socketAddress.getPort());
		channel.read(buf);
		byte[] data = buf.array();
		String msg = new String(data).trim();
		System.out.println("message come from client:"+msg);
		channel.write(ByteBuffer.wrap(new String("Hello client!").getBytes()));
		channel.close();
	}
	
}
