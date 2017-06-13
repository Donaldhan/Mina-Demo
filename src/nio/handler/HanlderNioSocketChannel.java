package nio.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import socket.ProtocolConstants;
/**
 * 处理SocketChannel读事件
 * @author donald
 * 2017年4月11日
 * 下午10:32:55
 */
public class HanlderNioSocketChannel implements Runnable{
	private Selector selector;
	private String threadName;
	public HanlderNioSocketChannel() {
		super();
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadName = Thread.currentThread().getName();
	}
	public Selector getSelector() {
		return selector;
	}
	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	@Override
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * use asking mode to listen the event of selector
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes" })
	public void listen() throws IOException{
		System.out.println(threadName+"=========The Server Calculate is start!===========");
		while(true){
			selector.select();
			Iterator ite =  this.selector.selectedKeys().iterator();
			while(ite.hasNext()){
				SelectionKey key = (SelectionKey)ite.next();
				ite.remove();
		        if (key.isReadable()) {
					read(key);
				}
			}
			
		}
	}	
	private void read(SelectionKey key){
		try {
			SocketChannel channel = (SocketChannel) key.channel();
			String  attachedInfo = (String) key.attachment();
			System.out.println(threadName+"========socketChannel attachedInfo："+attachedInfo);
			ByteBuffer[] proctols = null;//协议
			ByteBuffer proctolCodeBuffer = null;//协议编码
			proctolCodeBuffer = ByteBuffer.allocate(ProtocolConstants.PROTOCOL_CODE_LENGTH);
			ByteBuffer dataBuffer = null;//协议内容：操作数
			dataBuffer = ByteBuffer.allocate(2*ProtocolConstants.OPERATE_NUM_LENGTH);
			proctols = new ByteBuffer[]{proctolCodeBuffer,dataBuffer};
			System.out.println(threadName+"========read caculate proctol from Client=======");
	//		channel.read(proctols);
			while(proctolCodeBuffer.position() != ProtocolConstants.PROTOCOL_CODE_LENGTH && dataBuffer.position() != 2*ProtocolConstants.OPERATE_NUM_LENGTH){
				channel.read(proctols);//待读取完成协议才解析
			}
	//		channel.shutdownInput();
			proctolCodeBuffer.flip();
			dataBuffer.flip();
			byte[] proctolCodeBytes = proctolCodeBuffer.array();
			String proctolCode = new String(proctolCodeBytes,ProtocolConstants.CHARSET_UTF8).trim();
			int firstNum = 0;
			int secondNum = 0;
			int result = 0;
			if(proctolCode.equals(ProtocolConstants.SUM_PROTOCOL_300000)){
				System.out.println(threadName+"========the protocol is sum algorithm=======");
				firstNum = dataBuffer.getInt();
				secondNum = dataBuffer.getInt();
				System.out.println("operate num is:"+firstNum+","+secondNum);
				result = firstNum*secondNum;
				proctolCodeBuffer.clear();
				proctolCodeBuffer.put(ProtocolConstants.ACK_PROTOCOL_300200.getBytes(ProtocolConstants.CHARSET_UTF8));
				dataBuffer.clear();
				//针对数据太大，缓冲区一次装不完的情况,将缓冲区中，未写完的数据，移到缓冲区的前面
	//			dataBuffer.compact()
				dataBuffer.putInt(result);
				proctolCodeBuffer.flip();
				dataBuffer.flip();//切换写模式到读模式，从缓冲区读取数据，写到通道中
				channel.write(proctols);
				
			}
			else if(proctolCode.equals(ProtocolConstants.MULTI_PROTOCOL_300100)){
				System.out.println(threadName+"========the protocol is multiply algorithm=======");
				firstNum = dataBuffer.getInt();
				secondNum = dataBuffer.getInt();
				System.out.println("operate num is:"+firstNum+","+secondNum);
				result = firstNum*secondNum;
				proctolCodeBuffer.clear();
				proctolCodeBuffer.put(ProtocolConstants.ACK_PROTOCOL_300200.getBytes(ProtocolConstants.CHARSET_UTF8));
				proctolCodeBuffer.flip();
				dataBuffer.clear();
				//针对数据太大，缓冲区一次装不完的情况,将缓冲区中，未写完的数据，移到缓冲区的前面
	//			dataBuffer.compact()
				dataBuffer.putInt(result);
				dataBuffer.flip();//切换写模式到读模式，从缓冲区读取数据，写到通道中
				channel.write(proctols);
			}
			else{
				System.out.println(threadName+"========server decode procotol fail......");
				proctolCodeBuffer.clear();
				proctolCodeBuffer.put(ProtocolConstants.ACK_PROTOCOL_300300.getBytes(ProtocolConstants.CHARSET_UTF8));
				proctolCodeBuffer.flip();
				dataBuffer.clear();
				dataBuffer.putInt(0);
				dataBuffer.flip();
				channel.write(proctols);
			}
			/*关闭Connection,即关闭到通道的连接，再次write将抛出异常*/
//			channel.shutdownOutput();
			/*关闭通道*/
	        //channel.close();
			/*注意上面两个方法，测试时，不要开启；测试开启的话，Server端，会有一个OP_READ事件*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
