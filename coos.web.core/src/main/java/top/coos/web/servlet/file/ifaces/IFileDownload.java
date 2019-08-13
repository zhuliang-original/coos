package top.coos.web.servlet.file.ifaces;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public interface IFileDownload {

	public byte[] download(HttpServletRequest request, String path) throws IOException;
}
