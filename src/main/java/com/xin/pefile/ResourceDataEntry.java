package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import lombok.Getter;

/**
 * 该类包含了资源的相对虚拟地址（RVA）、数据大小、代码页和保留字段等属性。
 * @author tongxin
 * @date 2024/4/21 13:20
 */
@Getter
public class ResourceDataEntry {
    /**
     * 定义了资源信息长度，这里固定为16。
     */
    public final static int LENGTH = 16;

    /**
     * 资源的相对虚拟地址（RVA），指向资源数据在可执行文件中的位置。
     */
    private final long dataRva;

    /**
     * 资源数据的大小，以字节为单位。
     */
    private final long dataSize;

    /**
     * 资源使用的代码页，用于指定资源的编码格式。
     */
    private final long codePage;

    /**
     * 保留字段，供将来使用或实现特定用途。
     */
    private final long reserved;


    ResourceDataEntry(IPEFileReader dataReader, long offset) {
        IByteArray byteArray = dataReader.read(offset,LENGTH);
        this.dataRva = byteArray.readDWord();
        this.dataSize = byteArray.readDWord();
        this.codePage = byteArray.readDWord();
        this.reserved = byteArray.readDWord();
    }
}
