package mina.tcp.coder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.SmsInfo;
/**
 * 短信解码器
 * @author donald
 * 2017年5月20日
 * 下午10:55:33
 */
public class CumulativeSmsDecoder2 extends CumulativeProtocolDecoder {
	private static final Logger log = LoggerFactory.getLogger(CumulativeSmsDecoder2.class);
	private final CharsetDecoder charsetDecoder;
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");

	public CumulativeSmsDecoder2(Charset charset) {
		charsetDecoder = charset.newDecoder();
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		//从会话获取解码器状态上下文
		Context ctx = getContext(session);
		//从上下文获取已经解码的短信相关信息
		int matchCount = ctx.getMatchCount();
		int line = ctx.getLine();
		IoBuffer buffer = ctx.innerBuffer;
		String statusLine = ctx.getStatusLine(), sender = ctx.getSender(), 
				receiver = ctx.getReceiver(),length = ctx.getLength(), 
				sms = ctx.getSms();
		while (in.hasRemaining()) {
			byte b = in.get();
			buffer.put(b);
			matchCount++;
			if (line < 4 && b == 10) {
				if (line == 0) {//状态行
					buffer.flip();
					statusLine = buffer.getString(matchCount, charsetDecoder);
					statusLine = statusLine.substring(0, statusLine.length() - 1);
					matchCount = 0;
					buffer.clear();
					ctx.setStatusLine(statusLine);
				}
				if (line == 1) {//短信发送者
					buffer.flip();
					sender = buffer.getString(matchCount, charsetDecoder);
					sender = sender.substring(0, sender.length() - 1);
					matchCount = 0;
					buffer.clear();
					ctx.setSender(sender);
				}
				if (line == 2) {//短信接受者
					buffer.flip();
					receiver = buffer.getString(matchCount, charsetDecoder);
					receiver = receiver.substring(0, receiver.length() - 1);
					matchCount = 0;
					buffer.clear();
					ctx.setReceiver(receiver);
				}
				if (line == 3) {//短信内容长度
					buffer.flip();
					length = buffer.getString(matchCount, charsetDecoder);
					length = length.substring(0, length.length() - 1);
					matchCount = 0;
					buffer.clear();
					ctx.setLength(length);
				}
				line++;
			} else if (line == 4) {//短信内容，读到与短息内容长度length相同的字节数时，解析内容
				if (matchCount == Long.parseLong(length.split(": ")[1])) {
					buffer.flip();
					sms = buffer.getString(matchCount, charsetDecoder);
					ctx.setSms(sms);
					ctx.setMatchCount(matchCount);
					ctx.setLine(line);
					break;
				}
			}
			ctx.setMatchCount(matchCount);
			ctx.setLine(line);
		}
		//一条短信解码完毕，组装短信，重置解码器上下文
		if (ctx.getLine() == 4 && Long.parseLong(ctx.getLength().split(": ")[1]) == ctx.getMatchCount()) {
			SmsInfo smsObject = new SmsInfo();
			smsObject.setSender(sender.split(": ")[1]);
			smsObject.setReceiver(receiver.split(": ")[1]);
			smsObject.setMessage(sms);
			out.write(smsObject);
			log.info("========短信解码器解码一条短信完毕....");
			ctx.reset();
			/*检查是否读取数据，没有则非法状态异常；
			已经消费了数据，如果缓冲区有数据，则继续读取缓冲区数据并解码*/
			return true;
		} else {
			/*不再调用解码器解码方法解码数据，如果这次数据未读完，及缓冲区还有数据，则
			保存在会话中，以便与下一次的数据合并处理，如果数据读取完，则清空缓冲区。*/
			return false;
		}
	}
    /**
     * 从会话获取短信解码器上下文
     * @param session
     * @return
     */
	private Context getContext(IoSession session) {
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new Context();
			session.setAttribute(CONTEXT, context);
		}
		return context;
	}
    /**
     * 记录解码器的上下文
     */
	private class Context {
		private final IoBuffer innerBuffer;
		private String statusLine = "";
		private String sender = "";
		private String receiver = "";
		private String length = "";
		private String sms = "";
		private int matchCount = 0;
		private int line = 0;

		public Context() {
			innerBuffer = IoBuffer.allocate(1024).setAutoExpand(true);
		}
		public int getMatchCount() {
			return matchCount;
		}
		public void setMatchCount(int matchCount) {
			this.matchCount = matchCount;
		}

		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}
		public String getStatusLine() {
			return statusLine;
		}
		public void setStatusLine(String statusLine) {
			this.statusLine = statusLine;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		public String getReceiver() {
			return receiver;
		}
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}
		public String getLength() {
			return length;
		}
		public void setLength(String length) {
			this.length = length;
		}
		public String getSms() {
			return sms;
		}
		public void setSms(String sms) {
			this.sms = sms;
		}
        /**
         * 重置解码器上下文状态
         */
		public void reset() {
			this.innerBuffer.clear();
			this.matchCount = 0;
			this.line = 0;
			this.statusLine = "";
			this.sender = "";
			this.receiver = "";
			this.length = "";
			this.sms = "";
		}
	}

}
