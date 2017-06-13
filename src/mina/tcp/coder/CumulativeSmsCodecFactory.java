package mina.tcp.coder;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 短信编码解码工厂
 * @author donald 
 * 2017年5月19日 
 * 下午10:53:37
 */
public class CumulativeSmsCodecFactory implements ProtocolCodecFactory {
	private final CumulativeSmsEncoder encoder;
	private final CumulativeSmsDecoder decoder;

	public CumulativeSmsCodecFactory() {
		this(Charset.defaultCharset());
	}

	public CumulativeSmsCodecFactory(Charset charSet) {
		this.encoder = new CumulativeSmsEncoder(charSet);
		this.decoder = new CumulativeSmsDecoder(charSet);
	}
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}
}
