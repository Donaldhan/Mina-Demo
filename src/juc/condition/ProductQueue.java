package juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition实现的生产者，消费者模型
 * 
 * @author donald 2017年3月2日 下午7:02:17
 * @param <T>
 */
public class ProductQueue<T> {
	private final T[] items;
	private final Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();// 队列非满条件
	private Condition notEmpty = lock.newCondition();// 队列非空条件
	//队列头，尾，当前容量
	private int head, tail, count;
    /**
     * 
     * @param maxSize
     */
	@SuppressWarnings("unchecked")
	public ProductQueue(int maxSize) {
		items = (T[]) new Object[maxSize];
	}

	public ProductQueue() {
		this(10);
	}
    /**
     * 生产
     * @param t
     * @throws InterruptedException
     */
	public void put(T t) throws InterruptedException {
		lock.lock();
		try {
			while (count == getCapacity()) {
				// ReentrantLock is oweself, await for realse the lock and put up
				// the thread
				// when the condition is satsfy ,the get the lock and run
				//如果当前队列已满，则等待不满条件
				notFull.await();
			}
			//添加到队列尾部
			items[tail] = t;
			if (++tail == getCapacity()) {
				//如果队列满，则将队列尾，执行队列，第一个槽
				tail = 0;
			}
			//增加队列元素个数
			++count;
			//释放非空信号，通知所有持有当前锁lock，并等待消费的线程
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
   /**
    * 消费
    * @return
    * @throws InterruptedException
    */
	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				//如果队列为空，则等待非空条件
				notEmpty.await();
			}
			T ret = items[head];
			//取走队列头元素，并清空
			items[head] = null;// help GC
			//队列元素被取万，则head指向队列头
			if (++head == getCapacity()) {
				head = 0;
			}
			//减少队列元素数量
			--count;
			//释放非满信号，通知所有持有当前锁lock，并等待生产的线程
			notFull.signalAll();
			return ret;
		} finally {
			lock.unlock();
		}
	}
	/**
	 * 
	 * @return
	 */
	public int getCapacity() {
		return items.length;
	}
   /**
    * 
    * @return
    */
	public int size() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

}
