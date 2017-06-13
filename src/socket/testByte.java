package socket;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 辅助测试
 * @author donald
 * 2017年2月12日
 * 下午5:12:28
 */
public class testByte {
	public static void main(String[] args) {
		
	    try {
	    	String end = "\r\n";
			byte[] endBytes = end.getBytes("UTF-8");
			System.out.println("====endBytes Length:"+endBytes.length);
			String endStr = new String(endBytes,"UTF-8");
			System.out.println("是否结束:"+endStr.equals("\r\n"));
			System.out.println("====结束符:"+endStr);
			
			String sumProtocol = "300000";
			byte[] sumProtocolBytes = sumProtocol.getBytes("UTF-8");
			System.out.println("====sumProtocol Length:"+sumProtocolBytes.length);
			String sumProtocolStr = new String(sumProtocolBytes,"UTF-8");
			System.out.println("====加法协议:"+sumProtocolStr);
			
			String multiProtocol = "3000100";
			byte[] multiProtocolBytes = multiProtocol.getBytes("UTF-8");
			System.out.println("====multiProtocol Length:"+multiProtocolBytes.length);
			String multiProtocolStr = new String(multiProtocolBytes,"UTF-8");
			System.out.println("====乘法协议:"+multiProtocolStr);
			
			
			
			 int firstNum = 15;
             String firstNumStr = String.valueOf(firstNum);
             //如果第一个操作符不够长度，则补零
             if(firstNumStr.length()<10){
             	int NumLength = firstNumStr.length();
             	int zeroNum = 10 - NumLength;
             	String zeroNumStr="";
             	for(int i=0; i< zeroNum; i++){
             		zeroNumStr += "0";
             	}
             	firstNumStr = zeroNumStr + firstNumStr;
             }
             System.out.println("补零后的字符串："+firstNumStr);
			
             
             try {
				InetAddress host = InetAddress.getLocalHost();
				System.out.println("主机名："+host.getHostName());
				System.out.println("主机地质："+host.getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	 
}
