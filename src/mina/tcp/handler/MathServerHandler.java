package mina.tcp.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.AckMessage;
import mina.tcp.message.MathMessage;
/**
 * MathServerHandler
 * @author donald
 * 2017年5月20日
 * 下午11:49:02
 */
public class MathServerHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(MathServerHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		MathMessage sm = (MathMessage) message;
		log.info("===recieve MathMessage:" + sm.getFirstNum() + " " + sm.getSymbol() + " " + sm.getSecondNum());
		AckMessage rm = new AckMessage();
		if (sm.getSymbol() == '+')
			rm.setResult(sm.getFirstNum() + sm.getSecondNum());
		if (sm.getSymbol() == '-')
			rm.setResult(sm.getFirstNum() - sm.getSecondNum());
		session.write(rm);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("===会话异常："+cause.getMessage());
		cause.printStackTrace();
		session.closeNow();
	}
}
