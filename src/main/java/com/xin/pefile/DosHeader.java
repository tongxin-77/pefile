package com.xin.pefile;

import com.xin.pefile.io.ByteArray;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

/**
 * DOS头结构体类，用于解析PE文件中的DOS头信息。
 * @author tongxin
 * @date 2024/4/18 0:27
 */
@Getter
public class DosHeader {
    // DOS头的固定大小
    public static final int DOS_HEADER_SIZE = 64;
    // DOS头的魔数，标识一个PE文件的开始
    public static final long DOS_HEADER_MAGIC = 0x5a4d;
    // DOS头魔数在文件中的偏移位置
    public static final int DOS_HEADER_MAGIC_OFFSET = 0x3C;

    // MZ签名
    private final long signature;
    // 最后一个页面中使用的字节数
    private final int usedBytesInLastPage;
    // 页面中的文件大小
    private final int fileSizeInPage;
    // 迁移项的数量
    private final int numberOfRelocationItems;
    // 头部大小（段）
    private final int headerSizeInParagraphs;
    // 最小额外段数
    private final int minExtraParagraphs;
    // 最大额外段数
    private final int maxExtraParagraphs;
    // 初始相对SS值
    private final int initialRelativeSS;
    // 初始SP值
    private final int initialSP;
    // 校验和
    private final int checksum;
    // 初始IP值
    private final int initialIP;
    // 初始相对CS值
    private final int initialRelativeCS;
    // 迁移表地址
    private final int addressOfRelocationTable;
    // 覆盖号
    private final int overlayNumber;
    // 保留字段1
    private final int[] reserved1;
    // OEM ID
    private final int oemId;
    // OEM信息
    private final int oemInfo;
    // 保留字段2
    private final int[] reserved2;
    // nt头的地址
    private final long addressOfNewExeHeader;

    /**
     * 根据字节数组构建DOS头对象。
     * @param byteArray 包含DOS头信息的字节数组
     * @throws IOException 如果解析过程中遇到错误，或者MZ签名不匹配，则抛出此异常
     */
    @SneakyThrows
    DosHeader(ByteArray byteArray) {
        // 读取并验证MZ签名
        long mzSignature = byteArray.readWord();
        if (DOS_HEADER_MAGIC !=mzSignature) {
            throw new IOException("Invalid PE file: Missing MZ signature");
        }
        this.signature = mzSignature;

        // 读取DOS头的其他字段
        this.usedBytesInLastPage = byteArray.readWord();
        this.fileSizeInPage = byteArray.readWord();
        this.numberOfRelocationItems = byteArray.readWord();
        this.headerSizeInParagraphs = byteArray.readWord();
        this.minExtraParagraphs = byteArray.readWord();
        this.maxExtraParagraphs = byteArray.readWord();
        this.initialRelativeSS = byteArray.readWord();
        this.initialSP = byteArray.readWord();
        this.checksum = byteArray.readWord();
        this.initialIP = byteArray.readWord();
        this.initialRelativeCS = byteArray.readWord();
        this.addressOfRelocationTable = byteArray.readWord();
        this.overlayNumber = byteArray.readWord();

        // 读取保留字段1
        this.reserved1 = new int[4];
        for (int i = 0; i < 4; i++) {
            this.reserved1[i] = byteArray.readWord();
        }

        // 读取OEM ID和信息
        this.oemId = byteArray.readWord();
        this.oemInfo = byteArray.readWord();

        // 读取保留字段2
        this.reserved2 = new int[10];
        for (int i = 0; i < 10; i++) {
            this.reserved2[i] = byteArray.readWord();
        }

        // 读取nt头的地址
        this.addressOfNewExeHeader = byteArray.readDWord();
    }
}
