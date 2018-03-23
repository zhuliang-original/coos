package top.coos.resource.handler;

import java.util.List;

import javax.annotation.Resource;

import top.coos.PackageInfo;
import top.coos.classloader.cache.ClassByteCache;
import top.coos.constant.Constant;
import top.coos.protect.C;
import top.coos.resource.bean.ResourceBean;
import top.coos.resource.cache.ResourceNameCache;
import top.coos.resource.cache.ResourceTypeCache;
import top.coos.tool.clazz.ClassTool;
import top.coos.tool.string.StringHelper;

public class ResourceLoadHandler {

	public static boolean INITED = false;

	public synchronized static boolean init() {

		if (INITED) {
			return true;
		}
		C.load();
		for (String key : ClassByteCache.CONTEXT.keySet()) {
			try {
				Class<?> clazz = Constant.Class.BASEPM_CLASS_LOADER.loadClass(key);
				load(clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		load(PackageInfo.NAME);
		INITED = true;
		return true;
	}

	public synchronized static void load(String packagename) {

		List<Class<?>> clazzs = ClassTool.getClasses(packagename);
		if (clazzs != null) {
			for (Class<?> clazz : clazzs) {
				load(clazz);
			}
		}
	}

	public synchronized static void load(Class<?> clazz) {

		if (clazz.isInterface()) {
			return;
		}
		if (clazz.isAnnotationPresent(Resource.class)) {
			Resource resource = clazz.getAnnotation(Resource.class);
			Class<?>[] types = null;
			if (resource.type() != null && !resource.type().equals(Object.class)) {
				types = new Class<?>[] { resource.type() };
			} else {
				types = clazz.getInterfaces();
			}
			if (types == null || types.length == 0) {
				return;
			}
			for (Class<?> type : types) {
				ResourceBean resourceBean = getResource(clazz, type);
				if (resourceBean != null) {
					String name = resourceBean.getName();
					String typeName = type.getName();
					ResourceNameCache.CONTEXT.put(name, resourceBean);
					ResourceTypeCache.CONTEXT.put(typeName, resourceBean);
				}
			}
		}
	}

	private static ResourceBean getResource(Class<?> clazz, Class<?> type) {

		if (clazz.isInterface()) {
			return null;
		}
		if (clazz.isAnnotationPresent(Resource.class)) {
			Resource resource = clazz.getAnnotation(Resource.class);
			String name = resource.name();
			String typeName = type.getName();
			ResourceBean resourceBean = new ResourceBean();
			if (!StringHelper.isEmpty(name)) {
				resourceBean.setName(name);
			} else {
				resourceBean.setName(typeName);
			}
			resourceBean.setInstanceClass(clazz);
			resourceBean.setType(type);
			return resourceBean;

		}
		return null;
	}
}
