package top.coos.web.servlet.file.bean;

import java.io.Serializable;

public class FileBean implements Serializable {

	private static final long serialVersionUID = -160472326045116752L;

	private String path;

	private String name;

	private String url;

	private String type;

	private String absolutePath;

	private long length;

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getAbsolutePath() {

		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {

		this.absolutePath = absolutePath;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public long getLength() {

		return length;
	}

	public void setLength(long length) {

		this.length = length;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
