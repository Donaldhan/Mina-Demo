package mina.tcp.coder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.SmsInfo;
/**
 * 短信编码器
 * @author donald
 * 2017年5月19日
 * 下午10:55:48
 */
public class CumulativeSmsEncoder extends ProtocolEncoderAdapter {
	private static final Logger log = LoggerFactory.getLogger(CumulativeSmsEncoder.class);
	private final Charset charset;
	private final CharsetEncoder charsetEncoder;
	public CumulativeSmsEncoder(Charset charset) {
		this.charset = charset;
		charsetEncoder = charset.newEncoder();
	}
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		SmsInfo sms = (SmsInfo) message;
		
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		String statusLine = "M sip:wap.fetion.com.cn SIP-C/2.0";//状态行
		String sender = sms.getSender();
		String receiver = sms.getReceiver();
		String smsContent = sms.getMessage();
		//组装发送内容，我们以\n来分隔
		buffer.putString(statusLine + '\n', charsetEncoder);//状态行
		buffer.putString("S: " + sender + '\n', charsetEncoder);//短信发送者
		buffer.putString("R: " + receiver + '\n', charsetEncoder);//短信接受者
		buffer.putString("L: " + (smsContent.getBytes(charset).length) + "\n", charsetEncoder);//内容长度
		buffer.putString(smsContent, charsetEncoder);//内容
		//切换读写模式
		buffer.flip();
		out.write(buffer);
		log.info("========短信编码器编码完毕....");
	}
}
