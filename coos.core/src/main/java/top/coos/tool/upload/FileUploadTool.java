package top.coos.tool.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.coos.bean.FileEntity;
import top.coos.exception.CoreException;
import top.coos.tool.entity.EntityTool;
import top.coos.tool.http.aid.HttpRequestAid;
import top.coos.tool.string.StringHelper;

public class FileUploadTool {

	public static String upload(String url, File file) throws IOException {

		List<File> files = new ArrayList<File>();
		files.add(file);
		return upload(url, files);
	}

	public static String upload(String url, List<File> files) throws IOException {

		HttpRequestAid aid = new HttpRequestAid();
		aid.setUrl(url);
		aid.setFiles(files);

		StringBuffer buffer = aid.doPost();
		return buffer.toString();
	}

	public static FileEntity up(String url, File file) throws CoreException {

		List<File> files = new ArrayList<File>();
		files.add(file);
		List<FileEntity> list = up(url, files);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<FileEntity> up(String url, List<File> files) throws CoreException {

		List<FileEntity> list = new ArrayList<FileEntity>();
		try {

			String result = upload(url, files);
			if (!StringHelper.isEmpty(result)) {
				JSONObject object = JSONObject.fromObject(result);
				if (object.get("error") != null && object.getInt("error") == 0) {
					if (object.get("value") != null && object.getJSONArray("value").size() > 0) {
						JSONArray array = object.getJSONArray("value");
						JSONObject one = array.getJSONObject(0);

						FileEntity entity = (FileEntity) EntityTool.getEntity(FileEntity.class, one, false);
						list.add(entity);
					}
				}
			}
		} catch (IOException e) {
			throw new CoreException(e);
		} catch (CoreException e) {
			throw e;
		} catch (Exception e) {
			throw new CoreException(e);
		}
		return list;
	}
}
