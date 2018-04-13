package top.coos.classloader;

import java.net.URL;
import java.net.URLClassLoader;

import top.coos.classloader.cache.ClassByteCache;
import top.coos.classloader.cache.ClassCache;
import top.coos.protect.K;

public class CoreURLClassLoader extends URLClassLoader {

	public CoreURLClassLoader() {

		this(new URL[] {});
	}

	public CoreURLClassLoader(URLClassLoader parent) {

		super(new URL[] {}, parent);
	}

	public CoreURLClassLoader(URL[] urls) {

		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		if (ClassCache.CONTEXT.get(name) != null) {
			return ClassCache.CONTEXT.get(name);
		}
		if (ClassByteCache.CONTEXT.get(name) != null) {
			byte[] cbs = ClassByteCache.CONTEXT.get(name);
			try {
				byte[] cbs_ = K.KS.get(cbs);
				Class<?> clazz = defineClass(name, cbs_, 0, cbs_.length);
				ClassCache.CONTEXT.put(name, clazz);
				return clazz;
			} catch (Exception e) {
			}
		}
		return super.loadClass(name);
	}

	public Class<?> loadClass(String name, byte[] bytes) throws ClassNotFoundException {

		try {
			if (bytes.length > 0) {
				Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
				return clazz;
			}
		} catch (Exception e) {
		}
		return loadClass(name);
	}
}
