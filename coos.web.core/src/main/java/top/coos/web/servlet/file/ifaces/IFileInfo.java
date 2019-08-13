package top.coos.web.servlet.file.ifaces;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import top.coos.web.servlet.file.bean.FileBean;

public interface IFileInfo {

	public FileBean get(HttpServletRequest request, String path) throws IOException;

}
