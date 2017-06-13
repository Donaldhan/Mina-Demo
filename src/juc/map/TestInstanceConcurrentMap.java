package juc.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 用ConcurrentHashMap实现单例模式
 * @author donald
 * 2017年3月11日
 * 下午6:18:09
 */
public class TestInstanceConcurrentMap {
		private static final ConcurrentMap<String, TestInstanceConcurrentMap> map = new ConcurrentHashMap<String, TestInstanceConcurrentMap>();
		private static TestInstanceConcurrentMap instance;
		/**
		 * 
		 * @return
		 */
		public static TestInstanceConcurrentMap getInstance() {
			if (instance == null) {
				map.putIfAbsent("INSTANCE", new TestInstanceConcurrentMap());
				instance = map.get("INSTANCE");
			}
			return instance;
		}
		/**
		 * 
		 */
		private TestInstanceConcurrentMap() {
		}
}
