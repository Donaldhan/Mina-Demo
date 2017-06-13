package nio.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;

/**
 * 测试读写文件
 * @author donald 
 * 2017年5月12日 
 * 下午11:33:14
 * @see java.io.FileInputStream#getChannel()//读模式
 * @see java.io.FileOutputStream#getChannel()//写模式
 * @see java.io.RandomAccessFile#getChannel()//以创建RandomAccessFile的模式为准，r，rw。
 */
public class testFile {
	private static File file = null;
	public static void main(String[] args) throws IOException {
         file = new File("E:/file/dir/test.txt");
         String pathStr = file.getPath();//路径中包括文件名和文件后缀
         System.out.println("文件路径："+pathStr);
         System.out.println("文件绝对路径："+file.getAbsolutePath());
         System.out.println("文件规范路径："+file.getCanonicalPath());//返回路径的规范格式，与系统有关
         System.out.println("文件父路径："+file.getParent());
         System.out.println("完整文件名："+file.getName());//默认返回的文件名是包括后缀的
         System.out.println("文件名："+file.getName().split("\\.")[0]);
         System.out.println("文件类型："+file.getName().split("\\.")[1]);
         System.out.println("文件分隔符："+file.separator);
         System.out.println("是否绝对路径："+file.isAbsolute());
         System.out.println("是否为目录："+file.isDirectory());
         System.out.println("是否为文件："+file.isFile());
         System.out.println("是否为隐藏文件："+file.isHidden());
         System.out.println("文件是否可读："+file.canRead());
         System.out.println("文件是否可写："+file.canWrite());
         System.out.println("文件是否可执行："+file.canExecute());
//         testMkdirs();

         if(file.exists()){
        	 file.delete();
         }
         if(!file.exists()){
        	 createFile(file);
         }
        testListRoots();
        testListFiles(file);
         /*
          * 这里的Path，就是我们在FileChannle定义文章中，要打开一个文件通道，要传入一个Path参数
          * FileChannel open(Path path,Set<? extends OpenOption> options,FileAttribute<?>... attrs)
          * */
         Path path = file.toPath();
         System.out.println("Path文件类型："+path.getClass());
         //FileOutputStream
         //第二个参数为true，表示从文件末尾开始写，只写模式
         FileOutputStream fileOutputStream = new FileOutputStream(file,true);
         String writeString = "test FileOutputStream write...";
         fileOutputStream.write(writeString.getBytes("UTF-8"));
         System.out.println("===写字节序列到文件输出流完成======");
//         fileOutputStream.getChannel();//获取写模式文件通道
         fileOutputStream.close();
         
         //FileInputStreams
         FileInputStream fileInputStream = new FileInputStream(file);
         int fisAvailable = fileInputStream.available();
         System.out.println("==文件输入流可用字节数："+fisAvailable);
         byte[] readBytes = new byte[fisAvailable];
         System.out.println("从文件输入流读取字节数："+fileInputStream.read(readBytes));
         System.out.println("从文件输入流内容："+new String(readBytes,"UTF-8"));
//         fileInputStream.getChannel();//获取读模式文件通道
         fileInputStream.close();
         
        /* FileOutput/InputStream,主要用于读写文件，面向字节流的*/
         
         //BufferedOutputStream
         BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file,true));
         bufferedOutputStream.write(new String("test BufferedOutputStream write...").getBytes("UTF-8"));
         System.out.println("===写字节序列到缓冲区输出流======");
         bufferedOutputStream.flush();
         System.out.println("===刷新缓冲区字节序列到输出流完毕======");
         bufferedOutputStream.close();
         
         //BufferedInputStream
         BufferedInputStream  bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
         int bisAvailable = bufferedInputStream.available();
         System.out.println("==缓存区输入流可用字节数："+bisAvailable);
         byte[] readBytesfromBuffer = new byte[bisAvailable];
         System.out.println("从缓存区输入流读取字节数："+bufferedInputStream.read(readBytesfromBuffer));
         System.out.println("从缓存区输入流内容："+new String(readBytesfromBuffer,"UTF-8"));
         bufferedInputStream.close();
         
         /*
          * BufferedInputStream，BufferedOutputStream，也可以用于读写socket（Bio）输入/出流
          * 针对字节流，在写字节序列，先放入缓冲区中，调用flush方法，可以缓冲区字节序列更新到底层输出流
         */
         
         /*
          *  public DataOutputStream(OutputStream out) {
		  *     super(out);
		  *   }
          */
         //DataOutputStream构造中的OutputStream，可以为socket（Bio）输出流，或FileInputStream  
         DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file,true));;
         dataOutputStream.close();
         /*
          *  public DataInputStream(InputStream in) {
		  *     super(in);
		  * }
          */
         //DataInputStream构造中的InputStream，可以为socket（Bio）输入流，或FileInputStream
         DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
         dataInputStream.close();
         /*
          * DataInputStream,DataOutputStream,除了处理字节序列之外，也可以原始类型Int，Char，Long，Double，
          * Float,Boolean等
          */
         
      
//       ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file,true));
//       objectOutputStream.writeObject(obj);
//       objectOutputStream.close();
//       ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
//       objectInputStream.readObject();
//       objectInputStream.close();  

         /*
          * ObjectOutput/InputStream构造中的Output/InputStream，可以为socket（Bio）输出流/输入流，
          * 或FileOutput/InputStream,ObjectOutput/InputStream除了可以处理字节序列之外，也可以原始类型Int，
          * Char，Long，Double，Float,Boolean等，主要用于对象的序列化和反序列化;
          * 这部分测试的话，最好先写一个对象到文件，然后在读取，因为写对象的时候，要将魔数等信息写进去
          */
           System.out.println("=======测试Writer，Reader=====");
           if(file.exists()){
          	 file.delete();
           }
           if(!file.exists()){
          	 createFile(file);
           }
           FileWriter fileWriter = new FileWriter(file, true);
           fileWriter.write("test FileWriter write...");
           fileWriter.flush();
           fileWriter.close();
           System.out.println("==fileWriter写字符串完毕");
           FileReader fileReader = new FileReader(file);
           char[] readChars = new char[24];
           if(fileReader.ready()){
        	   System.out.println("==fileReader读取字符数:"+fileReader.read(readChars));
        	   System.out.println("==fileReader读取结果:"+new String(readChars));
           }
           fileReader.close();
           
           /* FileWriter/Reader,主要用于读写文件，面向字符流的*/
           
         /*
          * public OutputStreamWriter(OutputStream out, String charsetName)
          */
         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8");
         BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
         bufferedWriter.newLine();
         bufferedWriter.write("test BufferedWriter write...");
         bufferedWriter.newLine();
         bufferedWriter.flush();
         bufferedWriter.close();
         System.out.println("==bufferedWriter写字符串完毕");
         /*
          *  public InputStreamReader(InputStream in, String charsetName)
          */
         InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         if(bufferedReader.ready()){
        	 System.out.println("==bufferedReader读取一行字符串："+bufferedReader.readLine());
             System.out.println("==bufferedReader读取一行字符串："+bufferedReader.readLine());
         }
         bufferedReader.close();
         /*
          * BufferedWriter/Reader,读写文件，可以将文件先包装成FileOutput/InputStream,
          * 再根据FileOutput/InputStream，构造OutputStreamWriter/InputStreamReader,
          * 最后OutputStreamWriter/InputStreamReader构造BufferedWriter/Reader,就可以
          * 通过BufferedWriter/Reader读写文件；从上面可以看出OutputStreamWriter/InputStreamReader
          * 的构造关键参数为Output/InputStream,我们从Socket（BIO），获取socket输入流和输出流，
          * 包装成OutputStreamWriter/InputStreamReader，那么我们就可以通过BufferedWriter/Reader
          * 读socket输入流和写socket输出流。
          */
         RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
         //这里就不具体介绍了，F3，看看具体非方法声明，就知道怎么回事了
//       randomAccessFile.getChannel();//获取读写模式文件通道
         randomAccessFile.close();
         /**
          * 前面无论是面向字节的，还是面向字符的流，都是读写分离的，而RandomAccessFile,是
          * 可读可写的；可写字节序列，原始类型，可读字节序列，原始类型，按行读取；
          * 
          */ 
         
//         FileChannel fileChannel = randomAccessFile.getChannel();
         /**
          * 这种方式为通过通道读写文件，在前面的文章以说，这里不再说
          */
         File tfile = File.createTempFile("testFile", ".tmp", new File("E:/file/dir/"));
         if(tfile.exists()){
        	 System.out.println("==创建临时文件成功："+tfile.getName());
         }
         tfile.deleteOnExit();//针对临时文件
	}
	/**
	 * 测试创建文件目录方法
	 */
	public static void testMkdirs(){
		 if(!file.exists()){
        	 System.out.println("========文件不存在========");
        	 System.out.println("========创建文件路径："+file.mkdir());
         }
		 if(!file.exists()){
        	 System.out.println("========文件不存在========");
        	//当父路径不存在时，循环创建父类路径,包括文件名和后缀
        	 System.out.println("========循环创建父类路径："+file.mkdirs());
         }
	}
	/**
	 * 创建文件
	 * @param f
	 * @return
	 * @throws IOException 
	 */
	public static File createFile(File f) throws IOException{
		 if(!f.exists()){
        	 //创建文件时，如果文件父路径不存在，则应先创建创建路径，然后再创建文件
        	 f.getParentFile().mkdirs();
        	 if(f.getParentFile().exists()){
        		 System.out.println("========创建文件："+f.createNewFile());
        		 System.out.println("创建文件过后，是否为文件："+file.isFile());
        		 System.out.println("========是否为隐藏文件："+file.isHidden());
        		 System.out.println("========文件是否可读："+file.canRead());
        		 System.out.println("========文件是否可写："+file.canWrite());
                 System.out.println("========文件是否可执行："+file.canExecute());
        	 }
         }
		 return f;
	}
	/**
	 * 获取文件系统的更目录，unix下，根目录为/
	 * windows为C:，D:,E:...
	 */
	public static void testListRoots(){
		 System.out.println("文件系统根目录size："+File.listRoots().length);
         for(File tfile : File.listRoots()){
        	 System.out.print(tfile.getPath()+",");
         }
         System.out.println();
	}
	/**
	 * 获取文件当前目录下的文件
	 * @param lf
	 */
	public static void testListFiles(File lf){
		File parentFile = lf.getParentFile();
	    System.out.println("当前文件目录下的文件size："+parentFile.listFiles().length);
	    for(File tfile : parentFile.listFiles()){
	   	 System.out.print(tfile.getPath()+",");
	    }
	    System.out.println();
	}
	
    
}
