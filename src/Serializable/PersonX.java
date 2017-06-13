package Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PersonX implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -7261964764908521302L;
	private String name;
	private Integer age;
	private transient String sex;
	
	public PersonX() {
		super();
		System.out.println("==========无参构造");
	}
	public PersonX(String name, Integer age, String sex) {
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
    /**
     * 重写序列化方法
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {  
        out.defaultWriteObject();  
        //关键在这里，在序列化obejct后，序列化sex属性
        out.writeUTF(this.sex);  
    }  
    /**
     * 重写反序列化方法
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {  
        in.defaultReadObject();  
        //关键在这里，在反序列化obejct后，反序列化sex属性
        this.sex = in.readUTF();  
    }  
}
