package top.coos.web.servlet.file.bean;

import java.io.Serializable;

public class FileByteBean implements Serializable {

	private static final long serialVersionUID = -160472326045116752L;

	private String name;

	private byte[] bytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
