package top.coos.tool.http.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.ssl.SSLContexts;

import top.coos.exception.CoreException;

public class SSLSocketFactoryFactory {

    public static SSLSocketFactory create(SSLContextType sslContextType, KeyStoreBean keyStoreBean) throws CoreException {
        try {

            if (sslContextType.equals(SSLContextType.SSL)) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new SSLX509TrustManager() };
                SSLContext sslContext = null;
                if (keyStoreBean.isUseKey()) {
                    if (!keyStoreBean.getKeyFile().isFile()) {
                        throw new CoreException("keyFile is not file");
                    }
                    if (keyStoreBean.getKeyPassword() == null || keyStoreBean.getKeyPassword().length == 0) {
                        throw new CoreException("keyPassword is null");
                    }
                    if (keyStoreBean.getKeyStoreType().equals(KeyStoreType.PKCS12)) {

                        // 指定读取证书格式为PKCS12
                        KeyStore keyStore = KeyStore.getInstance(keyStoreBean.getKeyStoreType().getType());
                        // 读取本机存放的PKCS12证书文件
                        FileInputStream instream = new FileInputStream(keyStoreBean.getKeyFile());
                        try {
                            // 指定PKCS12的密码(商户ID)
                            keyStore.load(instream, keyStoreBean.getKeyPassword());

                        } finally {
                            instream.close();
                        }
                        sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keyStoreBean.getKeyPassword()).build();

                    } else {
                        throw new CoreException("not support " + keyStoreBean.getKeyStoreType());
                    }
                } else {
                    sslContext = SSLContext.getInstance(sslContextType.getValue());
                }
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                return sslContext.getSocketFactory();
            } else if (sslContextType.equals(SSLContextType.TLS)) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new SSLX509TrustManager() };
                SSLContext sslContext = null;
                sslContext = SSLContext.getInstance(sslContextType.getValue());
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                return sslContext.getSocketFactory();
            } else {
                throw new CoreException("not support " + sslContextType);
            }
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }
}
