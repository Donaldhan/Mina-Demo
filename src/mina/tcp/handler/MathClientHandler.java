package mina.tcp.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.message.AckMessage;
import mina.tcp.message.MathMessage;
/**
 * MathClientHandler
 * @author donald
 * 2017年5月20日
 * 下午11:48:48
 */
public class MathClientHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(MathClientHandler.class);
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		MathMessage sm = new MathMessage();
		sm.setFirstNum(100);
		sm.setSecondNum(99);
		sm.setSymbol('-');
		session.write(sm);
		sm.setSymbol('+');
		session.write(sm);
		log.info("====计算消息已发送");
	}
	@Override
	public void messageReceived(IoSession session, Object message) {
		AckMessage rs = (AckMessage) message;
		log.info("====calculate result:"+rs.getResult());
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("===会话异常："+cause.getMessage());
		cause.printStackTrace();
		session.closeNow();
	}
}
