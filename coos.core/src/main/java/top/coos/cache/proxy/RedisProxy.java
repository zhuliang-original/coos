package top.coos.cache.proxy;

import java.util.Set;

import redis.clients.jedis.Jedis;
import top.coos.redis.pool.RedisPool;

public abstract class RedisProxy extends CacheProxy {

	public abstract byte[] formatKey(Object key);

	public abstract byte[] formatValue(Object value);

	public abstract Object fullValue(byte[] bytes);

	public RedisPool pool = getPool();

	@Override
	public void set(Object key, Object value) {

		if (key == null) {
			return;
		}
		byte[] keyByte = formatKey(key);
		if (keyByte == null) {
			return;
		}
		Jedis client = pool.getClient();
		if (value == null) {
			client.del(keyByte);
			pool.close(client);
		} else {
			byte[] valueByte = formatValue(value);
			if (valueByte == null) {
				client.del(keyByte);
				pool.close(client);
			} else {
				client.set(keyByte, valueByte);
				pool.close(client);
			}
		}
	}

	@Override
	public abstract void clear();

	@Override
	public void remove(Object key) {

		if (key == null) {
			return;
		}
		byte[] keyByte = formatKey(key);
		if (keyByte == null) {
			return;
		}
		Jedis client = pool.getClient();
		client.del(keyByte);
		pool.close(client);
	}

	@Override
	public Object get(Object key) {

		if (key == null) {
			return null;
		}
		byte[] keyByte = formatKey(key);
		if (keyByte == null) {
			return null;
		}
		Jedis client = pool.getClient();
		byte[] valueBytes = client.get(keyByte);
		pool.close(client);
		if (valueBytes != null) {
			return formatValue(valueBytes);
		}
		return null;
	}

	@Override
	public abstract int size();

	@Override
	public abstract Set<?> keySet();

	public abstract RedisPool getPool();

}
