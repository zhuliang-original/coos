package top.coos.tool.http.aid;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.fileupload.FileItem;

import top.coos.exception.CoreRuntimeException;
import top.coos.servlet.enums.RequestMethod;
import top.coos.tool.string.StringHelper;

/**
 * http请求辅助工具类
 * 
 * @author 朱亮
 * 
 */
public abstract class HttpRequestAidMethod extends HttpRequestParameter {

    private boolean parameterStringInited = false;

    protected void initParameterString() {
        if (parameterStringInited) {
            return;
        }
        parameterStringInited = true;
        StringBuffer paramstr = new StringBuffer();
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (params.get(key) != null) {
                    paramstr.append("&").append(key).append("=").append(value);
                }
            }
        }
        if (!StringHelper.isEmpty(parameter)) {
            parameter = parameter + paramstr.toString();
        } else {
            parameter = paramstr.toString();
        }

        if (!StringHelper.isEmpty(parameter)) {
            parameter = parameter + "&";
        }

    }

    protected void initURL(RequestMethod method) {

        if (StringHelper.isEmpty(url)) {
            throw new CoreRuntimeException("url not set");
        }
        initParameterString();
        StringBuffer url = new StringBuffer(super.url);
        if (urlParams != null) {
            StringBuffer urlParameter = new StringBuffer();
            for (String key : urlParams.keySet()) {
                String value = urlParams.get(key);
                if (urlParams.get(key) != null) {
                    urlParameter.append("&").append(key).append("=").append(value);
                }
            }
            if (!StringHelper.isEmpty(urlParameter.toString())) {
                if (url.indexOf("?") > 0) {
                    url.append(urlParameter);
                } else {
                    url.append("?").append(urlParameter);
                }
            }
        }
        try {
            if (method.equals(RequestMethod.GET)) {
                if (!StringHelper.isEmpty(parameter)) {
                    if (url.indexOf("?") > 0) {
                        url.append(parameter);
                    } else {
                        url.append("?").append(parameter);
                    }
                }
            }
            realURL = new URL(url.toString());
        } catch (MalformedURLException e) {
            throw new CoreRuntimeException(e.getMessage());
        }
    }

    protected void initURLConnection(RequestMethod method) throws IOException {
        initURL(method);
        // 打开和URL之间的连接
        connection = (HttpURLConnection) realURL.openConnection();
        if (sslSocketFactory == null) {
            connection = (HttpURLConnection) realURL.openConnection();
        } else {
            connection = (HttpsURLConnection) realURL.openConnection();
            ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
        }
        connection.setRequestMethod(method.getValue());
        // 设置通用的请求属性
        if (StringHelper.isEmpty(accept)) {
            connection.setRequestProperty("accept", "*/*");
        } else {
            connection.setRequestProperty("accept", accept);
        }
        if (StringHelper.isEmpty(contentType)) {
        } else {
            connection.setRequestProperty("Content-Type", contentType); // 设置发送数据的格式
        }
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.10 Safari/537.36");

        connection.setRequestProperty("charset", requestEncoding);
        connection.setReadTimeout(readTimeOut);
        connection.setConnectTimeout(connectTimeOut);
        connection.setDoOutput(true);
        if (method.equals(RequestMethod.POST)) {
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false); // post方式不能使用缓存
            if ((files != null && files.size() > 0) || fileItems != null && fileItems.size() > 0) {

                // 设置边界
                connection.setRequestProperty("Content-Type", multipartFromData + ";boundary=" + boundary);
                StringBuilder sb = new StringBuilder();
                if (params != null && params.size() > 0) {
                    for (String name : params.keySet()) {
                        if (params.get(name) != null) {
                            sb.append("--"); // 必须多两道线
                            sb.append(boundary);
                            sb.append("\r\n");
                            sb.append("Content-Disposition: form-data;name=\"" + name + "\";id=\"" + name + "\"\r\n\r\n");
                            sb.append(params.get(name));
                            sb.append("\r\n");
                        }
                    }
                }
                byte[] bytes = sb.toString().getBytes(requestEncoding);
                connection.getOutputStream().write(bytes, 0, bytes.length);
                if (files != null && files.size() > 0) {

                    sb = new StringBuilder();
                    for (File file : files) {

                        sb.append("--"); // 必须多两道线
                        sb.append(boundary);
                        sb.append("\r\n");
                        sb.append("Content-Disposition: form-data;name=\"" + filename + "\";filename=\"" + file.getName() + "\";type=\"file\";\r\n");
                        sb.append("Content-Type:application/octet-stream\r\n\r\n");
                        byte[] head = sb.toString().getBytes(requestEncoding);
                        // 输出表头
                        connection.getOutputStream().write(head);
                        // 文件正文部分
                        // 把文件已流文件的方式 推入到url中
                        DataInputStream in = new DataInputStream(new FileInputStream(file));
                        int size = 0;
                        byte[] bufferOut = new byte[1024];
                        while ((size = in.read(bufferOut)) != -1) {
                            connection.getOutputStream().write(bufferOut, 0, size);
                        }
                        in.close();
                    }
                }

                if (fileItems != null && fileItems.size() > 0) {

                    sb = new StringBuilder();
                    for (FileItem fileItem : fileItems) {

                        sb.append("--"); // 必须多两道线
                        sb.append(boundary);
                        sb.append("\r\n");
                        sb.append("Content-Disposition: form-data;name=\"" + filename + "\";filename=\"" + fileItem.getName() + "\";type=\"file\";\r\n");
                        sb.append("Content-Type:application/octet-stream\r\n\r\n");
                        byte[] head = sb.toString().getBytes(requestEncoding);
                        // 输出表头
                        connection.getOutputStream().write(head);
                        // 文件正文部分
                        // 把文件已流文件的方式 推入到url中
                        DataInputStream in = new DataInputStream(fileItem.getInputStream());
                        int size = 0;
                        byte[] bufferOut = new byte[1024];
                        while ((size = in.read(bufferOut)) != -1) {
                            connection.getOutputStream().write(bufferOut, 0, size);
                        }
                        in.close();
                    }
                }
                // 结尾部分
                byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes(requestEncoding);// 定义最后数据分隔线
                connection.getOutputStream().write(foot);
            } else {
                if (!StringHelper.isEmpty(parameter)) {
                    byte[] bytes = parameter.getBytes(requestEncoding);
                    connection.getOutputStream().write(bytes, 0, bytes.length);
                }
                if (postBytes != null && postBytes.length > 0) {
                    connection.getOutputStream().write(postBytes, 0, postBytes.length);
                }
                if (!StringHelper.isEmpty(postData)) {
                    byte[] bytes = postData.getBytes(requestEncoding);
                    connection.getOutputStream().write(bytes, 0, bytes.length);
                }
            }
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
        }
    }

    protected StringBuffer readString() throws IOException {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), responseEncoding));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
                result.append(separator);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                // 使用finally块来关闭输入流
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    protected byte[] readBytes() throws IOException {
        BufferedInputStream in = null;
        try {

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedInputStream(connection.getInputStream());
            byte[] buf = new byte[8096];
            int size = 0;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((size = in.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, size);
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                // 使用finally块来关闭输入流
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
    }

    protected void connect() throws IOException {
        // 建立实际的连接
        connection.connect();
    }

}
