package top.coos.bean;

import top.coos.Support;

public class FileEntity extends Support {

	private static final long serialVersionUID = 1L;

	private String name;

	private String path;

	private String url;

	private String type;

	private long length;

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public long getLength() {

		return length;
	}

	public void setLength(long length) {

		this.length = length;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

}
