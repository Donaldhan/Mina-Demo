package mina.udp.main;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.udp.handler.UdpServerHandler;
/**
 * NioDatagramAcceptor Server peer
 * @author donald
 * 2017年5月18日
 * 下午9:59:13
 */
public class UdpServer {
	private  static final Logger log = LoggerFactory.getLogger(UdpServer.class);
	private static final  String ip = "10.16.7.107";
	private static final  int port = 9122;
	
	public static void main(String[] args) throws Exception {
		IoAcceptor acceptor = new NioDatagramAcceptor();
		//配置会话Handler
		UdpServerHandler udpServerHandler = new UdpServerHandler();
		acceptor.setHandler(udpServerHandler);
		//配置过滤器
		DefaultIoFilterChainBuilder  defaultIoFilterChainBuilder = acceptor.getFilterChain();
		defaultIoFilterChainBuilder.addLast("logger", new LoggingFilter());
		//配置会话
		DatagramSessionConfig datagramSessionConfig = (DatagramSessionConfig) acceptor.getSessionConfig();
		datagramSessionConfig.setReuseAddress(true);
		//绑定地址
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
		acceptor.bind(inetSocketAddress);
		log.info("=========Udp Server peer is start...");
	}
}
