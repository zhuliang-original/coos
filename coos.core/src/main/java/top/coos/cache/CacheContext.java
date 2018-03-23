package top.coos.cache;

import java.util.Set;

import top.coos.cache.factory.CacheContextFactory;
import top.coos.cache.proxy.CacheProxy;
import top.coos.cache.proxy.MapProxy;

@SuppressWarnings("unchecked")
public abstract class CacheContext<K, V> {

	private CacheProxy proxy = getProxy();

	private Object lock = new Object();

	public static <T> T get(Class<?> clazz) {

		return CacheContextFactory.get(clazz);
	}

	public V get(K key) {

		synchronized (lock) {
			return (V) proxy.get(key);
		}
	}

	public void put(K key, V value) {

		synchronized (lock) {
			proxy.set(key, value);
		}
	}

	public int size() {

		synchronized (lock) {
			return proxy.size();
		}

	}

	public Set<K> keySet() {

		synchronized (lock) {
			return (Set<K>) proxy.keySet();
		}
	}

	// 根据 key 来删除缓存中的一条记录
	public void remove(K key) {

		synchronized (lock) {
			proxy.remove(key);
		}
	}

	// 清空缓存中的所有记录
	public void clear() {

		synchronized (lock) {
			proxy.clear();
		}
	}

	/**
	 * 是否是单例
	 * 
	 * @return
	 */
	public abstract boolean isSingleton();

	protected CacheProxy getProxy() {

		return new MapProxy();
	}
}
