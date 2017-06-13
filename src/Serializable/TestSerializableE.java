package Serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 测试Externalizable接口，序列化
 * @author donald
 * 2017年2月16日
 * 下午6:56:27
 */
public class TestSerializableE {
	public static void main(String[] args) {
		File file = new File("E:/persone.out");
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
		PersonE person = new PersonE("donald", 27, "man");
		try {
			objectOutputStream.writeObject(person);
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
		PersonE getPerson = null;
		try {
			getPerson = (PersonE) objectInputStream.readObject();
			objectInputStream.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("=====read Object from file"+getPerson);
	}
}
