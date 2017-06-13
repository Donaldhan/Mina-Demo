package nio.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import socket.ProtocolConstants;

/**
 * Server
 * @author donald
 * 2017年4月13日
 * 下午11:14:28
 */
public class NIOServerCalculateX {
	private static final String HOST = "10.16.7.107";
	private static final int PORT = 10000;
	private static ExecutorService exec= null;
	static {
		exec = Executors.newFixedThreadPool(2);
	}
	
	//manager the channel
	private Selector selector;
	/**
	 * stat Server
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		NIOServerCalculateX server = new NIOServerCalculateX();
		server.initServer(HOST,PORT);
		server.listen();
	}
	/**
	 * get the ServerSocket and finish some initial work
	 * @param port
	 * @throws IOException
	 */
	public void initServer(String host, int port) throws IOException{
		//get the ServerSocket
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// set no blocking mode
		serverChannel.configureBlocking(false);
		//bind the port
		serverChannel.socket().bind(new InetSocketAddress(host, port));
		//get the channel manager
		this.selector = Selector.open();
		//Register the channel to manager and bind the event
		serverChannel.register(selector,SelectionKey.OP_ACCEPT);
		}
	/**
	 * use asking mode to listen the event of selector
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes" })
	public void listen() throws IOException{
		System.out.println("=========The Server is start!===========");
		while(true){
			selector.select();
			Iterator ite =  this.selector.selectedKeys().iterator();
			while(ite.hasNext()){
				SelectionKey key = (SelectionKey)ite.next();
				ite.remove();
				if(key.isAcceptable()){
					ServerSocketChannel server = (ServerSocketChannel)key.channel();
					SocketChannel channel = server.accept();
					channel.configureBlocking(false);
					System.out.println("=========channel is Connected："+channel.isConnected());
					System.out.println("=========channel is Open："+channel.isOpen());
					System.out.println("=========channel is ConnectionPending："+channel.isConnectionPending());
//					channel.register(this.selector, SelectionKey.OP_READ);
					HanlderNioSocketChannel hanlderNioSocketChannel= new HanlderNioSocketChannel();
					channel.register(hanlderNioSocketChannel.getSelector(), SelectionKey.OP_READ,"decodeProtol");
					exec.submit(hanlderNioSocketChannel);
				}
			}
			
		}
	}	
}
