package top.coos.tool.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

public class SerializeTool {

    public static Object getObjFromBytes(byte[] bytes) throws UnsupportedEncodingException, IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object result = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        return result;
    }

    /**
     * @param serStr
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClassNotFoundException
     * @描述 —— 将字符串反序列化成对象
     */
    public static Object getObjFromStr(String serStr) throws UnsupportedEncodingException, IOException, ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object result = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        return result;
    }

    /**
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @描述 —— 将对象序列化成字符串
     */
    public static String getStrFromObj(Object obj) throws IOException, UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");

        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @描述 —— 将对象序列化成字符串
     */
    public static byte[] getByteFromObj(Object obj) throws IOException, UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bytes;
    }
}
