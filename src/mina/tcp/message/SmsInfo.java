package mina.tcp.message;


/**
 * 短信
 * @author donald 
 * 2017年5月19日 
 * 下午10:46:36
 */
public class SmsInfo {
	/** 发送者 */
	private String sender;
	/** 接受者 */
	private String receiver;
	/** 短信内容 */
	private String message;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
