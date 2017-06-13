package mina.tcp.filter;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试过滤器
 * @author donald
 * 2017年5月19日
 * 上午8:59:29
 */
public class TestFilter implements IoFilter {
	private static final Logger log = LoggerFactory.getLogger(TestFilter.class);
	@Override
	public void onPreAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
		log.debug("$$$$$$$$onPreAdd...");
	}
	@Override
	public void onPostAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
		log.debug("$$$$$$$$onPostAdd...");
	}
	@Override
	public void init() throws Exception {
		log.debug("$$$$$$$$init...");
	}
	@Override
	public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
		log.debug("$$$$$$$$filterWrite...");
		nextFilter.filterWrite(session, writeRequest);
	}
	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
		log.debug("$$$$$$$$sessionCreated...");
		nextFilter.sessionCreated(session);
	}
	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
		log.debug("$$$$$$$$sessionIdle...");
		nextFilter.sessionIdle(session, status);
	}
	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
		log.debug("$$$$$$$$sessionOpened...");
		nextFilter.sessionOpened(session);
	}
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		log.debug("$$$$$$$$messageReceived...");
		nextFilter.messageReceived(session, message);
	}
	@Override
	public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
		log.debug("$$$$$$$$messageSent...");
		nextFilter.messageSent(session, writeRequest);
	}
	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws Exception {
		log.debug("$$$$$$$$exceptionCaught...");
		nextFilter.exceptionCaught(session, cause);
	}
	@Override
	public void onPostRemove(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
		log.debug("$$$$$$$$onPostRemove...");
	}
	
	@Override
	public void onPreRemove(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
		log.debug("$$$$$$$$onPreRemove...");
	}
	@Override
	public void inputClosed(NextFilter arg0, IoSession arg1) throws Exception {
		log.debug("$$$$$$$$inputClosed...");
	}
	@Override
	public void filterClose(NextFilter nextFilter, IoSession session) throws Exception {
		log.debug("$$$$$$$$filterClose...");
		nextFilter.filterClose(session);
	}
	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
		log.debug("$$$$$$$$sessionClosed...");
		nextFilter.sessionClosed(session);
	}
	@Override
	public void destroy() throws Exception {
		log.debug("$$$$$$$$destroy...");
	}
}
