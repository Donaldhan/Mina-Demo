package Serializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 * 
 * @author donald 2017年2月13日 下午4:51:53
 */
public class TestServer {
	public static final int PORT = 4003;

	public static void main(String[] args) {
		try {
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 服务端代码
	public static void startServer() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("服务器启动......");
		while (true) {
			Socket socket = serverSocket.accept();
			// 获取输入流，并读取服务器端的响应信息
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = null;
			objectInputStream = new ObjectInputStream(inputStream);
			Person person = null;
			try {
				person = (Person) objectInputStream.readObject();
				System.out.println("收到客户端用户信息：" + person);
				int int0 = objectInputStream.readInt();
				System.out.println("=======read int after read object persion:" + int0);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// 这里向网络进行两次写入
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = null;
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeUTF("it is a man");
			objectOutputStream.flush();
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();

		}
	}
}
