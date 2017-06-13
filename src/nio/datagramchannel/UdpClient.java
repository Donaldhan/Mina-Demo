package nio.datagramchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Client
 * @author donald
 * 2017年4月11日
 * 下午9:24:09
 */
public class UdpClient {
	//manager the channel
	private Selector selector;
	/**
	 * stat Client
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		UdpClient client = new UdpClient();
		client.initClient("10.16.7.107",10001);
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
		DatagramChannel channel = DatagramChannel.open();
		// set no blocking mode
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(ip,port));
		//get the channel manager
		this.selector = Selector.open();
		//Register the channel to manager and bind the event
		channel.register(selector,SelectionKey.OP_READ);
		channel.connect(new InetSocketAddress("10.16.7.107", 10000));
		while(!channel.isConnected()){
			//空转等待连接
		}
		channel.write(ByteBuffer.wrap(new String("Hello Server!").getBytes()));
		System.out.println("client send message to server is done!");
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
				if (key.isReadable()) read(key);
			}
			
		}
	}
	/**
	 * deal with the message come from the server
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
		DatagramChannel channel = (DatagramChannel) key.channel();
		System.out.println("is Connected："+channel.isConnected());
		ByteBuffer buf = ByteBuffer.allocate(100);
		InetSocketAddress socketAddress = (InetSocketAddress) channel.getRemoteAddress(); 
		System.out.println("server ip and port:"+socketAddress.getHostString()+","+socketAddress.getPort());
		channel.read(buf); 
		byte[] data = buf.array();
		String msg = new String(data).trim();
		System.out.println("message come from server:"+msg);
		channel.close();
	}
	
}
