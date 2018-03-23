package top.coos.tool.compressor;

import java.io.File;
import java.io.IOException;

import top.coos.constant.Constant;
import top.coos.tool.base.BaseTool;
import top.coos.tool.file.FileTool;
import top.coos.tool.string.StringHelper;

import com.yahoo.platform.yui.compressor.YUICompressor;

public class CompressorTool {
    static String encoding = "utf-8";

    public static enum Type {
        JS, CSS
    }

    @SuppressWarnings("static-access")
    public static String compressor(String content, Type type) {
        if (StringHelper.isEmpty(content)) {
            return null;
        }
        String oldFilePath = Constant.Path.TEMP_PATH + BaseTool.getRandomNumber();
        if (type.equals(Type.JS)) {
            oldFilePath += ".js";
        } else {
            oldFilePath += ".css";
        }
        String path = YUICompressor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String yuiPath = new File(path).getAbsolutePath();
        File temp = new File(oldFilePath);
        FileTool.save(temp, content);
        StringBuilder sb = new StringBuilder();
        String minPath = null;
        if (type.equals(Type.JS)) {
            minPath = compressJS(yuiPath, sb, temp, encoding);
        }
        if (type.equals(Type.CSS)) {
            minPath = compressCSS(yuiPath, sb, temp, encoding);
        }
        String[] res = sb.toString().split("/n");
        Runtime runTime = Runtime.getRuntime();
        for (String cmd : res) {
            if (!cmd.trim().equals("")) {
                try {
//                    System.out.println(cmd);
                    runTime.exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File minFile = new File(minPath);
        FileTool.save(minFile, "");
        String minContent = null;
        int count = 0;
        while ((minContent = FileTool.read(minFile)).replaceAll("\n", "").trim().length() == 0) {
            try {
                new Thread().sleep(500);
            } catch (Exception e) {
            }
            count++;
            if (count > 10) {
                break;
            }
        }
        minContent = FileTool.read(minFile);
        FileTool.delete(temp);
        FileTool.delete(minFile);
        return minContent;
    }

    /**
     * 压缩JS文件
     */
    public static String compressJS(String yuiPath, StringBuilder sb, File f, String encoding) {
        String minPath = f.getPath().replace(".js", ".min.js");

        String fileName = f.getName();
        if (fileName.endsWith(".js") && !fileName.endsWith(".min.js")) {
            sb.append("java -jar ");
            sb.append(yuiPath);
            sb.append(" --type js --charset ");
            sb.append(encoding + " " + f.getPath());
            sb.append(" -o " + minPath);
            sb.append("/n");
        }
        return minPath;
    }

    /**
     * 压缩CSS文件
     */
    public static String compressCSS(String yuiPath, StringBuilder sb, File f, String encoding) {
        String minPath = f.getPath().replace(".css", ".min.css");
        String fileName = f.getName();
        if (fileName.endsWith(".css") && !fileName.endsWith(".min.css")) {
            sb.append("java -jar ");
            sb.append(yuiPath);
            sb.append(" --type css --charset ");
            sb.append(encoding + " " + f.getPath());
            sb.append(" -o " + f.getPath().replace(".css", ".min.css"));
            sb.append("/n");
        }
        return minPath;
    }
}
