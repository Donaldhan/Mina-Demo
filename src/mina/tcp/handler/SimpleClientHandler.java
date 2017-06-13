package mina.tcp.handler;

import java.nio.charset.CharacterCodingException;
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
 * simple client handler
 * @author donald
 * 2017年5月19日
 * 下午1:10:12
 */
public class SimpleClientHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(SimpleClientHandler.class);
	private  static final CharsetEncoder charsetEncoder= Charset.forName("UTF-8").newEncoder();
	private static final CharsetDecoder charsetDecoder= Charset.forName("UTF-8").newDecoder();
	public void messageReceived(IoSession session, Object message) throws Exception {
		String msg = (String) message;
		log.info("=========The message received from Server  is:" + msg);
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("=========Session Created...");
	}
	@Override
	public void sessionOpened(IoSession session) throws CharacterCodingException {
		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.putString("Hello Server...\r\nI'm Client...\r\nclient test...", charsetEncoder);
		buffer.flip();
		session.write(buffer);
		//我们可以在这里发送一个quit命令，当Server接受到quit命令时，关闭会话
		/*buffer.clear();
		buffer.putString("quit\r\n", charsetEncoder);
		buffer.flip();
		session.write(buffer);*/
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug(session + "=========Session Idle...");
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
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("=========Session Closed...");
	}

}
