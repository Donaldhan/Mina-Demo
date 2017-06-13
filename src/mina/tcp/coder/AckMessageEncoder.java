package mina.tcp.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.AckMessage;
/**
 * 计算结果编码器
 * @author donald
 * 2017年5月20日
 * 下午11:58:18
 */
public class AckMessageEncoder implements MessageEncoder<AckMessage> {
	private final static Logger log = LoggerFactory.getLogger(AckMessageEncoder.class);
	@Override
	public void encode(IoSession session, AckMessage message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(4);
		buffer.putInt(message.getResult());
		buffer.flip();
		out.write(buffer);
	}
}
