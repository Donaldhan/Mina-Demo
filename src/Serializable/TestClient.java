package Serializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Client
 * @author donald
 * 2017年2月13日
 * 下午4:52:27
 */
public class TestClient {
	private static final int PORT = 4003;
	private static final String ip = "10.16.7.107";

	public static void main(String[] args) {
		try {
			client();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void client() throws UnknownHostException, IOException {
		// 创建socket连接
		Socket socket = new Socket(ip, PORT);
		System.out.println("连接服务器成功......");
		// 这里向网络进行两次写入
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(outputStream);
		Person person = new Person("donald", 27, "man");
		try {
			objectOutputStream.writeObject(person);
			objectOutputStream.writeInt(4);
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 获取输入流，并读取服务器端的响应信息
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream  = null;
		objectInputStream = new ObjectInputStream(inputStream);
		String str = objectInputStream.readUTF();
		System.out.println("收到服务端反馈信息：" + str);
		objectOutputStream.close();
		objectInputStream.close();
		socket.close();

	}
}
