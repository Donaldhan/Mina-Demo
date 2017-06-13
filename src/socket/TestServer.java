package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Server
 * @author donald
 * 2017年2月13日
 * 下午4:51:53
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
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String message = null;
			while ((message = bufferedReader.readLine()) != null) {
				System.out.println("收到客户端信息：" + message);
			}
			// 这里向网络进行两次写入
			OutputStream outputStream = socket.getOutputStream();
			// 将输出流包装为打印流
			PrintWriter printWriter = new PrintWriter(outputStream);
			printWriter.write("Welcome Client!");
			printWriter.flush();
			// 关闭输出流
			socket.shutdownOutput();
			// 关闭资源
			bufferedReader.close();
			inputStream.close();
			printWriter.close();
			outputStream.close();
			socket.close();
		}
	}
}
