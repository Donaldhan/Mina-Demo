package mina.udp.main;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.udp.handler.UdpClientHandler;
/**
 * NioDatagramConnector Client peer
 * @author donald
 * 2017年5月18日
 * 下午10:19:35
 */
public class UdpClient {
	private  static final Logger log = LoggerFactory.getLogger(UdpClient.class);
	private static final String ip = "10.16.7.107";
	private static final int port = 9122;
	public static void main(String[] args) throws Exception {
		IoConnector connector = new NioDatagramConnector();
		//配置会话Handler
		UdpClientHandler udpClientHandler = new UdpClientHandler();
		connector.setHandler(udpClientHandler);
		//配置过滤器
		DefaultIoFilterChainBuilder  defaultIoFilterChainBuilder = connector.getFilterChain();
		defaultIoFilterChainBuilder.addLast("logger", new LoggingFilter());
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
		//连接远端peer
		ConnectFuture connFuture = connector.connect(inetSocketAddress);
		connFuture.awaitUninterruptibly();
		log.info("=========Udp Client peer is start...");
	}
}
