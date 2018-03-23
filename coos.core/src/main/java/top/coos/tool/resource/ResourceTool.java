package top.coos.tool.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import top.coos.constant.Constant;
import top.coos.tool.file.FileTool;
import top.coos.tool.string.StringHelper;

public class ResourceTool {

	private static JarFile jarFile;

	public static InputStream load(String name) {

		return Constant.Class.CLASS_LOADER.getResourceAsStream(name);
	}

	public static Set<String> getPath(String packagepath) {

		Set<String> sets = new HashSet<String>();
		try {
			Enumeration<URL> urls =
					Constant.Class.CLASS_LOADER.getResources(packagepath);
			if (urls != null) {
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					String path = url.getPath();

					path = path.replace("!/" + packagepath, "").replace("file:", "");
					sets.add(path);
				}
			}

		} catch (Exception e) {
		}
		return sets;

	}

	public static Map<String, InputStream> loadByPackage(String packagepath) throws IOException {

		Map<String, InputStream> path_stream_map = new HashMap<String, InputStream>();
		Set<String> sets = getPath(packagepath);
		for (String path : sets) {

			if (!StringHelper.isEmpty(path)) {
				if (new File(path).getName().indexOf(".jar") > 0) {
					String jarPath = path;
					jarFile = new JarFile(jarPath);
					Enumeration<JarEntry> es = jarFile.entries();
					while (es.hasMoreElements()) {
						JarEntry e = es.nextElement();
						String name = e.getName();
						if (e.isDirectory()) {
						} else {
							if (name.indexOf(packagepath) == 0) {
								InputStream stream = Constant.Class.CLASS_LOADER.getResourceAsStream(name);
								path_stream_map.put(name, stream);
							}
						}
					}

				} else {
					File[] files = FileTool.getAllFilesByFolder(path);
					if (files != null) {
						for (File file : files) {
							InputStream stream = new FileInputStream(file);
							path_stream_map.put(file.getPath(), stream);
						}
					}
				}
			}
		}
		return path_stream_map;
	}
}
