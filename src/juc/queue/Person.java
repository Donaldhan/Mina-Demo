package juc.queue;

import java.io.Serializable;
/**
 * 
 * @author donald
 * 2017年3月23日
 * 上午11:44:11
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6217522934802700810L;
	
	public Person(String name) {
		super();
		this.name = name;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
