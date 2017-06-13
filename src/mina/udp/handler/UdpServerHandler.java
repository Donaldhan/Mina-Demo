package mina.udp.handler;

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
 * Udp Server peer handler
 * @author donald
 * 2017年5月18日
 * 下午10:00:21
 */
public class UdpServerHandler extends IoHandlerAdapter {
	private  static final Logger log = LoggerFactory.getLogger(UdpServerHandler.class);
	private  static final CharsetEncoder charsetEncoder= Charset.forName("UTF-8").newEncoder();
	private static final CharsetDecoder charsetDecoder= Charset.forName("UTF-8").newDecoder();
	public void messageReceived(IoSession session, Object message) throws Exception {
		IoBuffer buffer = (IoBuffer) message;
		String msg = buffer.getString(buffer.limit(),charsetDecoder);
		log.info("=========The message received from Server peer is:" + msg);
		buffer.clear();
		buffer.putString("Hello Client peer...", charsetEncoder);
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
		session.closeNow();
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("******************* messageSent...");
	}
}
