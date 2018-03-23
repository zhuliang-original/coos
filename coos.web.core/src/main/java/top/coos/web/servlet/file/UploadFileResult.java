package top.coos.web.servlet.file;

import java.util.List;

import top.coos.bean.FileEntity;

public class UploadFileResult {
    private int code;

    private int error;

    private String message;

    private String url;

    private String path;

    private List<FileEntity> value;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FileEntity> getValue() {
        return value;
    }

    public void setValue(List<FileEntity> value) {
        this.value = value;
    }

}
