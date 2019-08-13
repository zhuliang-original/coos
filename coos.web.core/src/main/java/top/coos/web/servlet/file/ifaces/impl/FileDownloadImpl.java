package top.coos.web.servlet.file.ifaces.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import top.coos.ConfigurationFactory;
import top.coos.web.servlet.file.ifaces.IFileDownload;

public class FileDownloadImpl implements IFileDownload {

	@Override
	public byte[] download(HttpServletRequest request, String path) throws IOException {

		if (!StringUtils.isEmpty(path)) {
			String local_folder = ConfigurationFactory.get().server.getLocal_folder();
			File file = new File(local_folder + path);
			if (file != null && file.isFile()) {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				InputStream in = null;
				try {
					in = new FileInputStream(file);
					int buf_size = 1024;
					byte[] buffer = new byte[buf_size];
					int len = 0;
					while (-1 != (len = in.read(buffer, 0, buf_size))) {
						bos.write(buffer, 0, len);
					}
					return (bos.toByteArray());
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		return null;
	}

}
