package mina.tcp.message;
/**
 * 计算协议
 * @author donald
 * 2017年5月20日
 * 下午11:48:08
 */
public class MathMessage {
	private int firstNum = 0;
	private int secondNum = 0;
	private char symbol = '+';
	public char getSymbol() {
		return symbol;
	}
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	public int getFirstNum() {
		return firstNum;
	}
	public void setFirstNum(int firstNum) {
		this.firstNum = firstNum;
	}
	public int getSecondNum() {
		return secondNum;
	}
	public void setSecondNum(int secondNum) {
		this.secondNum = secondNum;
	}
	
}
