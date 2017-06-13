package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * Socket处理线程
 * @author donald
 * 2017年2月12日
 * 下午2:54:47
 */
public class SocketHandleRunnable implements Runnable {
	private  static ProtocolUtils protocolUtils = null;
	private Socket socket;
	static {
		protocolUtils = ProtocolUtils.getInstance();
	}
	public SocketHandleRunnable(Socket socket) {
		super();
		this.socket = socket;
	}
	@Override
	public void run() {
		System.out.println("开始处理Socket........");
		InputStream serverInputStream = null;
		OutputStream serverOutputStream = null;
		BufferedInputStream serverBufferInputStream = null;
		BufferedOutputStream serverBufferOutputStream = null;
		  try {
			serverInputStream = socket.getInputStream();
			serverOutputStream =  socket.getOutputStream();
			serverBufferInputStream = new BufferedInputStream(serverInputStream);
			serverBufferOutputStream = new BufferedOutputStream(serverOutputStream);
			int protocolLenght = ProtocolConstants.PROTOCOL_CODE_LENGTH + ProtocolConstants.OPERATE_NUM_LENGTH*2 + ProtocolConstants.PROTOCOL_END_LENGTH;
			boolean flag = true;
			while(flag){
				if(serverBufferInputStream.available() >= protocolLenght){
					 byte[] protcBuf  = new byte[protocolLenght];
		        	 int readLength = serverBufferInputStream.read(protcBuf, 0, protocolLenght);
		        	 System.out.println("从接受缓冲区读取的实际协议长度为："+readLength);
		        	 if(readLength == protocolLenght){
		        		 String protcStr = new String(protcBuf,ProtocolConstants.CHARSET_UTF8);
		        		 String endStr = protcStr.substring(protcStr.length()-2, protcStr.length());
		        		 if(endStr.equals(ProtocolConstants.PROTOCOL_END)){
		        			 System.out.println("开始解析计算协议......");
		        			 String protocolCode = protcStr.substring(0, 6);
			        		 String firstNumStr = protcStr.substring(6, 16);
			        		 int firstNum = Integer.valueOf(firstNumStr);
			        		 String secNumStr = protcStr.substring(16, 26);
			        		 int secNum = Integer.valueOf(secNumStr);
			        		 System.out.println("计算协议解析完毕......");
			        		 int result = 0;
			        		 if(protocolCode.equals(ProtocolConstants.SUM_PROTOCOL_300000)){
			        			 result = firstNum + secNum;
			        		 }
			        		 if(protocolCode.equals(ProtocolConstants.MULTI_PROTOCOL_300100)){
			        			 result = firstNum*secNum;
			        		 }
			        		 System.out.println("开始发送计算结果协议......");
			        		 //将计算结果值发送给Client
			        		//发送计算结果协议编码
					        byte[] AckProtocolBytes = ProtocolConstants.ACK_PROTOCOL_300200.getBytes(ProtocolConstants.CHARSET_UTF8);
					        serverBufferOutputStream.write(AckProtocolBytes);
			        		 //结果值
			        		 String resultStr = String.valueOf(result);
			        		 System.out.println("服务器计算结果为："+resultStr);
			        		 byte[] resultBytes =  resultStr.getBytes(ProtocolConstants.CHARSET_UTF8);
			        		 //结果长度
			        		 int resultLength = resultStr.length();
			        		 String reultLenthStr = String.valueOf(resultLength);
			        		 reultLenthStr = protocolUtils.fillString(reultLenthStr, ProtocolConstants.PROTOCOL_ACK_LENGTH);
			        		 byte[] resultLengthBytes = reultLenthStr.getBytes(ProtocolConstants.CHARSET_UTF8);
			        		 serverBufferOutputStream.write(resultLengthBytes);
			        		 //发送结果值
			        		 serverBufferOutputStream.write(resultBytes);
			        		 //发送结束符
			        		 serverBufferOutputStream.write(ProtocolConstants.PROTOCOL_END.getBytes(ProtocolConstants.CHARSET_UTF8));
			        		 System.out.println("发送计算结果协议结束......");
			        		//将缓冲区发送到Client，我们这里是强制清空缓冲区，实际不要这样做，以免影响数据传输效率
			        		 serverBufferOutputStream.flush();
		        		 }
		        	 }
		        	 if(readLength < 0){
		        		 System.out.println("与客户端失去连接.....");
		        	 }
		        	 if(readLength < protocolLenght){
		        		 //从缓冲区继续读取数据，直至读取的数据长度为协议长度+结束符；
		        		 //当数据解析异常时，可以用InputStream.skip(long n)，丢弃一些数据，以保证一次协议包的完成性
		        	 }
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			 try {
				serverBufferInputStream.close();
				serverBufferOutputStream.close();
				socket.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			} 
		     
		}
		System.out.println("Socket处理结束........");
	     
	}

}
