package mina.tcp.coder;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import mina.tcp.message.AckMessage;
import mina.tcp.message.MathMessage;
/**
 * 协议编码解码器工厂
 * @author donald
 * 2017年5月21日
 * 上午12:02:11
 */
public class MathProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public MathProtocolCodecFactory(boolean server) {
		if (server) {
			super.addMessageEncoder(AckMessage.class, AckMessageEncoder.class);
			super.addMessageDecoder(MathMessageDecoderPositive.class);
			super.addMessageDecoder(MathMessageDecoderNegative.class);
		} else {
			super.addMessageEncoder(MathMessage.class, MathMessageEncoder.class);
			super.addMessageDecoder(AckMessageDecoder.class);
		}
	}
}
