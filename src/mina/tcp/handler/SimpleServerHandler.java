package mina.tcp.handler;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * simple server  handler
 * @author donald
 * 2017年5月19日
 * 下午1:13:45
 */
public class SimpleServerHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(SimpleServerHandler.class);
	private  static final CharsetEncoder charsetEncoder= Charset.forName("UTF-8").newEncoder();
	private static final CharsetDecoder charsetDecoder= Charset.forName("UTF-8").newDecoder();
	public void messageReceived(IoSession session, Object message) throws Exception {
		String msg = (String) message;
		log.info("=========The message received from Client is:" + msg);
		//收到客户端发送的关闭会话命令
		/*if(msg.equals("quit")){
			session.closeNow();
		}*/
		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.putString("Hello Client...\r\nI'm Server...\r\nserver test...", charsetEncoder);
		buffer.flip();
		session.write(buffer);
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("=========Session Closed...");
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("=========Session Created...");
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug(session + "=========Session Idle...");
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.debug("=========Session Opened...");
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage());
		cause.printStackTrace();
		session.closeNow();
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("=========messageSent...");
	}

}
