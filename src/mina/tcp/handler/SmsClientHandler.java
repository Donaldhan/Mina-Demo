package mina.tcp.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.SmsInfo;
/**
 * SmsClientHandler
 * @author donald
 * 2017年5月19日
 * 下午10:30:24
 */
public class SmsClientHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(SmsClientHandler.class);
	@Override
	public void sessionOpened(IoSession session) {
		SmsInfo sms = new SmsInfo();
		sms.setSender("13688888888");
		sms.setReceiver("18866666666");
		sms.setMessage("Hello Sms Server...");
		session.write(sms);
		log.info("===短信已发送...");
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		SmsInfo sms = (SmsInfo) message;
		log.info("===message received from "+sms.getSender()+" is:" + sms.getMessage());
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("===会话异常："+cause.getMessage());
		cause.printStackTrace();
		session.closeNow();
	}
	
}
