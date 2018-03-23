package top.coos.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 连接池工具类
 * 
 * @author CLQ
 *
 */
public final class RedisPool {

	private JedisPool jedisPool = null;
	private String host;
	private int port;
	private Object lock = new Object();

	public RedisPool(String host, int port) {

		this.host = host;
		this.port = port;
		init();
	}

	private void init() {

		jedisPool = new JedisPool(host, port);

	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public Jedis getClient() {

		try {
			if (jedisPool != null) {
				synchronized (lock) {
					Jedis resource = jedisPool.getResource();
					return resource;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void close(final Jedis jedis) {

		if (jedis != null) {
			synchronized (lock) {
				jedis.close();
			}
		}
	}

}