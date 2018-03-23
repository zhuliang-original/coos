package top.coos.protect;

import java.io.InputStream;

import top.coos.constant.Constant;
import top.coos.protect.resource.ResourcePackageInfo;
import top.coos.tool.clazz.ClassTool;
import top.coos.tool.file.FileTool;
import top.coos.tool.key.EncryptDecrypted;

public class K {

    public static String KP = ResourcePackageInfo.PATH + "/K";

    public static String KN = "KS_NAME";

    private static EncryptDecrypted ENCRYPTDECRYPTED = new EncryptDecrypted();

    public static byte[] K = readKey();

    public static KS KS = get();

    private static byte[] readKey() {
        byte[] bytes = null;
        try {
            bytes = KP.getBytes();
            return bytes;
        } catch (Exception e) {
        }
        return bytes;
    }

    private synchronized static KS get() {
        KS ks = null;
        try {
            InputStream stream = Constant.Class.CLASS_LOADER.getResourceAsStream(KP);
            byte[] bytes = FileTool.readBytes(stream);

            ks = get(bytes);
        } catch (Exception e) {
        } finally {
        }
        return ks;
    }

    private synchronized static KS get(byte[] bytes) {
        try {
            final byte[] bs = ENCRYPTDECRYPTED.Decrypted(bytes, K);
            ClassLoader c = new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    if (name.equals((String) M.get(KN))) {
                        return defineClass(name, bs, 0, bs.length);
                    }
                    return Class.forName(name);
                }
            };
            Class<?> clazz = c.loadClass((String) M.get(KN));
            return (KS) clazz.newInstance();
        } catch (Exception e) {
        } finally {
        }
        return null;
    }

    public synchronized static byte[] get(Class<?> clazz) throws Exception {
        byte[] bytes = ClassTool.getClassByte(clazz);
        byte[] bs = ENCRYPTDECRYPTED.Encrypt(bytes, K);
        return bs;
    }

}
