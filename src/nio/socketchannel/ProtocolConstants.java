package nio.socketchannel;

/**
 * 协议常量
 * @author donald
 * 2017年4月13日
 * 下午10:49:27
 */
public class ProtocolConstants {
	/**
	 * 加法协议编码
	 */
	public static final String SUM_PROTOCOL_300000 = "300000";
	/**
	 * 乘法协议编码
	 */
	public static final String MULTI_PROTOCOL_300100 = "300100";
	/**
	 * 计算结果
	 */
	public static final String ACK_PROTOCOL_300200 = "300200";
	/**
	 * 服务器解析协议失败
	 */
	public static final String ACK_PROTOCOL_300300 = "300300";
	/**
	 * 协议编码长度
	 */
	public static final int PROTOCOL_CODE_LENGTH = 6;
	/**
	 * 协议操作数长度
	 */
	public static final int OPERATE_NUM_LENGTH = 4;
	/**
	 * 字符集
	 */
	public static final String CHARSET_UTF8 = "UTF-8";
}
