package com.xin.pefile;

import com.xin.pefile.io.IByteArray;
import com.xin.pefile.io.IPEFileReader;
import lombok.Getter;

/**
 * @author tongxin
 * @date 2024/4/21 16:00
 */
@Getter
public class FixedFileInfo{
    /**
     * 定义常量LENGTH，表示某种固定长度，具体用途根据上下文确定
     */
    public final static int LENGTH = 52;

    /**
     * 定义文件信息的固定魔数，用于标识文件格式
     */
    private final static long FIXED_FILE_INFO_MAGIC = 0xFEEF04BDL;

    /**
     * 文件的签名，用于验证文件的完整性和真实性
     */
    private final long signature;

    /**
     * 结构版本号，表示数据结构的版本
     */
    private final long structVersion;

    /**
     * 文件版本号的高位和低位
     */
    private final int[] fileVersionMS = new int[2];
    private final int[] fileVersionLS = new int[2];

    /**
     * 产品版本号的高位和低位
     */
    private final int[] productVersionMS = new int[2];
    private final int[] productVersionLS = new int[2];

    /**
     * 文件标志掩码，用于确定文件标志位的有效位
     */
    private final long fileFlagsMask;

    /**
     * 文件标志，表示文件的属性和状态
     */
    private final long fileFlags;

    /**
     * 文件适用的操作系统类型
     */
    private final long fileOS;

    /**
     * 文件类型，用于分类或识别文件
     */
    private final long fileType;

    /**
     * 文件子类型，进一步细化文件类型
     */
    private final long fileSubtype;

    /**
     * 文件日期的高位和低位，表示文件创建或最后修改的日期
     */
    private final long fileDateMS;
    private final long fileDateLS;

    /**
     * 偏移量，表示数据在文件中的位置
     */
    private final long offset;


    public FixedFileInfo(IPEFileReader dataReader, long offset) {
        this.offset = offset;
        IByteArray byteArray = dataReader.read(offset, LENGTH);
        this.signature = byteArray.readDWord();
        if (this.signature != FIXED_FILE_INFO_MAGIC) {
            throw new RuntimeException("FixedFileInfo signature error");
        }
        this.structVersion = byteArray.readDWord();
        readVersion(this.fileVersionMS, byteArray);
        readVersion(this.fileVersionLS, byteArray);
        readVersion(this.productVersionMS, byteArray);
        readVersion(this.productVersionLS, byteArray);

        this.fileFlagsMask = byteArray.readDWord();
        this.fileFlags = byteArray.readDWord();
        this.fileOS = byteArray.readDWord();
        this.fileType = byteArray.readDWord();
        this.fileSubtype = byteArray.readDWord();
        this.fileDateMS = byteArray.readDWord();
        this.fileDateLS = byteArray.readDWord();
    }

    private void readVersion(int[] version, IByteArray byteArray) {
        for (int i = 1; i >= 0; i--) {
            version[i] = byteArray.readWord();
        }
    }

    /**
     * 获取结尾
     *
     * @return
     */
    public long getEndOffset() {
        return offset + LENGTH;
    }


}
