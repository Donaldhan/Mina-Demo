package mina.tcp.coder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.SmsInfo;
/**
 * 短信解码器
 * @author donald
 * 2017年5月19日
 * 下午11:01:50
 */
public class CumulativeSmsDecoder extends CumulativeProtocolDecoder {
	private static final Logger log = LoggerFactory.getLogger(CumulativeSmsDecoder.class);
	private final CharsetDecoder charsetDecoder;
	public CumulativeSmsDecoder(Charset charset) {
		charsetDecoder = charset.newDecoder();
	}
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(1024).setAutoExpand(true);
		int matchCount = 0;//记录短信每一行的字节数
		String statusLine = "", sender = "", receiver = "", length = "", sms = "";
		int line = 0;//短信行计数器
		while (in.hasRemaining()) {
			byte b = in.get();
			buffer.put(b);
			// 10 为\n的ASCII编码，短信一行信息读完
			if (b == 10 && line < 4) {
				matchCount++;//一行读取完毕，字节数加1为\n
				if (line == 0) {//状态行
					buffer.flip();
					statusLine = buffer.getString(matchCount, charsetDecoder);
					//剔除最后一个换行符\n
					statusLine = statusLine.substring(0, statusLine.length() - 1);
					log.debug("========短信状态行："+statusLine);
					matchCount = 0;//重置短信行字节序列计数器
					buffer.clear();//清除短信行字节序列计数器
				}
				if (line == 1) {//短信发送者
					buffer.flip();
					sender = buffer.getString(matchCount, charsetDecoder);
					sender = sender.substring(0, sender.length() - 1);
					log.debug("========短信发送者："+sender);
					matchCount = 0;
					buffer.clear();
				}
				if (line == 2) {//短信接受者
					buffer.flip();
					receiver = buffer.getString(matchCount, charsetDecoder);
					receiver = receiver.substring(0, receiver.length() - 1);
					log.debug("========短信接受者："+receiver);
					matchCount = 0;
					buffer.clear();
				}
				if (line == 3) {//短信内容长度
					buffer.flip();
					length = buffer.getString(matchCount, charsetDecoder);
					length = length.substring(0, length.length() - 1);
					log.debug("========短信内容长度："+length.split(": ")[1]);
					matchCount = 0;
					buffer.clear();
				}
				line++;//短信一行读取完毕
			} else if (line == 4) {//短信内容
				matchCount++;
				//读取短信内容，读到与短息内容长度length相同的字节数时，解析内容
				if (matchCount == Long.parseLong(length.split(": ")[1])) {
					buffer.flip();
					sms = buffer.getString(matchCount, charsetDecoder);
					log.debug("========短信内容："+sms);
					buffer.clear();
					matchCount = 0;
					line++;
					break;
				}
			}else{
				matchCount++;//一行没读完，记录读取的字节数
			}
		}
		//组装短信
		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setSender(sender.split(": ")[1]);
		smsInfo.setReceiver(receiver.split(": ")[1]);
		smsInfo.setMessage(sms);
		out.write(smsInfo);
		log.info("========短信解码器解码完毕....");
		/*不再调用解码器解码方法解码数据，如果这次数据未读完，及缓冲区还有数据，则
		保存在会话中，以便与下一次的数据合并处理，如果数据读取完，则清空缓冲区。*/
		return false;
	}
}
