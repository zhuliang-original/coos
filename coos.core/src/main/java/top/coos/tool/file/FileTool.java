package top.coos.tool.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.FileUtils;

import top.coos.constant.Constant;
import top.coos.tool.base.BaseTool;
import top.coos.tool.string.StringHelper;

public class FileTool {

	public static String getName(String path) {

		if (path != null) {
			path = path.replaceAll("\\\\", "/");
		}
		String name = path.trim();
		if (name.indexOf("/") >= 0) {
			name = name.substring(name.lastIndexOf("/") + 1);
		}
		if (name.indexOf(".") > 0) {
			name = name.substring(0, name.lastIndexOf("."));
		}

		return name;
	}

	public static File[] getFilesByFolder(String folder) {

		File f = new File(folder);
		if (f != null && f.isDirectory()) {
			return f.listFiles();
		}
		return new File[0];
	}

	public static File[] getAllFilesByFolder(String folder) {

		File f = new File(folder);
		if (f != null && f.isDirectory()) {
			return f.listFiles();
		}
		return new File[0];
	}

	/**
	 * 判断文件是否为图片<br>
	 * <br>
	 * 
	 * @param pInput
	 *            文件名<br>
	 * @param pImgeFlag
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isPicture(String pInput, String pImgeFlag) throws Exception {

		// 文件名称为空的场合
		if (pInput == null) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1, pInput.length());
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" }, { "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" }, { "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (pImgeFlag != null && imgeArray[i][0].equals(tmpName.toLowerCase()) && imgeArray[i][1].equals(pImgeFlag)) {
				return true;
			}
			// 判断符合全部类型的场合
			if (pImgeFlag == null && imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取音频总时长
	 * 
	 * @param filepath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static long getAudioPlayTime(String filepath) {

		File file = new File(filepath);
		long total = 0;
		try {
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
			Map props = aff.properties();
			if (props.containsKey("duration")) {
				total = (Long) props.get("duration");
			}
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String read(String filename) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\t\n");
		}
		in.close();
		return sb.toString();
	}

	public static void copyfile(String inputname, String outputname) throws IOException {

		InputStream in = new FileInputStream(inputname);
		OutputStream out = new FileOutputStream(outputname);
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
		out.close();
	}

	public static void copyDir(String inputname, String outputname, String[] filterNames) throws IOException {

		(new File(outputname)).mkdirs();
		String name = new File(inputname).getName();
		if (!StringHelper.isEmpty(name)) {
			if (filterNames != null) {
				for (String filterName : filterNames) {
					if (!StringHelper.isEmpty(filterName) && filterName.equals(name)) {
						return;
					}
				}
			}
		}
		File[] file = (new File(inputname)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				name = file[i].getName();
				if (!StringHelper.isEmpty(name)) {
					if (filterNames != null) {
						for (String filterName : filterNames) {
							if (!StringHelper.isEmpty(filterName) && filterName.equals(name)) {
								continue;
							}
						}
					}
				}
				file[i].toString();
				FileInputStream input = new FileInputStream(file[i]);
				// mkdir if destination does not exist
				File outtest = new File(outputname);
				if (!outtest.exists()) {
					outtest.mkdir();
				}
				FileOutputStream output = new FileOutputStream(outputname + "/" + (file[i].getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			} else if (file[i].isDirectory()) {
				copyDir(file[i].toString(), outputname + "//" + file[i].getName(), filterNames);
			}
		}
	}

	public static void loadFolderFiles(String folder, List<File> files) throws IOException {

		if (files == null) {
			files = new ArrayList<File>();
		}
		if (!new File(folder).exists()) {
			return;
		}
		File[] file = (new File(folder)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				files.add(file[i]);
			} else if (file[i].isDirectory()) {
				loadFolderFiles(file[i].getPath(), files);
			}
		}
	}

	public static File getFile(String path) {

		if (path != null) {
			if (path.indexOf("http") < 0) {
				return new File(path);
			} else {
				try {
					File f = downloadFromUrl(path, Constant.Path.TEMP_PATH);
					f.deleteOnExit();
					return f;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static File downloadFromUrl(String url, String dir) throws Exception {

		String fileName = getFileNameFromUrl(url);
		File f = new File(dir + fileName);
		URL httpurl = new URL(url);
		FileUtils.copyURLToFile(httpurl, f);
		return f;
	}

	public static byte[] download(String url) throws Exception {

		URLConnection connection = new URL(url).openConnection();
		connection.connect();
		InputStream stream = connection.getInputStream();
		return readBytes(stream);

	}

	public static String getFileNameFromUrl(String url) {

		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		String beforename = BaseTool.getRandomNumber() + "_";
		if (url.indexOf("?") > 0) {
			int index = url.lastIndexOf("?");
			if (index > 0) {
				beforename = BaseTool.getRandomNumber();
			}
		}

		int index = url.lastIndexOf("/");
		if (index > 0) {
			name = url.substring(index + 1);
			name = beforename + name;
			if (name.trim().length() > 0) {
				name = name.replaceAll("\\?", "_");
				return name;
			}
		}
		return name;
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static String read(File file) {

		StringBuffer buffer = new StringBuffer();
		try {
			// 创建字节输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			// 创建一个长度为1024的竹筒
			byte[] bs = readBytes(fileInputStream);
			buffer.append(new String(bs, "UTF-8"));
			fileInputStream.close();
		} catch (Exception e) {

		}
		return buffer.toString();
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static String read(InputStream stream) {

		StringBuffer buffer = new StringBuffer();
		try {
			// 创建一个长度为1024的竹筒
			byte[] bs = readBytes(stream);
			buffer.append(new String(bs, "UTF-8"));
			stream.close();
		} catch (Exception e) {

		}
		return buffer.toString();
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static void delete(File file) {

		if (file != null) {
			if (file.isFile()) {
				file.delete();
			} else {
				File[] fs = file.listFiles();
				if (fs != null && fs.length > 0) {
					for (File f : fs) {
						delete(f);
					}
				}
				file.delete();
			}
		}
	}

	public static byte[] readBytes(InputStream stream) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(stream);
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
			bos.close();
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param saveFile
	 * @param buffer
	 */
	public static boolean save(File saveFile, byte[] bytes) {

		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			o = new FileOutputStream(saveFile);
			o.write(bytes);
			o.close();
			flag = true;
		} catch (Exception e) {
			System.out.println("error:" + saveFile);
			e.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (Exception e2) {
				}
			}
		}
		return flag;
	}

	/**
	 * 保存文件
	 * 
	 * @param saveFile
	 * @param buffer
	 */
	public static boolean save(File saveFile, String content) {

		boolean flag = false;
		FileOutputStream o = null;
		try {
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			o = new FileOutputStream(saveFile);
			if (StringHelper.isEmpty(content)) {
				content = "";
			}
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return flag;
	}

	/**
	 * 保存文件
	 * 
	 * @param saveFile
	 * @param buffer
	 */
	public static boolean saveObject(File saveFile, Object object) {

		boolean flag = false;
		FileOutputStream o = null;
		try {
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			o = new FileOutputStream(saveFile);
			ObjectOutputStream oos = new ObjectOutputStream(o);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return flag;
	}

	/**
	 * 保存文件
	 * 
	 * @param saveFile
	 * @param buffer
	 */
	public static Object readObject(File file) {

		FileInputStream o = null;
		try {
			if (!file.exists()) {
				return null;
			}
			o = new FileInputStream(file);
			ObjectInputStream oos = new ObjectInputStream(o);
			Object object = oos.readObject();
			oos.close();
			o.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	public static String getMd5(File file) {

		if (!file.exists()) {
			return null;
		}
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}
