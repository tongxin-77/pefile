package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import lombok.Data;

/**
 * 定义与资源目录相关的常量和成员变量。
 * 该类提供了对资源目录结构的抽象，包括资源的ID、子目录偏移量、资源目录表和资源数据入口。
 * @author tongxin
 * @date 2024/4/20 22:40
 */
@Data
public class ResourceDirectoryEntry {
    /**
     * 标识位掩码，用于判断是否是头信息
     */
    public final static long HEADER_MASK = 0x80000000L;
    /**
     * 位置掩码，用于提取位置信息
     */
    public final static long LOCATION_MASK = 0x7FFFFFFFL;
    /**
     * 长度常量，指定每个条目的长度
     */
    public final static int LENGTH = 8;
    /**
     * 资源的整数ID
     */
    private long integerId;
    /**
     * 子目录的偏移量
     */
    private long subdirectoryOffset;
    /**
     * 资源目录表
     */
    private ResourceDirectoryTable resourceDirectoryTable;
    /**
     * 资源数据入口
     */
    private ResourceDataEntry resourceDataEntry;

    /**
     * 构造函数用于初始化ResourceDirectoryEntry实例。
     * @param dataReader 提供数据读取功能的IDataReader对象。
     * @param offset 当前目录项在数据流中的偏移量。
     * @param baseOffset 基础偏移量，用于计算资源数据或子目录的实际位置。
     */
    ResourceDirectoryEntry(IPEFileReader dataReader, long offset, Long baseOffset) {
        // 从数据流中读取指定长度的数据
        IByteArray byteArray = dataReader.read(offset,LENGTH);
        // 从读取的字节数组中解析出整数ID和子目录偏移量
        integerId = byteArray.readDWord();
        subdirectoryOffset = byteArray.readDWord();

        // 判断当前目录项是否为叶子节点
        if (isLeaf()) {
            // 如果是叶子节点，则创建一个ResourceDataEntry实例
            this.resourceDataEntry = new ResourceDataEntry(
                    dataReader,
                    baseOffset + subdirectoryOffset
            );
            return;
        }
        // 如果不是叶子节点，则创建一个ResourceDirectoryTable实例
        resourceDirectoryTable = new ResourceDirectoryTable(dataReader,
                baseOffset + subdirectoryOffset & LOCATION_MASK,
                baseOffset);
    }

    /**
     * 判断当前对象是否为叶子节点。
     * 该方法通过检查subdirectoryOffset与HEADER_MASK的按位与结果是否为0来判断。
     * 如果结果为0，则表示当前节点为叶子节点；否则，表示当前节点不是叶子节点。
     *
     * @return Boolean 返回一个布尔值，如果当前节点是叶子节点，则返回true；否则返回false。
     */
    public Boolean isLeaf() {
        return (subdirectoryOffset & HEADER_MASK) == 0;
    }
}
