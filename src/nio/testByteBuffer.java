package nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
/**
 * 协议测试
 * @author donald
 * 2017年4月10日
 * 下午9:26:57
 */
public class testByteBuffer {
	public static void main(String[] args) {
		ByteBuffer[] proctols = null;//
		proctols = new ByteBuffer[2];
		ByteBuffer protocolBuffer = null;//协议编码
		protocolBuffer = ByteBuffer.allocate(6);
		try {
			System.out.println("ProtocolCode String length："+new String("300000").getBytes("UTF-8").length);
			protocolBuffer.put(new String("300000").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("ProtocolCode length："+protocolBuffer.position());
		proctols[0] = protocolBuffer;
		ByteBuffer dataBuffer = null;//操作数
		dataBuffer = ByteBuffer.allocate(8);
		dataBuffer.putInt(15);
		dataBuffer.putInt(6);
		System.out.println("data length："+dataBuffer.position());
		proctols[1] = dataBuffer;
//		protocolBuffer.compact();//针对数据太大，缓冲区一次装不完的情况
		protocolBuffer.clear();
		try {
			protocolBuffer.put(new String("300100").getBytes("UTF-8"));
			System.out.println("ProtocolCode length："+protocolBuffer.position());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		dataBuffer.compact();
		dataBuffer.clear();
		dataBuffer.putInt(17);
		dataBuffer.putInt(8);
		System.out.println("data length："+dataBuffer.position());
	}
}
