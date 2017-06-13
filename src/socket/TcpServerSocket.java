package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Socket Server
 * @author donald
 * 2017年2月12日
 * 下午2:43:58
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TcpServerSocket {

	private static int  port = 4003;//端口号
	private static int backlog = 20;//Server最大连接数
	private static InetAddress serverHost = null;//server地址
	static{
		try {
			serverHost = InetAddress.getLocalHost();
			System.out.println("主机名："+serverHost.getHostName());
			System.out.println("主机地址："+serverHost.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("获取主机地址信息异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	//搭建服务器端
    public static void main(String[] args){
    	TcpServerSocket tcpServerSocket = new TcpServerSocket();
        //创建一个服务器端Socket，即SocketService 
    	tcpServerSocket.startServer();
    }
    public  void startServer(){
            ServerSocket tcpServer=null;
            ExecutorService  exec = Executors.newCachedThreadPool();
            try {
				tcpServer=new ServerSocket(port,backlog,serverHost);
				System.out.println("服务器启动成功.........");
				 while(true){
		            	try {
		            		Socket socket = tcpServer.accept();
							System.out.println("服务器接受客户端连接.........");
							SocketHandleRunnable sHandleRunnable = new SocketHandleRunnable(socket);
			            	exec.execute(sHandleRunnable);
			            	//有返回值
//			            	exec.submit(sHandleRunnable);
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
			} catch (IOException e) {
				System.out.println("服务器启动失败:"+e.getMessage());
				e.printStackTrace();
			}
            finally{
            	try {
					tcpServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            	exec.shutdown();
            }
           
    }
}
