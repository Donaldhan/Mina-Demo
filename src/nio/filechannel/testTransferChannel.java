package nio.filechannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
/**
 * 测试通道间传输
 * @author donald
 * 2017年4月9日
 * 下午10:27:16
 */
public class testTransferChannel {
	public static void main(String[] args) throws IOException {
		RandomAccessFile fromFile = new RandomAccessFile("E:/nio_data.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile("E:/nio_data_to.txt", "rw");
		FileChannel toChannel = toFile.getChannel();
		RandomAccessFile to2File = new RandomAccessFile("E:/nio_data_tox.txt", "rw");
		FileChannel to2Channel = to2File.getChannel();
		long position = 0;
		long count = fromChannel.size();
		//将源通道的数据传输的本通道
		toChannel.transferFrom(fromChannel, position, count);
		System.out.println("===将源通道的数据传输的本通道完毕");
		//将本通道数据传输到目的通道
		fromChannel.transferTo(position, count, to2Channel);
		System.out.println("===将本通道数据传输到目的通道完毕");
		fromChannel.close();
		fromFile.close();
		toChannel.close();
		toFile.close();
		to2Channel.close();
		to2File.close();
	}
}
