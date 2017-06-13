package mina.tcp.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.util.ReferenceCountingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mina.tcp.filter.TestFilter;
import mina.tcp.handler.SimpleServerHandler;
/**
 * Simple Server With TestFilter
 * @author donald
 * 2017年5月19日
 * 上午8:59:37
 */
public class SimpleServerWithTestFilter {
	private static final Logger log = LoggerFactory.getLogger(SimpleServerWithTestFilter.class);
	private static final  String ip = "10.16.7.107";
	private static final  int port = 9122;
	private static final  int readBufferSize = 2048;
	private static final  int idleTime = 10;
	private static final Charset charset = Charset.forName("UTF-8");
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
		 TextLineCodecFactory textLineCodecFactory = 
		         new TextLineCodecFactory(charset,LineDelimiter.WINDOWS.getValue(),
				 LineDelimiter.WINDOWS.getValue());
		 ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(textLineCodecFactory);
		 defaultIoFilterChainBuilder.addLast("protocolCodecFilter",protocolCodecFilter);
		 TestFilter testFilter = new TestFilter(); 
		 ReferenceCountingFilter referenceCountingFilter = new ReferenceCountingFilter(testFilter);
		 defaultIoFilterChainBuilder.addLast("testFilter",referenceCountingFilter);
		//配置NioSocketAcceptor处理器
		 SimpleServerHandler simpleServerHandler = new SimpleServerHandler();
		 acceptor.setHandler(simpleServerHandler);
		 InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
		 acceptor.bind(inetSocketAddress);
		 log.info("=========SimpleServer is start============");

	}
}
