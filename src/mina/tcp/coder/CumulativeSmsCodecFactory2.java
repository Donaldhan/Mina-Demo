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
public class CumulativeSmsCodecFactory2 implements ProtocolCodecFactory {
	private final CumulativeSmsEncoder2 encoder;
	private final CumulativeSmsDecoder2 decoder;

	public CumulativeSmsCodecFactory2() {
		this(Charset.defaultCharset());
	}

	public CumulativeSmsCodecFactory2(Charset charSet) {
		this.encoder = new CumulativeSmsEncoder2(charSet);
		this.decoder = new CumulativeSmsDecoder2(charSet);
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
