package juc.asserts;
/**
 * 
 * @author donald
 * 2017年3月3日
 * 上午8:57:56
 */
public class TestAssert {
	public static void main(String[] args) {
		int a = 1;
		assert a < 0;//正确往下执行，否则中断程序
        System.out.println("assert int a value:"+a);
        //断言失败，输出断言表达式的错误信息
        assert a > 0:"assert int a value smaller then 0 is fail.";
        System.out.println("assert int a value smaller then 0 is ok");
        
        
	}
}
