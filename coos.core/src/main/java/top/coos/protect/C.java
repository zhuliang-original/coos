package top.coos.protect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import top.coos.classloader.cache.ClassByteCache;
import top.coos.constant.Constant;
import top.coos.protect.resource.code.CodePackageInfo;
import top.coos.tool.file.FileTool;
import top.coos.tool.resource.ResourceTool;

@SuppressWarnings("unchecked")
public class C {

	public static String CODE_PACKAGE = CodePackageInfo.PATH;

	public static String CN = "CS_NAME";

	private static boolean LOADED = false;

	static {
		load();
	}

	public static CS CS = get();

	public synchronized static void load() {

		if (LOADED) {
			return;
		}
		loadByPack(CODE_PACKAGE);
		LOADED = true;
	}

	private synchronized static void loadByPack(String pack) {

		try {
			Map<String, InputStream> streamMap = ResourceTool.loadByPackage(pack);
			if (streamMap != null) {
				for (String path : streamMap.keySet()) {
					InputStream stream = streamMap.get(path);
					loadByStream(stream);
				}
			}
		} catch (Exception e) {
		}
	}

	private synchronized static void loadByStream(InputStream stream) {

		try {
			byte[] bytes = FileTool.readBytes(stream);
			byte[] bytes_ = K.KS.get(bytes);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes_);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object bean = ois.readObject();
			ois.close();
			bis.close();

			if (bean != null && bean instanceof Map) {
				final Map<String, byte[]> bytes_map = ((Map<String, byte[]>) bean);
				for (String key : bytes_map.keySet()) {
					ClassByteCache.CONTEXT.put(key, bytes_map.get(key));
				}
			}
		} catch (Exception e) {
		}
	}

	private synchronized static CS get() {

		try {
			return (CS) Constant.Class.BASEPM_CLASS_LOADER.loadClass((String) M.get(CN)).newInstance();
		} catch (Exception e) {
		}
		return null;
	}

}
