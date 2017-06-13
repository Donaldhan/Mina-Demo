package socket;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
/**
 * 测试ByteBuffer
 * @author donald
 * 2017年2月14日
 * 下午5:23:32
 */
public class TestByteBuffer {
	private static ByteBuffer byteBuffer = null;
	public  static void main(String[] args) {
		    /* 以下顺序不要改变*/
			initByteBuffer();
			testByte(); 
			testChar(); 
//			testMark();
			testInt();
			testFloat(); 
			testDouble();
			testLong();
			testRemaining();
			testOverFlow();
//			testReset();
//			testClear();
			testCompact();
	}
	/**
	 * 初始化缓存空间
	 */
	 public static void initByteBuffer(){
		 byteBuffer  = ByteBuffer.allocate(32);
         System.out.println("===============init status============");
         System.out.println("position:"+byteBuffer.position());
         System.out.println("limit:"+byteBuffer.limit());
         System.out.println("capacity:"+byteBuffer.capacity());
	  }
	 /**
	  * 测试Byte，占用一个字节
	  */
	 public static void testByte(){
   	  	 System.out.println("===============put byte============");
         //字节
         byte bbyte = 102;
         byteBuffer.put(bbyte);//ByteBuffer
         byteBuffer.get(0);//byte
         System.out.println("position:"+byteBuffer.position());
         System.out.println("limit:"+byteBuffer.limit());
         System.out.println("capacity:"+byteBuffer.capacity());
         System.out.println("======get byte:"+byteBuffer.get(0));
     }
	 /**
	  * 测试Char，占用2个字节
	  */
	 public static void testChar(){
   	  System.out.println("===============put char============");
         //字符
         char aChar= 'a';
         byteBuffer.putChar(aChar);
         System.out.println("position:"+byteBuffer.position());
         System.out.println("limit:"+byteBuffer.limit());
         System.out.println("capacity:"+byteBuffer.capacity());
         System.out.println("======get Char:"+byteBuffer.getChar(1));
     }
	 /**
	  * 标记位置，以便reset，返回这个标记位置
	  */
	  public static void testMark(){
    	  //标记位置
          byteBuffer.mark();
          System.out.println("===============mark============");
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
      }
	  /**
	   * 测试int，占用4个字节
	   */
	  public static void testInt(){
    	  System.out.println("===============put int============");
          //int
          int int4 = 4;
          byteBuffer.putInt(int4);
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          //这里为什么从第三个字节开始读取，因为前面一个字节和一个字符总共三个字节
          System.out.println("======get int:"+byteBuffer.getInt(3));
      }
	  /**
	   * 测试float，占用4个字节
	   */
	  public static void testFloat(){
    	  System.out.println("===============put float============");
          //float
          float float5 = 10;
          byteBuffer.putFloat(float5);
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          //这里为什么从第7个字节开始读取，因为前面一个字节和一个字符，一个int总共7个字节
          System.out.println("======get float:"+byteBuffer.getFloat(7));
      }
	  /**
	   * 测试double，占用8个字节
	   */
	  public static void testDouble(){
    	  System.out.println("===============put double============");
          //double
          double double6 = 20.0;
          byteBuffer.putDouble(double6);
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          //这里为什么从第11个字节开始读取，因为前面一个字节和一个字符，一个int,一个float总共11个字节
          System.out.println("======get double:"+byteBuffer.getDouble(11));
      }
	  /**
	   * 测试Long，占用8个字节
	   */
	  public static void testLong(){
    	  System.out.println("===============put long============");
          //long
          long long7 = (long) 30.0;
          byteBuffer.putLong(long7);
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          //这里为什么从第19个字节开始读取，因为前面一个字节和一个字符，一个int,一个float，一个double总共19个字节
          System.out.println("======get long:"+byteBuffer.getLong(19));
      }
	  /**
	   * 测试字节缓冲的剩余空间函数
	   */
	  public static void testRemaining(){
    	  System.out.println("======buffer 剩余空间大小:"+byteBuffer.remaining());
      }
	  /**
	   * 测试添加元素字节长度，大于剩余空间时的情况
	   */
	  public static void testOverFlow(){
    	  /*Exception in thread "main" java.nio.BufferOverflowException
	      	at java.nio.Buffer.nextPutIndex(Buffer.java:519)
	      	at java.nio.HeapByteBuffer.putLong(HeapByteBuffer.java:417)
	      	at socket.TestByteBuffer.main(TestByteBuffer.java:60)
	      	超出空间，则抛出BufferOverflowException异常
	      	*/
         try{
      	   byteBuffer.putLong((long)30.0);
         }
         catch(BufferOverflowException e){
      	   e.printStackTrace();
         }
      }
	  /**
	   * 测试回到标记，position为标记的mark
	   */
	  public static void testReset(){
     	 System.out.println("===============reset============");
          //回到mark标记位置，position为标记的mark
          byteBuffer.reset();
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          System.out.println("======get  int from mark:"+byteBuffer.getInt(3));
          //重新，从标记位置put一个int值，原来的内容被覆盖掉
          int int5 = 5;
          byteBuffer.putInt(int5);
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit());
          System.out.println("capacity:"+byteBuffer.capacity());
          System.out.println("======get int from mark after put new int value:"+byteBuffer.getInt(3));
      }
	  /**
	   * clear重置position，mark，limit位置，原始缓存区内容并不清掉
	   */
	  public static void testClear(){
    	  System.out.println("===============clear============");
          //clear重置position，mark，limit位置，原始缓存区内容并不清掉
          byteBuffer.clear();
          System.out.println("position:"+byteBuffer.position());
          System.out.println("limit:"+byteBuffer.limit()); 
          System.out.println("capacity:"+byteBuffer.capacity());
          System.out.println("======get int  after clear:"+byteBuffer.getInt(3));
          
      }
	  
	  public static void testCompact(){
			 System.out.println("===============compact============");
	            /*
	             * compact操作用于当
	             *  while (in.read(buf) >= 0 || buf.position != 0) {
	             *     buf.flip();
	             *     out.write(buf);
	             *     buf.compact();    // In case of partial write
	             *    }
	             * 当out发送数据，即读取buf的数据，write方法可能只发送了部分数据，buf里还有剩余，
	             * 这时调用buf.compact()函数将position与limit之间的数据，copy到buf的0到limit-position，进行压缩（非实际以压缩，只是移动），
	             * 以便下次 向写入缓存。
	             */
	           
	            byteBuffer.compact();
	            System.out.println("position:"+byteBuffer.position());
	            System.out.println("limit:"+byteBuffer.limit());
	            System.out.println("capacity:"+byteBuffer.capacity());
	            System.out.println("======get int:"+byteBuffer.getInt(3));
	            System.out.println("===============flip============");
	           /* 
	            * buf.put(magic);    // Prepend header
	            * in.read(buf);      // Read data into rest of buffer
	            * buf.flip();        // Flip buffer
	            * out.write(buf); 
	            * 当in从缓冲中读取数据后，如果想要将缓存中的数据发送出去，则调用buf.flip()函数，limit为当前position，position为0，
	            */
//	            byteBuffer.flip();
	            System.out.println("===============rewind============");
	            /* 
	            * out.write(buf);    // Write remaining data
	            * buf.rewind();      // Rewind buffer
	            * buf.get(array);    // Copy data into array</pre></blockquote>
	            * 当out写出数据，即读取buf的数据后，如果想要从缓存中，从0位置，获取缓存数据，则调用buf.rewind()
	            */            
//	            byteBuffer.rewind();
	            
		}
      
}
