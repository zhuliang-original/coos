package top.coos.tool.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓冲池
 * 
 */
public interface BufferPool {
    public ByteBuffer allocate(int size);

    public void recycle(ByteBuffer theBuf);

    public long capacity();

    public long size();

    public int getConReadBuferChunk();

    public int getSharedOptsCount();

    public int getChunkSize();

    public ConcurrentHashMap<Long, Long> getNetDirectMemoryUsage();

}
