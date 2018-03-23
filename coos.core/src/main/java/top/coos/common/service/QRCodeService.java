package top.coos.common.service;

import java.io.File;

import top.coos.tool.file.FileTool;
import top.coos.tool.zxing.ZxingEncoderHandler;

public class QRCodeService {

    public void create(String path, String contents, int width, int height) {
        FileTool.save(new File(path), "");
        ZxingEncoderHandler handler = new ZxingEncoderHandler();
        handler.encode(contents, width, height, path);
    }

}
