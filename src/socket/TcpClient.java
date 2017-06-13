package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Socket Client
 * @author donald
 * 2017年2月12日
 * 下午2:43:46
 */
public class TcpClient {
	private static ProtocolUtils protocolUtils = null;
	private static final String ip ="10.16.7.107";
	private static final int port = 4003;
	private static final int bufferSize = 1024;
	static {
		protocolUtils = ProtocolUtils.getInstance();
	}
	// 搭建客户端
	public static void main(String[] args){
			Socket socket = null;
			OutputStream clientOutputStream = null;
			InputStream clientInputStream = null;
			BufferedInputStream clientBufferInputStream = null;
			BufferedOutputStream clientBufferOutputStream = null;
			try {
				socket = new Socket(ip, port);
				socket.setSendBufferSize(bufferSize);
				socket.setReceiveBufferSize(bufferSize);
				socket.setKeepAlive(true);
				if(socket.isConnected()){
					System.out.println("连接服务器成功...........");
					try {
						clientOutputStream =  socket.getOutputStream();
						clientInputStream = socket.getInputStream();
						clientBufferInputStream = new BufferedInputStream(clientInputStream);
						clientBufferOutputStream = new BufferedOutputStream(clientOutputStream);
						 //只做长度为10以内的加法与乘法
						//加法
						System.out.println("发送加法计算协议开始...........");
						//发送协议编码
			            byte[] sumProtocolBytes = ProtocolConstants.SUM_PROTOCOL_300000.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(sumProtocolBytes);
			            //发送第一个操作数
			            int firstNum = 15;
			            String firstNumStr = String.valueOf(firstNum);
			            //如果操作符不够长度，则左侧补零
			            firstNumStr = protocolUtils.fillString(firstNumStr, ProtocolConstants.OPERATE_NUM_LENGTH);
			            byte[] firstNumBytes = firstNumStr.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(firstNumBytes);
			            //发送第二个操作数
			            int secondNum = 6;
			            String secondNumStr = String.valueOf(secondNum);
			            
			            secondNumStr = protocolUtils.fillString(secondNumStr, ProtocolConstants.OPERATE_NUM_LENGTH);
			            byte[] secondNumBytes = secondNumStr.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(secondNumBytes);
			            //发送协议结束符
			            byte[] endBytes = ProtocolConstants.PROTOCOL_END.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(endBytes);
			            System.out.println("发送加法计算协议结束...........");
			            System.out.println("发送乘法计算协议开始...........");
			            // 乘法
			            byte[] sumProtocolBytesx = ProtocolConstants.MULTI_PROTOCOL_300100.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(sumProtocolBytesx);
			            //发送第一个操作数
			            int firstNumx = 17;
			            String firstNumStrx = String.valueOf(firstNumx);
			            //如果操作符不够长度，则左侧补零
			            firstNumStrx = protocolUtils.fillString(firstNumStrx, ProtocolConstants.OPERATE_NUM_LENGTH);
			            byte[] firstNumBytesx = firstNumStrx.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(firstNumBytesx);
			            //发送第二个操作数
			            int secondNumx = 8;
			            String secondNumStrx = String.valueOf(secondNumx);
			            
			            secondNumStrx = protocolUtils.fillString(secondNumStrx, ProtocolConstants.OPERATE_NUM_LENGTH);
			            byte[] secondNumBytesx = secondNumStrx.getBytes(ProtocolConstants.CHARSET_UTF8);
			            clientBufferOutputStream.write(secondNumBytesx);
			            //发送协议结束符
			            clientBufferOutputStream.write(endBytes);
			            //将缓冲区发送到Server，我们这里是强制清空缓冲区，实际不要这样做，以免影响数据传输效率
			            clientBufferOutputStream.flush();
			            System.out.println("发送乘法计算协议结束...........");
			            try {
							Thread.sleep(3000);
							System.out.println("等待服务器计算结果...........");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			            boolean flag = true;
			            //返回的协议长度，协议编码6+结果长度2
			            int ackLength = ProtocolConstants.PROTOCOL_CODE_LENGTH + ProtocolConstants.PROTOCOL_ACK_LENGTH;
			            while(flag){
//			               System.out.println("等待解析服务器计算结果...........");
			               //结果值长度
			               int valueLenth = 0;
			               //接受缓冲区的可利用长度大于等于8
				           if(clientBufferInputStream.available()>=ackLength){
				        	   byte[] codeBuf  = new byte[6];
				        	   int codeLength = clientBufferInputStream.read(codeBuf, 0, 6);
				        	   System.out.println("从接受缓冲区读取协议编码的实际长度为："+codeLength);
				        	   if(codeLength == 6){
				        		   String ackProtcolCode = new String(codeBuf,ProtocolConstants.CHARSET_UTF8);
				        		   if(ackProtcolCode.equals(ProtocolConstants.ACK_PROTOCOL_300200)){
				        			   System.out.println("协议编码："+ackProtcolCode);
				        			   byte[] resultBuf  = new byte[2];
				        			   int resultLength = clientBufferInputStream.read(resultBuf, 0, 2);
				        			   if(resultLength == 2){
				        				   String resultLengthStr = new String(resultBuf,ProtocolConstants.CHARSET_UTF8);
				        				   valueLenth = Integer.valueOf(resultLengthStr);
				        				   System.out.println("结果值长度为："+valueLenth);
				        			   }
				        			   if(resultLength < 0){
						        		   //与服务器失去连接
						        		   flag = false;
						        		   System.out.println("与服务器失去连接.....");
						        	   }
				        		   }
				        	   }
				        	   if(codeLength < 0){
				        		   //与服务器失去连接
				        		   flag = false;
				        		   System.out.println("与服务器失去连接.....");
				        	   }
				        	   
				           }
				        if(valueLenth > 0){
				        	//结果值+协议结束符（2）
				        	int valueEndLenth = valueLenth + ProtocolConstants.PROTOCOL_END_LENGTH;
				        	if(clientBufferInputStream.available()>=valueEndLenth){
				        		byte[] valueEndBuf  = new byte[valueEndLenth];
			        			int readLength = clientBufferInputStream.read(valueEndBuf, 0, valueEndLenth);
			        			System.out.println("从接受缓冲区读取计算结果值和结束符实际长度为："+readLength);
			        			if(readLength == valueEndLenth){
			        				String valueEndStr = new String(valueEndBuf,ProtocolConstants.CHARSET_UTF8);
			        				String endStr = valueEndStr.substring(valueEndStr.length()-2, valueEndStr.length());
			        				if(endStr.equals(ProtocolConstants.PROTOCOL_END)){
			        					System.out.println("计算结果协议结束："+readLength);
			        					String valueStr = valueEndStr.substring(0,valueEndStr.length()-2);
			        					System.out.println("计算结果为："+valueStr);
			        				}
			        			}
			        			 if(readLength < 0){
					        		   //与服务器失去连接
					        		   flag = false;
					        		   System.out.println("与服务器失去连接.....");
					        	   }
				        	}
				        	
				        }
			           }
			            
					} catch (IOException e) {
						System.out.println("服务器IO异常："+e.getMessage());
						e.printStackTrace();
					}
					finally{
						try {
							clientBufferInputStream.close();
							clientInputStream.close();
						} catch (IOException e) {
							System.out.println("关闭资源异常："+e.getMessage());
							e.printStackTrace();
						} 
						
					}
				}
			} catch (UnknownHostException e) {
				System.out.println("连接服务器异常："+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("连接服务器IO异常："+e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
	}
}
