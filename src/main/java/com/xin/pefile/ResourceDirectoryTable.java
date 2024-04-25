package com.xin.pefile;

import com.sun.istack.internal.Nullable;
import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



/**
 * 表示一个资源目录，封装了有关目录结构和内容的详细信息。
 * 这包括关于目录的元数据，如版本信息和时间戳，
 * 以及关于目录中特定条目（包括命名和未命名条目）的详细信息。
 * @author tongxin
 * @date 2024/4/20 22:12
 */
@Getter
public class ResourceDirectoryTable {

    /**
     * 常量，对象长度
     */
    private final static int LENGTH = 16;

    /**
     * 特性标志，
     */
    private final long characteristics;

    /**
     * 时间日期戳，记录资源目录创建或最后修改的时间
     */
    private final long timeDateStamp;

    /**
     * 主版本号，标识资源目录所遵循的主要版本规范
     */
    private final int majorVersion;

    /**
     * 次版本号，标识资源目录所遵循的次要版本规范
     */
    private final int minorVersion;

    /**
     * 命名条目的数量，表示目录中具有名称的资源条目总数
     */
    private final int numberOfNamedEntries;

    /**
     * 无名条目的数量，表示目录中通过ID标识而非名称标识的资源条目总数
     */
    private final int numberOfIdEntries;

    /**
     * 存储资源目录条目的映射表，键为条目的唯一标识（长整型），值为ResourceDirectoryEntry对象
     */
    private final Map<Long, ResourceDirectoryEntry> entries;

    ResourceDirectoryTable(IPEFileReader dataReader, long offset) {
        this(dataReader, offset, null);
    }

    ResourceDirectoryTable(IPEFileReader dataReader, long offset, @Nullable Long baseOffset) {
        IByteArray byteArray = dataReader.read(offset,LENGTH);
        this.characteristics = byteArray.readDWord();
        this.timeDateStamp = byteArray.readDWord();
        this.majorVersion = byteArray.readWord();
        this.minorVersion = byteArray.readWord();
        this.numberOfNamedEntries = byteArray.readWord();
        this.numberOfIdEntries = byteArray.readWord();
        int count = numberOfNamedEntries + numberOfIdEntries;
        this.entries = new HashMap<>(count);
        for (int i = 0; i < numberOfNamedEntries + numberOfIdEntries; i++) {
            ResourceDirectoryEntry entry = new ResourceDirectoryEntry(dataReader,
                    offset + LENGTH + (long) i * ResourceDirectoryEntry.LENGTH,
                    Optional.ofNullable(baseOffset).orElse(offset));
            entries.put(entry.getIntegerId(), entry);
        }
    }
    public ResourceDirectoryEntry getResourceDirectoryEntry(Long id) {
        return entries.get(id);
    }
}
