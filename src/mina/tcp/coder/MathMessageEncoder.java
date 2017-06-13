package mina.tcp.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.MathMessage;

/**
 * 计算消息编码器
 * @author donald 
 * 2017年5月20日 
 * 下午11:49:42
 */
public class MathMessageEncoder implements MessageEncoder<MathMessage> {
	private final static Logger log = LoggerFactory.getLogger(MathMessageEncoder.class);
	@Override
	public void encode(IoSession session, MathMessage message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(10);
		buffer.putChar(message.getSymbol());
		buffer.putInt(message.getFirstNum());
		buffer.putInt(message.getSecondNum());
		buffer.flip();
		out.write(buffer);
	}
}
