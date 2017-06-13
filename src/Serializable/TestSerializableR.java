package Serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 测试java序列化
 * @author donald
 * 2017年2月16日
 * 下午6:37:33
 */
public class TestSerializableR {
	public static void main(String[] args) {
		File file = new File("E:/person.out");
		FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PersonR person = PersonR.getInstance();
		try {
			//写persion
			objectOutputStream.writeObject(person);
			//写int
			objectOutputStream.writeInt(4);
			//写UTF编码格式的字符串
			objectOutputStream.writeUTF("it is a man");
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectInputStream objectInputStream  = null;
		try {
			objectInputStream = new ObjectInputStream(inFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PersonR getPerson = null;
		try {
			//读取对象
			getPerson = (PersonR) objectInputStream.readObject();
			System.out.println("=======Person is equal the one from readObject:"+getPerson.equals(person));
			//读取int
			int int0 = objectInputStream.readInt();
			System.out.println("=======read int after read object persion:"+int0);
			//读取UTF格式的字符串
			String str = objectInputStream.readUTF();
			System.out.println("=======read UTF after read object persion and int:"+str);
			objectInputStream.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(getPerson);
	}
}
