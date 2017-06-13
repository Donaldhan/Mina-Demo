package mina.tcp.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.SmsInfo;
/**
 * SmsServerHandler
 * @author donald
 * 2017年5月19日
 * 下午10:45:26
 */
public class SmsServerHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(SmsServerHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		SmsInfo sms = (SmsInfo) message;
		log.info("===message received from "+sms.getSender()+" is:" + sms.getMessage());
		SmsInfo ackSms = new SmsInfo();
		ackSms.setSender(sms.getReceiver());
		ackSms.setReceiver(sms.getSender());
		ackSms.setMessage("收到...");
		session.write(ackSms);
		log.info("===回复短信已发送...");
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("===会话异常："+cause.getMessage());
		cause.printStackTrace();
		session.closeNow();
	}

}
