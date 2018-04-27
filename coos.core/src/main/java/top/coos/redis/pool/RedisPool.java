package top.coos.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import top.coos.tool.string.StringHelper;

/**
 * redis 连接池工具类
 * 
 * @author CLQ
 *
 */
public final class RedisPool {

	private JedisPool pool = null;
	private final String host;
	private final String auth;
	private final int port;
	private final int timeout = 10000;
	private final Object lock = new Object();

	public RedisPool(String host, int port) {

		this(host, port, null);
	}

	public RedisPool(String host, int port, String auth) {

		this.host = host;
		this.port = port;
		this.auth = auth;
		init();
	}

	private void init() {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(500);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 10);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		// new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		if (StringHelper.isEmpty(auth)) {
			pool = new JedisPool(config, host, port, timeout);
		} else {
			pool = new JedisPool(config, host, port, timeout, auth);
		}

	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public Jedis getClient() {

		try {
			if (pool != null) {
				synchronized (lock) {
					Jedis resource = pool.getResource();
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