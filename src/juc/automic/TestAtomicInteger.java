package juc.automic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
/**
 * 测试AtomicInteger
 * @author donald
 * 2017年2月28日
 * 下午7:01:47
 */
public class TestAtomicInteger {
	public static void main(String[] args) {
		//测试 AtomicInteger
		AtomicInteger testInteger = new AtomicInteger(0);
		System.out.println("=======get:"+testInteger.get());
		System.out.println("=======addAndGet:"+testInteger.addAndGet(3));
		System.out.println("=======incrementAndGet:"+testInteger.incrementAndGet());
		System.out.println("=======decrementAndGet:"+testInteger.decrementAndGet());
		System.out.println("=======getAndAdd:"+testInteger.getAndAdd(2));
		System.out.println("=======getAndIncrement:"+testInteger.getAndIncrement());
		System.out.println("=======getAndDecrement:"+testInteger.getAndDecrement());
		System.out.println("=======getAndSet:"+testInteger.getAndSet(7));
		System.out.println("=======compareAndSet:"+testInteger.compareAndSet(7, 8));
		System.out.println("=======get:"+testInteger.get());
		System.out.println("=======compareAndSet:"+testInteger.weakCompareAndSet(8, 9));
		System.out.println("=======get:"+testInteger.get());
		testInteger.lazySet(6);
		System.out.println("=======get:"+testInteger.get());
		//测试 AtomicIntegerArray
		AtomicIntegerArray testArray = new AtomicIntegerArray(10);
		testArray.set(0, 1);
		System.out.println("Array=======get:"+testArray.get(0));
		System.out.println("Array=======getAndAdd:"+testArray.getAndAdd(0, 2));
		System.out.println("Array=======getAndIncrement:"+testArray.getAndIncrement(0));
		System.out.println("Array=======getAndDecrement:"+testArray.getAndDecrement(0));
		System.out.println("Array=======getAndSet:"+testArray.getAndSet(0, 5));
		System.out.println("Array=======incrementAndGet:"+testArray.incrementAndGet(0));
		System.out.println("Array=======decrementAndGet:"+testArray.decrementAndGet(0));
		System.out.println("Array=======addAndGet:"+testArray.addAndGet(0, 3));
				
	}
}
