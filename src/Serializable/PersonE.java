package Serializable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
/**
 * 继承Externalizable实体类
 * @author donald
 * 2017年2月16日
 * 下午6:55:37
 */
public class PersonE implements Externalizable  {
	
	private String name;
	private Integer age;
	private transient String sex;
	
	public PersonE() {
		super();
		System.out.println("==========无参构造");
	}
	public PersonE(String name, Integer age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		System.out.println("==========有参构造");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
    public String toString(){
    	return "["+this.name+","+this.age+","+this.sex+"]";
    }
    private void writeObject(ObjectOutputStream out) throws IOException {  
        out.defaultWriteObject();  
        out.writeUTF(this.sex);  
    }  
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {  
        in.defaultReadObject();  
        this.sex = in.readUTF();  
    }
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.name);  
        out.writeInt(this.age);  
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.name = (String) in.readObject();  
        this.age = in.readInt(); 
	}  
}
