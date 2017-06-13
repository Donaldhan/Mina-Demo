package Serializable;

import java.io.Serializable;
/**
 * 
 * @author donald
 * 2017年2月16日
 * 下午6:37:13
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9122096642444363706L;
	private String name;
	private Integer age;
	private transient String sex;
	
	public Person() {
		super();
		System.out.println("==========无参构造");
	}
	public Person(String name, Integer age, String sex) {
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
}
