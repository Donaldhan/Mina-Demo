package mina.tcp.main;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mina.tcp.coder.CumulativeSmsCodecFactory2;
import mina.tcp.handler.SmsClientHandler2;
/**
 * SmsClient
 * @author donald
 * 2017年5月19日
 * 下午10:27:30
 */
public class SmsClient2 {
	private static final Logger log = LoggerFactory.getLogger(SmsClient2.class);
	private static final  String ip = "10.16.7.107";
	private static final  int port = 9122;
	private static final  int connectTimeoutMillis = 30000;
	private static final Charset charset = Charset.forName("UTF-8");
	public static void main(String[] args) {
		IoConnector connector=new NioSocketConnector();
		 connector.setConnectTimeoutMillis(connectTimeoutMillis);
		//配置过滤器
		 DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = connector.getFilterChain();
		 LoggingFilter loggingFilter = new LoggingFilter();
		 defaultIoFilterChainBuilder.addLast("loggingFilter", loggingFilter);
		 CumulativeSmsCodecFactory2 cmccSipcCodecFactory2 = new CumulativeSmsCodecFactory2(charset);
		 ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(cmccSipcCodecFactory2);
		 defaultIoFilterChainBuilder.addLast("protocolCodecFilter",protocolCodecFilter);
		//配置NioSocketConnector处理器
		 SmsClientHandler2 smsClientHandler2 = new SmsClientHandler2();
		 connector.setHandler(smsClientHandler2);
		 InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,port);
		 connector.connect(inetSocketAddress);
		 log.info("=========SmsClient2 is start============");
	}
}
