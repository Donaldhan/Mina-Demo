package juc.queue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * 测试集合类
 * @author donald
 * 2017年3月15日
 * 下午9:50:23
 */
public class TestQueue {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		ConcurrentLinkedQueue conCurrentLinkedQueue = null;
		//双向阻塞队列
		LinkedBlockingDeque linkedBlockingDeque = null;
		//双向的同步队列SynchronousQueue
		Exchanger exchanger = null;
		Set<Person> set = null;
		List<Person> list = null;
		Vector vector = null;
		Set<Person> synchronizedSet = Collections.synchronizedSet(set);
		List<Person> synchronizedList = Collections.synchronizedList(list);
//		Collections.unmodifiableList(list)
//		Collections.singletonList(o)
//		Collections.emptyList()
//		Collections.checkedList(list, type)
		CopyOnWriteArrayList<Person> copyOnWriteArrayList = null;
		CopyOnWriteArraySet<Person> copyOnWriteArraySet = null;
	}
}
