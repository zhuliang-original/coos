package top.coos.protect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import top.coos.constant.Constant;
import top.coos.protect.resource.ResourcePackageInfo;
import top.coos.tool.file.FileTool;
import top.coos.tool.key.EncryptDecrypted;
import top.coos.tool.serialize.SerializeTool;

public class M {

	public static String MP = ResourcePackageInfo.PATH + "/M";

	private static Map<String, Object> MAP = new HashMap<String, Object>();

	private static EncryptDecrypted ENCRYPTDECRYPTED = new EncryptDecrypted();

	public static byte[] K = readKey();

	static {
		load();
	}

	private static byte[] readKey() {

		byte[] bytes = null;
		try {
			bytes = MP.getBytes();
			return bytes;
		} catch (Exception e) {
		}
		return bytes;
	}

	@SuppressWarnings("unchecked")
	private synchronized static void load() {

		InputStream stream = Constant.Class.CLASS_LOADER.getResourceAsStream(MP);
		try {

			byte[] bytes = FileTool.readBytes(stream);
			byte[] bytes_ = ENCRYPTDECRYPTED.Decrypted(bytes, K);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes_);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object bean = ois.readObject();
			ois.close();
			bis.close();

			if (bean != null && bean instanceof Map) {
				final Map<String, Object> data_map = ((Map<String, Object>) bean);
				for (String key : data_map.keySet()) {
					MAP.put(key, data_map.get(key));
				}
			}
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	public synchronized static <T> T get(String key) {

		try {
			return (T) MAP.get(key);
		} catch (Exception e) {
		}
		return null;
	}

	public synchronized static byte[] get(Map<String, Object> map) throws Exception {

		byte[] bytes = SerializeTool.getByteFromObj(map);
		byte[] bs = ENCRYPTDECRYPTED.Encrypt(bytes, K);
		return bs;
	}

}
