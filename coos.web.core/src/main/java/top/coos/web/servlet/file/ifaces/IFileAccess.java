package top.coos.web.servlet.file.ifaces;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFileAccess {

	public void access(HttpServletRequest request, HttpServletResponse response, String path) throws IOException;

}
