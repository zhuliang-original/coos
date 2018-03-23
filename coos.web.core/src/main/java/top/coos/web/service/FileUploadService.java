package top.coos.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import top.coos.bean.FileEntity;

public interface FileUploadService {
    public List<FileEntity> upload(HttpServletRequest request, List<FileItem> items) throws Exception;
}
