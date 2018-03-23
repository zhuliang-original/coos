package top.coos.tool.http.aid;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.fileupload.FileItem;

/**
 * http请求辅助工具类
 * 
 * @author 朱亮
 * 
 */
public abstract class HttpRequestParameter {

    // 连接超时
    protected int connectTimeOut = 5000;

    // 读取数据超时
    protected int readTimeOut = 10000;

    // 请求编码
    protected String requestEncoding = "UTF-8";

    // 响应编码
    protected String responseEncoding = "UTF-8";

    // 请求地址
    protected String url;

    // 请求参数
    protected Map<String, String> params;

    protected Map<String, String> urlParams;

    protected String parameter;

    protected String postData;

    protected byte[] postBytes;

    protected URL realURL;

    protected String separator = System.getProperty("line.separator");

    protected HttpURLConnection connection;

    protected SSLSocketFactory sslSocketFactory;

    protected String accept;

    protected String contentType;

    protected String filename = "file";

    protected List<File> files;

    protected List<FileItem> fileItems;

    protected String multipartFromData = "multipart/form-data";

    // 设置边界
    protected String boundary = "----------" + System.currentTimeMillis();

    public String getMultipartFromData() {
        return multipartFromData;
    }

    public void setMultipartFromData(String multipartFromData) {
        this.multipartFromData = multipartFromData;
    }

    public List<FileItem> getFileItems() {
        return fileItems;
    }

    public void setFileItems(List<FileItem> fileItems) {
        this.fileItems = fileItems;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void addFile(File file) {
        if (files == null) {
            files = new ArrayList<File>();
        }
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getPostData() {
        return postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public byte[] getPostBytes() {
        return postBytes;
    }

    public void setPostBytes(byte[] postBytes) {
        this.postBytes = postBytes;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }
}
