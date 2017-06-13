package test;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author donald
 * 2017年4月20日
 * 下午12:31:02
 */
public class Test {
	private static final Logger log = LoggerFactory.getLogger(Test.class);
	public static void main(String[] args) {
		long t1 = 0L;
		long t2 = 0L;
		System.out.println(t1<t2);
		InetSocketAddress inetSocketAddress = new InetSocketAddress("10.16.7.107",10010);
		log.info("==getHostName:"+inetSocketAddress.getHostName());
		log.info("==getHostString:"+inetSocketAddress.getHostString());
		log.info("==getAddress().getHostAddress():"+inetSocketAddress.getAddress().getHostAddress());
		
	}
}
