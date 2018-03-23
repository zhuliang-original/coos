package top.coos.tool.clazz;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import top.coos.tool.file.FileTool;

public class ClassTool {

	public static Object[] newArray(Class<?> componentType, int length) {

		Object[] datas = null;
		if (componentType.equals(String.class)) {
			datas = new String[length];
		} else if (componentType.equals(Integer.class)) {
			datas = new Integer[length];
		} else if (componentType.equals(Double.class)) {
			datas = new Double[length];
		} else if (componentType.equals(Long.class)) {
			datas = new Long[length];
		} else if (componentType.equals(Float.class)) {
			datas = new Float[length];
		} else if (componentType.equals(Boolean.class)) {
			datas = new Boolean[length];
		} else if (componentType.equals(Float.class)) {
			datas = new Float[length];
		} else {
			datas = new Object[length];
		}
		return datas;
	}

	public static boolean isPrimitive(Class<?> objectClass) {

		if (objectClass.equals(String.class)) {
			return true;
		} else if (objectClass.equals(Integer.class)) {
			return true;
		} else if (objectClass.equals(Double.class)) {
			return true;
		} else if (objectClass.equals(Float.class)) {
			return true;
		} else if (objectClass.equals(Long.class)) {
			return true;
		} else if (objectClass.equals(Short.class)) {
			return true;
		} else if (objectClass.equals(Boolean.class)) {
			return true;
		} else if (objectClass.equals(Byte.class)) {
			return true;
		} else {
			return objectClass.isPrimitive();
		}
	}

	public static boolean isPrimitive(Object data) {

		if (data instanceof String) {
			return true;
		} else if (data instanceof Integer) {
			return true;
		} else if (data instanceof Double) {
			return true;
		} else if (data instanceof Float) {
			return true;
		} else if (data instanceof Long) {
			return true;
		} else if (data instanceof Short) {
			return true;
		} else if (data instanceof Boolean) {
			return true;
		} else if (data instanceof Byte) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[] getClassByte(String className) {

		byte[] bytes = null;
		try {
			String name = className.replaceAll("\\.", "/") + ".class";
			InputStream stream = ClassTool.class.getClassLoader().getResourceAsStream(name);
			bytes = FileTool.readBytes(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static byte[] getClassByte(Class<?> className) {

		return getClassByte(className.getName());
	}

	public static <T> T getObjectByClass(Class<T> clazz) {

		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return t;
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClasses(String pack) {

		return getClasses(pack, Thread.currentThread().getContextClassLoader());
	}

	public static List<String> getNames(JarFile jarFile, String pack) {

		if (pack == null) {
			pack = "";
		}
		String path = pack.replace('.', '/');

		List<String> names = new ArrayList<String>();
		try {
			// 从此jar包 得到一个枚举类
			Enumeration<JarEntry> entries = jarFile.entries();
			// 同样的进行循环迭代
			while (entries.hasMoreElements()) {
				// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				// 如果是以/开头的
				if (name.charAt(0) == '/') {
					// 获取后面的字符串
					name = name.substring(1);
				}
				// 如果前半部分和定义的包名相同
				if (name.startsWith(path)) {
					if (!entry.isDirectory()) {
						names.add(name);
					}
				}
			}
		} catch (Exception e) {
		} catch (Throwable e) {

		}
		return names;
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClasses(String pack, ClassLoader classLoader) {

		// 第一个class类的集合
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = classLoader.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					try {
						findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes, classLoader);

					} catch (Exception e) {
						// TODO: handle exception
					} catch (Throwable e) {

					}
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (Exception e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											// e.printStackTrace();
										} catch (ClassFormatError e) {

										}
									}
								}
							}
						}
					} catch (Exception e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						// e.printStackTrace();
					} catch (Throwable e) {

					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} catch (Throwable e) {

		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
			List<Class<?>> classes, ClassLoader classLoader) {

		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {

			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {

				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				try {

					findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
							classes, classLoader);
				} catch (Exception e) {
					// TODO: handle exception
				} catch (Throwable e) {

				}
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(classLoader.loadClass(packageName + '.' + className));
				} catch (Exception e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					// e.printStackTrace();
				} catch (Throwable e) {

				}
			}
		}
	}
}
