package nio.socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import socket.ProtocolConstants;

/**
 * 加法计算
 * @author donald
 * 2017年4月10日
 * 下午9:32:57
 */
public class NIOClientSum {
	private static final String HOST = "10.16.7.107";
	private static final int PORT = 10000;
	//manager the channel
	private Selector selector;
	/**
	 * stat Client
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		NIOClientSum client = new NIOClientSum();
		client.initClient(HOST,PORT);
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
		System.out.println("===========The Sum Client is start!===========");
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
					System.out.println("=========channel is Connected："+channel.isConnected());
					System.out.println("=========channel is Open："+channel.isOpen());
					System.out.println("=========channel is ConnectionPending："+channel.isConnectionPending());
					ByteBuffer[] proctols = null;//协议
					proctols = new ByteBuffer[2];
					ByteBuffer proctolCodeBuffer = null;//协议编码
					proctolCodeBuffer = ByteBuffer.allocate(ProtocolConstants.PROTOCOL_CODE_LENGTH);
//					proctolCodeBuffer = ByteBuffer.wrap(new String("300000").getBytes("UTF-8"));
					System.out.println("ProtocolCode String length："+ProtocolConstants.SUM_PROTOCOL_300000.getBytes(ProtocolConstants.CHARSET_UTF8).length);
					proctolCodeBuffer.put(ProtocolConstants.SUM_PROTOCOL_300000.getBytes(ProtocolConstants.CHARSET_UTF8));
					System.out.println("ProtocolCode length："+proctolCodeBuffer.position());
					proctols[0] = proctolCodeBuffer;
					proctolCodeBuffer.flip();
					ByteBuffer dataBuffer = null;//协议内容：操作数
					dataBuffer = ByteBuffer.allocate(2*ProtocolConstants.OPERATE_NUM_LENGTH);
					dataBuffer.putInt(15);
					dataBuffer.putInt(6);
					System.out.println("data length："+dataBuffer.position());
					proctols[1] = dataBuffer;
					dataBuffer.flip();
					channel.write(proctols);//将缓冲区的内容发送到通道，
//					channel.shutdownOutput();
					System.out.println("=======write proctols to channel");
//					channel.register(this.selector, SelectionKey.OP_READ);
					channel.register(this.selector, SelectionKey.OP_READ,"calculateResult");
				}
				else if (key.isReadable()) read(key);
			}
			
		}
	}
	/**
	 * deal with the data come from the server
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
		SocketChannel channel = (SocketChannel) key.channel();
		String  attachedInfo = (String) key.attachment();
		System.out.println("========socketChannel attachedInfo："+attachedInfo);
		ByteBuffer[] proctols = null;
		proctols = new ByteBuffer[]{ByteBuffer.allocate(ProtocolConstants.PROTOCOL_CODE_LENGTH),ByteBuffer.allocate(ProtocolConstants.OPERATE_NUM_LENGTH)};
		System.out.println("========read caculate result from Server=======");
//		channel.read(proctols);
		while(proctols[0].position() != ProtocolConstants.PROTOCOL_CODE_LENGTH && proctols[1].position() != ProtocolConstants.OPERATE_NUM_LENGTH){
			channel.read(proctols);//待读取完成协议才解析
		}
		proctols[0].flip();
		proctols[1].flip();
		byte[] proctolCodeBytes = proctols[0].array();
		String proctolCode = new String(proctolCodeBytes,ProtocolConstants.CHARSET_UTF8).trim();
		if(proctolCode.equals(ProtocolConstants.ACK_PROTOCOL_300200)){
			int result = proctols[1].getInt();
			System.out.println("========the calculated result from server:"+result);
		}else if(proctolCode.equals(ProtocolConstants.ACK_PROTOCOL_300300)){
			System.out.println("========server decode procotol fail......");
		}
		else {
			System.out.println("========unknow error ...");
		}
		/*关闭Connection,即关闭到通道的连接，再次write将抛出异常*/
//		channel.shutdownOutput();
		/*关闭通道*/
//		channel.close();
		/*注意上面两个方法，测试时，不要开启；测试开启的话，Server端，会有一个OP_READ事件*/
	}
	
}
