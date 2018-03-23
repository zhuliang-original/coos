package top.coos.tool.http.aid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import top.coos.config.ContentTypeConfig;
import top.coos.servlet.enums.RequestMethod;
import top.coos.tool.base.BaseTool;

/**
 * http请求辅助工具类
 * 
 * @author 朱亮
 * 
 */
public class HttpRequestAid extends HttpRequestAidMethod {

    public StringBuffer doGet() throws IOException {
        initURLConnection(RequestMethod.GET);
        connect();
        return readString();
    }

    public StringBuffer doPost() throws IOException {
        initURLConnection(RequestMethod.POST);
        connect();
        return readString();
    }

    private byte[] doFile(RequestMethod method) throws IOException {
        initURLConnection(method);
        connect();
        return readBytes();
    }

    public byte[] doGetFile() throws IOException {
        return doFile(RequestMethod.GET);
    }

    public byte[] doPostFile() throws IOException {
        return doFile(RequestMethod.POST);
    }

    public File doGetFile(String folder) throws IOException {
        return getFile(RequestMethod.GET, folder);
    }

    public File doPostFile(String folder) throws IOException {
        return getFile(RequestMethod.POST, folder);
    }

    private File getFile(RequestMethod method, String folder) throws IOException {
        byte[] bytes = doFile(method);
        File file = null;
        String fileContentType = connection.getHeaderField("Content-Type");
        String fileName = "themp-" + BaseTool.getRandomNumber() + "." + ContentTypeConfig.getFileSuffix(fileContentType);
        String filePathName = folder + File.separator + fileName;
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        file = new File(filePathName);
        file.createNewFile();
        OutputStream fileOut = new FileOutputStream(file);

        fileOut.write(bytes, 0, bytes.length);
        fileOut.flush();
        fileOut.close();
        return file;
    }

}
