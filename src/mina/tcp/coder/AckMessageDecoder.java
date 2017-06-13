package mina.tcp.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.AckMessage;
/**
 * 计算结果解码器
 * @author donald
 * 2017年5月21日
 * 上午12:01:04
 */
public class AckMessageDecoder implements MessageDecoder {
	private final static Logger log = LoggerFactory.getLogger(AckMessageDecoder.class);
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		if (in.remaining() < 4)
			return MessageDecoderResult.NEED_DATA;
		else if (in.remaining() == 4)
			return MessageDecoderResult.OK;
		else
			return MessageDecoderResult.NOT_OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		AckMessage rm = new AckMessage();
		rm.setResult(in.getInt());
		out.write(rm);
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// undo
	}
}
