package Serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 测试重写序列化与反序列化方法
 * @author donald
 * 2017年2月16日
 * 下午6:48:58
 */
public class TestSerializableX {
	public static void main(String[] args) {
		File file = new File("E:/personx.out");
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
		PersonX person = new PersonX("donald", 27, "man");
		try {
			objectOutputStream.writeObject(person);
			objectOutputStream.writeInt(4);
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
		PersonX getPerson = null;
		try {
			getPerson = (PersonX) objectInputStream.readObject();
			int int0 = objectInputStream.readInt();
			System.out.println("=======read int after read object persion:"+int0);
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
