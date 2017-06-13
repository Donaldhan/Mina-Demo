package Serializable;

import java.io.ObjectStreamException;
import java.io.Serializable;
/**
 * 
 * @author donald
 * 2017年2月16日
 * 下午6:37:13
 */
public class PersonR implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9122096642444363706L;
	private static volatile PersonR instance= null;
	private String name;
	private Integer age;
	private String sex;
	public static synchronized PersonR getInstance(){
		if(instance == null){
			instance = new PersonR("donald", 27, "man");
		}
		return instance;
	}
	public PersonR() {
		super();
		System.out.println("==========无参构造");
	}
	public PersonR(String name, Integer age, String sex) {
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
  /*  private Object readResolve() throws ObjectStreamException {  
        return getInstance();  
    }  */
}
