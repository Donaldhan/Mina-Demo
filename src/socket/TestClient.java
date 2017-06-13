package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
		// 将输出流包装为打印流
		PrintWriter printWriter = new PrintWriter(outputStream);
		printWriter.write("Hello Server!");
		printWriter.flush();
		// 关闭输出流
		socket.shutdownOutput();
		// 获取输入流，并读取服务器端的响应信息
		InputStream inputStream = socket.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String message = null;
		while ((message = bufferedReader.readLine()) != null) {
			System.out.println("收到服务端信息：" + message);
		}
		// 关闭资源
		bufferedReader.close();
		inputStream.close();
		printWriter.close();
		outputStream.close();
		socket.close();

	}
}
