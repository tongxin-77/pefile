package com.xin.pefile.io;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.xin.pefile.constants.DataTypeSizes.*;

/**
 * 提供对字节数组进行读取操作的实现类，支持多种数据类型的读取，如字符串、字节、单词和双字。
 * @author tongxin
 * @date 2024/4/18 0:11
 */
public class ByteArray implements IByteArray {
    private byte[] data;
    private int offset = 0;

    /**
     * 构造函数，初始化字节数组和偏移量。
     *
     * @param data 字节数组数据。
     */
    ByteArray(byte[] data) {
        this.data = data;
    }

    /**
     * 以UTF-8编码读取字符串。
     *
     * @return 从当前位置开始读取的字符串。
     */
    @Override
    public String readString() {
        return this.readString(StandardCharsets.UTF_8);
    }

    /**
     * 按指定字符集读取字符串。
     *
     * @param charset 字符集。
     * @return 从当前位置开始读取的字符串。
     */
    @Override
    public String readString(Charset charset) {
        return this.readString(0, this.data.length, charset);
    }

    /**
     * 按指定长度以UTF-8编码读取字符串。
     *
     * @param length 要读取的字符串长度。
     * @return 从当前位置开始读取的字符串。
     */
    @Override
    public String readString(int length) {
        return this.readString(length, StandardCharsets.UTF_8);
    }

    /**
     * 按指定长度和字符集读取字符串。
     *
     * @param length  要读取的字符串长度。
     * @param charset 字符集。
     * @return 从当前位置开始读取的字符串。
     */
    @Override
    public String readString(int length, Charset charset) {
        return this.readString(this.offset, length, charset);
    }

    /**
     * 从指定位置开始按指定长度和字符集读取字符串。
     *
     * @param offset  读取的起始位置。
     * @param length  要读取的字符串长度。
     * @param charset 字符集。
     * @return 从指定位置开始读取的字符串。
     */
    @Override
    public String readString(int offset, int length, Charset charset) {
        this.offset = offset;
        // 检查指定偏移和长度是否超出数组范围
        if (offset + length > data.length) {
            throw new ArrayIndexOutOfBoundsException("Length out of total");
        }
        byte[] bytes = new byte[length];
        System.arraycopy(data, offset, bytes, 0, length);
        this.offset += length;
        return new String(bytes, charset);
    }

    /**
     * 读取剩余所有字节。
     *
     * @return 剩余所有字节的数据。
     */
    @Override
    public byte[] readBytes() {
        return this.data;
    }

    /**
     * 读取指定长度的字节。
     *
     * @param length 要读取的字节长度。
     * @return 读取的字节数据。
     */
    @Override
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(data, offset, bytes, 0, length);
        this.offset += length;
        return bytes;
    }

    /**
     * 读取一个单词（2字节）。
     *
     * @return 读取的单词值。
     */
    @Override
    public int readWord() {
        if (offset + WORD > data.length) {
            throw new ArrayIndexOutOfBoundsException("Length out of total");
        }
        int value = (data[offset] & 0xFF) |
                ((data[offset + 1] & 0xFF) << 8);
        offset += WORD;
        return value;
    }

    /**
     * 读取一个双字（4字节）。
     *
     * @return 读取的双字值。
     */
    @Override
    public long readDWord() {
        if (offset + DWORD > data.length) {
            throw new ArrayIndexOutOfBoundsException("Length out of total");
        }
        long value =
                (data[offset] & 0xFF) |
                        ((data[offset + 1] & 0xFF) << 8) |
                        ((data[offset + 2] & 0xFF) << 16) |
                        ((long) (data[offset + 3] & 0xFF) << 24);
        offset += DWORD;
        return value;
    }

    /**
     * 读取指定长度的数据，并返回一个新的ByteArray实例。
     *
     * @param length 要读取的数据长度。
     * @return 包含读取数据的新ByteArray实例。
     */
    @Override
    public ByteArray read(int length) {
        byte[] bytes = this.readBytes(length);
        return new ByteArray(bytes);
    }
}