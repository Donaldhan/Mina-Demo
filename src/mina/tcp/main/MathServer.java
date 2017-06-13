package mina.tcp.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.coder.MathProtocolCodecFactory;
import mina.tcp.handler.MathServerHandler;
/**
 * MathServer
 * @author donald
 * 2017年5月19日
 * 下午10:16:29
 */
public class MathServer {
	private static final Logger log = LoggerFactory.getLogger(MathServer.class);
	private static final  String ip = "10.16.7.107";
	private static final  int port = 9122;
	private static final  int readBufferSize = 2048;
	private static final  int idleTime = 10;
	public static void main(String[] args) throws IOException {
		 IoAcceptor acceptor=new NioSocketAcceptor();
		//配置socket会话
		 SocketSessionConfig socketSessionConfig = (SocketSessionConfig) acceptor.getSessionConfig();
		 socketSessionConfig.setReadBufferSize(readBufferSize);
		 socketSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE,idleTime);
		 //配置过滤器
		 DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = acceptor.getFilterChain();
		 LoggingFilter loggingFilter = new LoggingFilter();
		 defaultIoFilterChainBuilder.addLast("loggingFilter", loggingFilter);
		 MathProtocolCodecFactory mathProtocolCodecFactory = new MathProtocolCodecFactory(true);
		 ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(mathProtocolCodecFactory);
		 defaultIoFilterChainBuilder.addLast("protocolCodecFilter",protocolCodecFilter);
		 //配置NioSocketAcceptor处理器
		 MathServerHandler mathServerHandler = new MathServerHandler();
		 acceptor.setHandler(mathServerHandler);
		 InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
		 acceptor.bind(inetSocketAddress);
		 log.info("=========MathServer is start============");
	}
}
