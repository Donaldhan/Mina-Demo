package nio.filechannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 测试FileChannel
 * @author donald
 * 2017年4月9日
 * 下午4:16:35
 */
public class testFileChannel {
	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("E:/nio_data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		/*
		 * 1.先屏蔽writeBytes，执行readBytes，从文件中读取数据
		 * 2.再屏蔽readBytes，执行writeBytes，向文件中写数据
		 * 3.最后屏蔽writeBytes，执行readBytes，从文件中读取数据
		 */
		writeBytes(buf, inChannel);
//		readBytes(buf, inChannel);
		inChannel.close();
		aFile.close();
	}
	private static void writeBytes(ByteBuffer buf, FileChannel fileChannel) throws IOException{
	    String newData = "new String to write to file...."+System.currentTimeMillis();
	    buf.put(newData.getBytes("UTF-8"));
	    buf.flip();
	    while(buf.hasRemaining())
	    	fileChannel.write(buf);
	    System.out.println("===已经写完数据到文件");
	}
	private static void readBytes(ByteBuffer buf, FileChannel fileChannel) throws IOException{
		    buf.clear();
			//从file通道读取数据到缓存区，即写入缓冲区
			int bytesRead = fileChannel.read(buf);
			while (bytesRead != -1) {
//				buf.compact();//将未读完的数据移到缓冲的前面，新写入的数据，将会append旧数据的后面
				bytesRead = fileChannel.read(buf);
			}
			//转换缓冲区模式
			buf.flip();// swith the mode write or read
			System.out.println("=====Read byte length:" + buf.limit());
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			System.out.println();
	}
}
