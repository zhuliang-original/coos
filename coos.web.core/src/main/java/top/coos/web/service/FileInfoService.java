package top.coos.web.service;

import javax.servlet.http.HttpServletRequest;

import top.coos.bean.FileEntity;

public interface FileInfoService {

	public FileEntity readInfo(HttpServletRequest request, String path, String url);
}
