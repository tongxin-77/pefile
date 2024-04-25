package com.xin.pefile;

import com.xin.pefile.io.ByteArray;
import lombok.Getter;

/**
 * 表示一个用于精细管理与特定实体相关的数据的结构，重点关注名称、杂项信息以及内存地址细节。
 * 该类不可实例化，旨在存储与其用途相关的静态数据。
 * @author tongxin
 * @date 2024/4/20 17:26
 */
@Getter
public class SectionHeader {

    /**
     * 允许的最大名称长度。
     */
    private final static int NAME_LENGTH = 8;

    /**
     * 实体的名称，受限于最大长度。
     */
    private final String name;

    /**
     * 一个Misc对象，持有与实体相关的各种未指定信息。
     */
    private final Misc misc;

    /**
     * 实体在虚拟地址空间中的起始位置。
     */
    private final long virtualAddress;

    /**
     * 实体原始数据的大小（以字节为单位）。
     */
    private final long sizeOfRawData;

    /**
     * 指向实体原始数据在文件中偏移位置的指针。
     */
    private final long pointerToRawData;

    /**
     * 指向重定位表的指针，用于调整实体在内存中的位置。
     */
    private final long pointerToRelocations;

    /**
     * 指向行号表的指针，用于调试时关联源代码行与实体内存位置。
     */
    private final long pointerToLineNumbers;

    /**
     * 重定位表中的条目数，指示需要进行重定位操作的数量。
     */
    private final int numberOfRelocations;

    /**
     * 行号表中的条目数，指示关联到实体的源代码行数量。
     */
    private final int numberOfLineNumbers;

    /**
     * 描述实体特性的位标志，如可读、可写、可执行等属性。
     */
    private final long characteristics;

    SectionHeader(ByteArray byteArray) {
        this.name = byteArray.readString(NAME_LENGTH);
        this.misc = new Misc(byteArray.read(Misc.LENGTH));
        this.virtualAddress = byteArray.readDWord();
        this.sizeOfRawData = byteArray.readDWord();
        this.pointerToRawData = byteArray.readDWord();
        this.pointerToRelocations = byteArray.readDWord();
        this.pointerToLineNumbers = byteArray.readDWord();
        this.numberOfRelocations = byteArray.readWord();
        this.numberOfLineNumbers = byteArray.readWord();
        this.characteristics = byteArray.readDWord();
    }
}
