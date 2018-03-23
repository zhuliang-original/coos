package top.coos.classloader;

import top.coos.classloader.cache.ClassByteCache;
import top.coos.classloader.cache.ClassCache;
import top.coos.protect.K;

public class CoreClassLoader extends java.lang.ClassLoader {

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
        return Class.forName(name);
        // return super.loadClass(name);
    }

}
