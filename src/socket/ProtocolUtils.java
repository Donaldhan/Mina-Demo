package socket;
/**
 * 协议辅助工具
 * @author donald
 * 2017年2月11日
 * 下午12:05:13
 */
public class ProtocolUtils {
	private static volatile ProtocolUtils instance = null;
	public static synchronized ProtocolUtils getInstance(){
		if(instance == null){
			instance = new ProtocolUtils();
		}
		return instance;
	}
	/**
	 * 如果orgStr的长度不够length，左侧填充0
	 * @param orgStr
	 * @param length
	 * @return
	 */
	public String fillString(String orgStr, int length){
		if(orgStr.length() < length){
        	int orgStrLength = orgStr.length();
        	int zeroNum = length - orgStrLength;
        	for(int i=0; i< zeroNum; i++){
        		orgStr = "0" + orgStr;
        	}
        }
		return orgStr;
	}
	public static void main(String[] args) {
		 int firstNum = 15;
         String firstNumStr = String.valueOf(firstNum);
         if(firstNumStr.length() <= 10){
        	 firstNumStr = ProtocolUtils.getInstance().fillString(firstNumStr,10);
         }
         System.out.println("=======firstNumStr:"+firstNumStr);
         System.out.println("=======firstNumStr Integer Value:"+Integer.valueOf(firstNumStr));
	}
}
