package mina.tcp.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.MathMessage;
/**
 * 减法解码器
 * @author donald
 * 2017年5月20日
 * 下午11:54:25
 */
public class MathMessageDecoderNegative implements MessageDecoder {
	private final static Logger log = LoggerFactory.getLogger(MathMessageDecoderNegative.class);
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		if (in.remaining() < 2)
			return MessageDecoderResult.NEED_DATA;
		else {
			char symbol = in.getChar();
			if (symbol == '-') {
				return MessageDecoderResult.OK;
			} else {
				return MessageDecoderResult.NOT_OK;
			}
		}
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		MathMessage sm = new MathMessage();
		sm.setSymbol(in.getChar());
		sm.setFirstNum(in.getInt());
		sm.setSecondNum(in.getInt());
		out.write(sm);
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// undo
	}
}
