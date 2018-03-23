package top.coos.tool.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferTool {
    // 缓冲区大小
    protected static final int BUFFER_SIZE = 1024 * 1024;

    public static ByteBuffer expand(ByteBuffer buffer, int expandSize) {
        buffer.flip();
        byte[] oldSrc = buffer.array();
        int oldLength = buffer.limit();
        int newLength = expandSize;
        int allLength = oldLength + newLength;
        ByteBuffer newBuffer = ByteBuffer.allocate(allLength);
        newBuffer.put(oldSrc, 0, oldLength);
        return newBuffer;
    }

    public static ByteBuffer expand(int expandSize, ByteBuffer buffer) {
        buffer.flip();
        byte[] oldSrc = buffer.array();
        int oldLength = buffer.limit();
        int newLength = expandSize;
        int allLength = oldLength + newLength;
        ByteBuffer newBuffer = ByteBuffer.allocate(allLength);
        newBuffer.put(oldSrc, 0, oldLength);
        return newBuffer;
    }

    public static ByteBuffer writeToBuffer(byte src, ByteBuffer buffer) {
        buffer.flip();
        byte[] oldSrc = buffer.array();
        int oldLength = buffer.limit();
        int newLength = 1;
        int allLength = oldLength + newLength;
        ByteBuffer newBuffer = ByteBuffer.allocate(allLength);
        newBuffer.put(oldSrc, 0, oldLength);
        newBuffer.put(src);
        return newBuffer;
    }

    public static ByteBuffer writeToBuffer(byte[] src, ByteBuffer buffer) {
        buffer.flip();
        byte[] oldSrc = buffer.array();
        int oldLength = buffer.limit();
        int newLength = src.length;
        int allLength = oldLength + newLength;
        ByteBuffer newBuffer = ByteBuffer.allocate(allLength);
        newBuffer.put(oldSrc, 0, oldLength);
        newBuffer.put(src, 0, newLength);
        return newBuffer;
    }

    public static ByteBuffer read(SocketChannel channel) throws IOException {
        int byteReadSize = 0;
        List<byte[]> bytesList = new ArrayList<byte[]>();
        while (true) {
            ByteBuffer input = allocate();
            int read = channel.read(input);
            if (read == -1) {
                return null;
            }
            if (read > 0) {
                byteReadSize += read;
                byte[] bytes = new byte[read];
                input.flip();
                input.get(bytes, 0, read);
                bytesList.add(bytes);
            } else {
                break;
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(byteReadSize);
        for (byte[] bytes : bytesList) {
            buffer.put(bytes);
        }
        return buffer;
    }

    public static ByteBuffer allocate() {
        return ByteBuffer.allocate(BUFFER_SIZE);
    }
}
