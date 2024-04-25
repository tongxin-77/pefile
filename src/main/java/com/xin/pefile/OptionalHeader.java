package com.xin.pefile;

import com.xin.pefile.io.ByteArray;
import lombok.Getter;

/**
 * 可选头结构体类，用于解析PE文件中的可选头。可选头提供了关于DLL或可执行文件的大量信息，
 * 包括入口点地址、图像基地址、节对齐方式等。
 * @author tongxin
 * @date 2024/4/18 22:05
 */
@Getter
public class OptionalHeader {
    private int magic; // 标识可选头的魔数
    private int majorLinkerVersion; // 链接器主要版本号
    private int minorLinkerVersion; // 链接器次要版本号
    private int sizeOfCode; // 可执行代码的大小
    private int sizeOfInitializedData; // 初始化的数据大小
    private int sizeOfUninitializedData; // 未初始化的数据大小
    private int addressOfEntryPoint; // 程序的入口点地址
    private int baseOfCode; // 代码区的基地址
    private long baseOfData; // 数据区的基地址
    private long imageBase; // 映像的首选加载地址
    private int sectionAlignment; // 节的对齐方式
    private int fileAlignment; // 文件的对齐方式
    private int majorOperatingSystemVersion; // 操作系统主要版本号
    private int minorOperatingSystemVersion; // 操作系统次要版本号
    private int win32VersionValue; // Win32版本值
    private int sizeOfImage; // 映像的大小
    private int SizeOfHeaders; // 头部的大小
    private int checkSum; // 映像的校验和
    private int subsystem; // 程序的子系统类型
    private int dllCharacteristics; // DLL特性
    private int sizeOfStackReserve; // 栈保留大小
    private int sizeOfStackCommit; // 栈提交大小
    private int sizeOfHeapReserve; // 堆保留大小
    private int sizeOfHeapCommit; // 堆提交大小
    private int loaderFlags; // 负责加载映像的加载器标志
    private int numberOfRvaAndSizes; // 数据目录的数量及其大小

    /**
     * 构造函数，用于初始化可选头字段。
     * @param byteArray 代表可选头的字节数组
     */
    public OptionalHeader(ByteArray byteArray) {
        // 初始化可选头字段的逻辑
    }
}

