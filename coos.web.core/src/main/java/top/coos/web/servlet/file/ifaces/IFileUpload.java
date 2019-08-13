package top.coos.web.servlet.file.ifaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import top.coos.web.servlet.file.bean.FileBean;
import top.coos.web.servlet.file.bean.FileByteBean;

public interface IFileUpload {

	public List<FileBean> upload(HttpServletRequest request, List<FileItem> items) throws Exception;

	public List<FileBean> upload(String this_server_root, List<FileByteBean> files) throws Exception;
}
