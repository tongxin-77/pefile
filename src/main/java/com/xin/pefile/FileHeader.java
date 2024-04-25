package com.xin.pefile;

import com.xin.pefile.io.ByteArray;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;

/**
 * @author tongxin
 * @date 2024/4/18 22:05
 */
@Getter
public class FileHeader {
    /**
     * 文件头大小常量，单位为字节
     */
    public final static int FILE_HEADER_SIZE = 0x14;

    /**
     * 机器类型，指明此文件是为哪种处理器构建的
     */
    private final int machine;

    /**
     * 段的数量，PE文件可以被组织成多个段
     */
    private final int numberOfSections;

    /**
     * 编译或链接的时间日期戳
     */
    private final long timeDateStamp;

    /**
     * 指向符号表的指针，用于调试信息
     */
    private final long pointerToSymbolTable;

    /**
     * 符号表中的符号数量
     */
    private final long numberOfSymbols;

    /**
     * 可选头的大小，单位为字节。不是所有PE文件都有可选头
     */
    private final int sizeOfOptionalHeader;

    /**
     * 文件的特性，如是否可执行、是否是DLL等
     */
    private final int characteristics;

    public FileHeader(ByteArray byteArray) {
        this.machine = byteArray.readWord();
        this.numberOfSections = byteArray.readWord();
        this.timeDateStamp = byteArray.readDWord();
        this.pointerToSymbolTable = byteArray.readDWord();
        this.numberOfSymbols = byteArray.readDWord();
        this.sizeOfOptionalHeader = byteArray.readWord();
        this.characteristics = byteArray.readWord();
    }
}
